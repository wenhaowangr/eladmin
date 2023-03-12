package me.zhengjie.modules.system.service.impl;

import com.google.common.collect.Lists;
import me.zhengjie.exception.BizException;
import me.zhengjie.exception.EntityNotFoundException;
import me.zhengjie.modules.system.CheckUtils;
import me.zhengjie.modules.system.dao.mapper.EmployeeMapper;
import me.zhengjie.modules.system.dao.mapper.SprintMapper;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.domain.entity.EmployeeDO;
import me.zhengjie.modules.system.domain.entity.SprintDO;
import me.zhengjie.modules.system.domain.entity.SprintManageFilter;
import me.zhengjie.modules.system.domain.entity.TaskFilter;
import me.zhengjie.modules.system.domain.vo.*;
import me.zhengjie.modules.system.service.SprintManageService;
import me.zhengjie.modules.system.service.TaskManageService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

import static me.zhengjie.modules.system.DateUtils.calculateQuarter;

@Service
public class SprintManageServiceImpl implements SprintManageService {

    @Resource
    private SprintMapper sprintMapper;

    @Resource
    TaskManageService taskManageService;

    @Resource
    EmployeeMapper employeeMapper;

    @Override
    public int add(SprintDO sprintDO) {

        // 必填校验
        CheckUtils.checkNonNullWithMsg("入参校验失败!", sprintDO, sprintDO.getName(), sprintDO.getBeginDate(),
                sprintDO.getEndDate());

        // 示例 : beginDate=2022-12-01, endDate=2022-12-21, quarter=2022Q4
        // 起止时间规范校验
        Date beginDate = sprintDO.getBeginDate();
        Date endDate = sprintDO.getEndDate();
        CheckUtils.checkBeginDateEarlierThanEndDate("时间校验失败！", beginDate, endDate);
        // 冲刺时间重合校验
        checkSprintDateOverlap(sprintDO, null);

        String quarter = calculateQuarter(beginDate, endDate);
        sprintDO.setQuarter(quarter);
        return sprintMapper.insertSprint(sprintDO);
    }

    @Override
    public int delete(int id) {
        if (sprintMapper.findBySprintId(id) == null) {
            throw new EntityNotFoundException(User.class, "SprintId", String.valueOf(id));
        }
        return sprintMapper.deleteSprint(id);
    }

    @Override
    public PageVO<SprintDO> querySprintByPage(SprintManageFilter sprintManageFilter) {

        // 入参校验
        CheckUtils.checkNonNullWithMsg("查询对象为空！", sprintManageFilter);
        CheckUtils.checkBeginDateEarlierThanEndDate("时间校验失败！", sprintManageFilter.getBeginDate(), sprintManageFilter.getEndDate());
        int pageSize = sprintManageFilter.getPageSize();
        int pageNum = sprintManageFilter.getPageNum();
        int totalCount = sprintMapper.querySprintTotalCount(sprintManageFilter);
        if (totalCount == 0) {
            return new PageVO<SprintDO>(pageSize, pageNum, totalCount, Lists.newArrayList());
        }
        return new PageVO<SprintDO>(pageSize, pageNum, totalCount, sprintMapper.querySprintByPage(sprintManageFilter));
    }


    // int update(SprintDO sprintDO);
    @Override
    public int update(SprintDO sprintDO) {
        // 必填校验
        CheckUtils.checkNonNullWithMsg("入参校验失败!", sprintDO, sprintDO.getName(), sprintDO.getBeginDate(),
                sprintDO.getEndDate());
        // 起止时间规范校验
        CheckUtils.checkBeginDateEarlierThanEndDate("时间校验失败！", sprintDO.getBeginDate(), sprintDO.getEndDate());
        // 冲刺时间重合校验
        checkSprintDateOverlap(sprintDO, sprintDO.getId());
        // 冲刺是否存在校验
        if (sprintMapper.findBySprintId(sprintDO.getId()) == null) {
            throw new EntityNotFoundException(SprintDO.class, "SprintId", String.valueOf(sprintDO.getId()));
        }

        String quarter = calculateQuarter(sprintDO.getBeginDate(), sprintDO.getEndDate());
        sprintDO.setQuarter(quarter);
        return sprintMapper.updateSprint(sprintDO);
    }

    // 查询指定日期的冲刺id，如果该日期没有任何冲刺，则返回0
    @Override
    public SprintDO getSprintByDate(Date date) {
        return sprintMapper.getSprintByDate(date);
    }

    @Override
    public List<SprintVO> getSprintAndReq() {
        Calendar cal = Calendar.getInstance();
        int yyyy = cal.get(Calendar.YEAR);
        List<SprintVO> res = new ArrayList<SprintVO>();

        // 1.查询本年所有冲刺
        List<SprintDO> sprintDOList = sprintMapper.findAllByYear(yyyy);
        if (sprintDOList == null || CollectionUtils.isEmpty(sprintDOList)) {
            return res;
        }

        // 2.根据冲刺ID查询所有任务
        HashSet<String> requirementSet = new HashSet<>();
        for (SprintDO sprintDO : sprintDOList) {
            SprintVO sprintVO = new SprintVO(sprintDO);
            // 根据冲刺ID查询任务
            TaskFilter taskFilter = buildTaskFilter(sprintDO.getId());
            PageVO<TaskVO> taskVOs = taskManageService.queryTaskByPage(taskFilter);
            for (TaskVO taskVO : taskVOs.getData()) {
                requirementSet.add(taskVO.getRequirementName());
            }
            sprintVO.setRequirementNameList(new ArrayList<>(requirementSet));
            res.add(sprintVO);
        }
        return res;
    }


    @Override
    public List<MyCurTaskVO> getMyCurTask(Long userId) {

        List<MyCurTaskVO> res = new ArrayList<MyCurTaskVO>();
        EmployeeDO employeeDO = employeeMapper.getEmployeeByUserId(userId);
        if (employeeDO == null) {
            return res;
        }
        // 首先获取当前冲刺
        int curSprintId = 0;
        SprintDO sprintDO = getSprintByDate(new Date());
        if (sprintDO != null) {
            curSprintId = sprintDO.getId();
        }
        // 构建查询任务请求filter
        TaskFilter taskFilter = buildTaskFilter(curSprintId);
        taskFilter.setDevEmployeeId(employeeDO.getId());
        PageVO<TaskVO> taskVOs = taskManageService.queryTaskByPage(taskFilter);
        for (TaskVO taskVO : taskVOs.getData()) {
            MyCurTaskVO myCurTaskVO = new MyCurTaskVO(taskVO);
            res.add(myCurTaskVO);
        }
        return res;
    }

    @Override
    public SprintDO getSprintByName(String name) {
        return sprintMapper.getSprintByName(name);
    }

    @Override
    public List<SprintVO> getAllSprint() {
        List<SprintDO> allSprint = sprintMapper.findAllSprint();
        List<SprintVO> allSprintVO = new ArrayList<>();
        allSprint.forEach((sprintDO)->{
            allSprintVO.add(new SprintVO(sprintDO));
        });
        return allSprintVO;
    }

    private TaskFilter buildTaskFilter(int sprintId) {
        TaskFilter taskFilter = new TaskFilter();
        taskFilter.setSprintId(sprintId);
        return taskFilter;
    }

    private void checkSprintDateOverlap(SprintDO sprintDO, Integer sprintId) {

        SprintDO sprintOnBeginDate = getSprintByDate(sprintDO.getBeginDate());
        SprintDO sprintOnEndDate = getSprintByDate(sprintDO.getEndDate());

        // 没有任何重合
        if (sprintOnBeginDate == null && sprintOnEndDate == null) {
            return;
        }
        // 新增, 直接抛异常
        if (sprintId == null) {
            throw new BizException("冲刺时间重合!");
        }
        // 编辑
        if (sprintOnBeginDate != null && sprintOnEndDate != null) {
            if (!(Objects.equals(sprintOnBeginDate.getId(), sprintId) && Objects.equals(sprintOnEndDate.getId(), sprintId))) {
                throw new BizException("冲刺时间重合!");
            }
        } else {
            if (sprintOnBeginDate != null){
                if (!Objects.equals(sprintOnBeginDate.getId(), sprintId)) {
                    throw new BizException("冲刺时间重合!");
                }
            } else {
                if (!Objects.equals(sprintOnEndDate.getId(), sprintId)) {
                    throw new BizException("冲刺时间重合!");
                }
            }
        }

    }


}
