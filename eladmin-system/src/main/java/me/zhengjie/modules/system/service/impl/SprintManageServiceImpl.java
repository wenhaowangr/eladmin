package me.zhengjie.modules.system.service.impl;

import com.google.common.collect.Lists;
import me.zhengjie.exception.BizException;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.exception.EntityNotFoundException;
import me.zhengjie.modules.system.CheckUtils;
import me.zhengjie.modules.system.dao.mapper.SprintMapper;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.domain.entity.EmployeeDO;
import me.zhengjie.modules.system.domain.entity.SprintDO;
import me.zhengjie.modules.system.domain.entity.SprintManageFilter;
import me.zhengjie.modules.system.domain.entity.TaskFilter;
import me.zhengjie.modules.system.domain.vo.*;
import me.zhengjie.modules.system.service.EmployeeManageService;
import me.zhengjie.modules.system.service.SprintManageService;
import me.zhengjie.modules.system.service.TaskManageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    EmployeeManageService employeeManageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(SprintDO sprintDO) {
        if (sprintMapper.findBySprintName(sprintDO.getName()) != null) {
            throw new EntityExistException(User.class, "SprintName", sprintDO.getName());
        }
        // beginDate=2022-12-01, endDate=2022-12-21, quarter=2022Q4
        Date beginDate = sprintDO.getBeginDate();
        Date endDate = sprintDO.getEndDate();
        CheckUtils.checkBeginDateEarlierThanEndDate("冲刺结束时间需晚于冲刺开始时间！", beginDate, endDate);
        if (getSprintByDate(beginDate) != null || getSprintByDate(endDate) != null) {
            throw new BizException("冲刺时间重合!");
        }

        String quarter = calculateQuarter(beginDate, endDate);
        sprintDO.setQuarter(quarter);
        return sprintMapper.insertSprint(sprintDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(int id) {
        if (sprintMapper.findBySprintId(id) == null) {
            throw new EntityNotFoundException(User.class, "SprintId", String.valueOf(id));
        }
        return sprintMapper.deleteSprint(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageVO<SprintDO> querySprintByPage(SprintManageFilter sprintManageFilter) {

        CheckUtils.checkNonNullWithMsg("查询对象为空！", sprintManageFilter);
        //CheckUtils.checkNonNullWithMsg("开始日期为空！", sprintManageFilter.getBeginDate());
        //CheckUtils.checkNonNullWithMsg("结束日期为空！", sprintManageFilter.getEndDate());
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
    @Transactional(rollbackFor = Exception.class)
    public int update(SprintDO sprintDO) {
        if (sprintMapper.findBySprintId(sprintDO.getId()) == null) {
            throw new EntityNotFoundException(User.class, "SprintId", String.valueOf(sprintDO.getId()));
        }
        Date beginDate = sprintDO.getBeginDate();
        Date endDate = sprintDO.getEndDate();
        String quarter = calculateQuarter(beginDate, endDate);
        sprintDO.setQuarter(quarter);
        return sprintMapper.updateSprint(sprintDO);
    }

    // 查询指定日期的冲刺id，如果该日期没有任何冲刺，则返回0
    @Override
    public SprintDO getSprintByDate(Date date) {
        SprintDO sprintDO = sprintMapper.getSprintByDate(date);
        return sprintDO;
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
        HashSet<String> requirementNameList = new HashSet<>();
        for (SprintDO sprintDO : sprintDOList) {

            SprintVO sprintVO = new SprintVO(sprintDO);
            // 根据冲刺ID查询任务
            TaskFilter taskFilter = buildTaskFilter(sprintDO.getId());
            PageVO<TaskVO> taskVOs = taskManageService.queryTaskByPage(taskFilter);
            for (TaskVO taskVO : taskVOs.getData()){
                requirementNameList.add(taskVO.getRequirementName());
            }
            sprintVO.setRequirementNameList(requirementNameList);
            res.add(sprintVO);
        }
        return res;
    }


    @Override
    public List<MyCurTaskVO> getMyCurTask(String username) {

        List<MyCurTaskVO> res = new ArrayList<MyCurTaskVO>();
        EmployeeDO employeeDO = employeeManageService.getEmployeeByName(username);
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
        for (TaskVO taskVO : taskVOs.getData()){
            MyCurTaskVO myCurTaskVO = new MyCurTaskVO(taskVO);
            res.add(myCurTaskVO);
        }
        return res;
    }

    @Override
    public SprintDO getSprintByName(String name) {
        return sprintMapper.getSprintByName(name);
    }

    private TaskFilter buildTaskFilter(int sprintId) {
        TaskFilter taskFilter = new TaskFilter();
        taskFilter.setSprintId(sprintId);
        return taskFilter;
    }

}
