package me.zhengjie.modules.system.service.impl;

import me.zhengjie.exception.BizException;
import me.zhengjie.exception.EntityNotFoundException;
import me.zhengjie.modules.system.CheckUtils;
import me.zhengjie.modules.system.TaskStateEnum;
import me.zhengjie.modules.system.dao.mapper.*;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.domain.entity.*;
import me.zhengjie.modules.system.domain.vo.PageVO;
import me.zhengjie.modules.system.domain.vo.TaskVO;
import me.zhengjie.modules.system.service.*;
import me.zhengjie.modules.system.service.dto.TaskDTO;
import me.zhengjie.modules.system.utils.FileUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TaskManageServiceImpl implements TaskManageService {

    @Resource
    TaskMapper taskMapper;
    @Resource
    TaskUploadService taskUploadService;
    @Resource
    WorkloadService workloadService;
    @Resource
    RequirementMapper requirementMapper;
    @Resource
    BusinessLineMapper businessLineMapper;
    @Resource
    SprintMapper sprintMapper;

    private static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

    @Override
    public PageVO<TaskVO> queryTaskByPage(TaskFilter filter) {
        Integer pageNum = filter.getPageNum();
        Integer pageSize = filter.getPageSize();
        Integer taskTotalCount = taskMapper.queryTaskTotalCount(filter);
        if (taskTotalCount == 0) {
            return new PageVO<>(pageSize, pageNum, taskTotalCount, new ArrayList<>());
        }
        List<TaskVO> taskVOList = taskMapper.queryTaskByPage(filter);
        return new PageVO<>(pageSize, pageNum, taskTotalCount, taskVOList);
    }

    @Override
    public int add(TaskDO taskDO) {
        CheckUtils.checkNonNullWithMsg("任务信息填写不完整！", taskDO, taskDO.getBusinessLineId(),
                taskDO.getName(), taskDO.getRequirementId(), taskDO.getStatus());
        Integer requirementId = taskDO.getRequirementId();
        if (requirementMapper.findByRequirementId(requirementId) == null) {
            throw new BizException("需求不存在!");
        }
        Integer businessLineId = taskDO.getBusinessLineId();
        if (businessLineMapper.findByBusinessLineId(businessLineId) == null) {
            throw new BizException("条线不存在!");
        }
        Integer sprintId = taskDO.getSprintId();
        if (sprintId != null && sprintMapper.findBySprintId(sprintId) == null) {
            throw new BizException("冲刺不存在!");
        }
        return taskMapper.insertTask(taskDO);
    }

    /**
     * 删除任务
     * 1. 删除任务
     * 2. 删除workload
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(int id) {
        if (taskMapper.queryTaskById(id) == null) {
            throw new EntityNotFoundException(TaskDTO.class, "TaskId", String.valueOf(id));
        }
        if (workloadService.queryWorkloadByTaskId(id) != null) {
            workloadService.deleteRWWorkload(id);
        }
        return taskMapper.deleteTask(id);
    }

    @Override
    public List<TaskDO> findAllTask() {

        return taskMapper.findAllTask();
    }

    @Override
    public int update(TaskDO taskDO) {
        CheckUtils.checkNonNullWithMsg("任务信息填写不完整！", taskDO, taskDO.getBusinessLineId(),
                taskDO.getName(), taskDO.getRequirementId(), taskDO.getStatus());
        if (taskMapper.queryTaskById(taskDO.getId()) == null) {
            throw new BizException("任务不存在!");
        }
        Integer requirementId = taskDO.getRequirementId();
        if (requirementMapper.findByRequirementId(requirementId) == null) {
            throw new BizException("需求不存在!");
        }
        Integer businessLineId = taskDO.getBusinessLineId();
        if (businessLineMapper.findByBusinessLineId(businessLineId) == null) {
            throw new BizException("条线不存在!");
        }
        Integer sprintId = taskDO.getSprintId();
        if (sprintId != null && sprintMapper.findBySprintId(sprintId) == null) {
            throw new BizException("冲刺不存在!");
        }
        return taskMapper.updateTask(taskDO);
    }

    @Override
    public List<TaskDO> queryTaskByRequirementId(int requirementId) {

        return taskMapper.queryTaskByRequirementId(requirementId);
    }

    @Override
    public List<TaskDO> queryTaskBySprintId(int sprintId) {

        return taskMapper.queryTaskBySprintId(sprintId);
    }

    @Override
    public Map<Integer, List<TaskDO>> queryTaskBySprintIds(List<Integer> sprintIds) {

        Map<Integer, List<TaskDO>> sprintTaskMap = new HashMap<>();
        if (CollectionUtils.isEmpty(sprintIds)) {
            return sprintTaskMap;
        }
        List<TaskDO> taskDOList = taskMapper.queryTaskBySprintIds(sprintIds);
        for (TaskDO taskDO : taskDOList) {
            int sprintId = taskDO.getSprintId();
            if (sprintTaskMap.containsKey(sprintId)) {
                sprintTaskMap.get(sprintId).add(taskDO);
            } else {
                List<TaskDO> taskDOs = new ArrayList<>();
                taskDOs.add(taskDO);
                sprintTaskMap.put(sprintId, taskDOs);
            }
        }
        return sprintTaskMap;
    }

    @Override
    public Map<Integer, List<TaskInfoDO>> queryWorkloadOfTaskBySprintIds(List<Integer> sprintIds) {

        List<TaskInfoDO> taskInfoDOs = taskMapper.queryWorkloadOfTaskBySprintIds(sprintIds);
        HashMap<Integer, List<TaskInfoDO>> taskIdInfoMap = new HashMap<>();
        for (TaskInfoDO taskInfoDO : taskInfoDOs) {
            int sprintId = taskInfoDO.getSprintId();
            if (taskIdInfoMap.containsKey(sprintId)) {
                taskIdInfoMap.get(sprintId).add(taskInfoDO);
            } else {
                List<TaskInfoDO> temp = new ArrayList<>();
                temp.add(taskInfoDO);
                taskIdInfoMap.put(sprintId, temp);
            }
        }
        return taskIdInfoMap;
    }

    @Override
    public void download(List<TaskVO> taskVOList, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TaskVO taskVO : taskVOList) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("条线", taskVO.getBusinessLineName());
            map.put("需求", taskVO.getRequirementName());
            map.put("任务名", taskVO.getName());
            map.put("用户故事", taskVO.getStory());
            map.put("任务详细描述", taskVO.getDescription());
            map.put("开发者", taskVO.getDevEmployeeName());
            map.put("工作量", taskVO.getWorkload());
            map.put("状态", TaskStateEnum.getNameByCode(taskVO.getStatus()));
            map.put("冲刺", taskVO.getSprintName());
            map.put("优先级", taskVO.getPriority());
            map.put("DUE", yyyyMMdd.format(taskVO.getDueDate()));
            map.put("备注", taskVO.getRemark());
            list.add(map);
        }

        FileUtils.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<Integer, String> upload(MultipartFile file) {

        Map<Integer, List<String>> excelInfo = FileUtils.readExcel(file);
        // key: 行数, value: 失败原因
        Map<Integer, String> resultMap = new HashMap<>();
        /**
         * {0=[条线, 需求, 任务名, 用户故事, 任务详细描述, 开发者, 工作量, 状态, 冲刺, 优先级, DUE, 备注],
         * 1=[画像, 画像中心第二批次需求, VOP批导优化-前端, 用户故事：blablabla, 任务详细描述：blablabla, 王文灏, 100, 0, sprint15, 13, 2030/01/01, 数据开发：EmployeeX],
         * 2=[画像, 画像中心第一批次需求, 1226测试任务, 用户故事, 描述, 王文灏, 0, 0, sprint16, 13, 2025/12/27, 备注]}
         */

        /**
         * 1. 找到标题行
         * 2. 判断每一行cell数量相同
         * 3. 判断条线名字、需求名字、开发者、是否存在，并查询到对应的ID
         * 4. 构建TaskDO insert
         */

        List<TaskDTO> taskDTOList = taskUploadService.excel2TaskDOList(excelInfo, resultMap);
        if (MapUtils.isEmpty(resultMap)) {
            for (TaskDTO taskDTO : taskDTOList) {
                // 写入任务表
                TaskDO taskDO = new TaskDO(taskDTO);
                add(taskDO);
                taskDTO.setId(taskDO.getId());
                // 写入workload表
                workloadService.addRWWorkload(taskDTO);
            }
        }
        return resultMap;
    }

    @Override
    public void updateTaskStatus(int id, int status) {

        taskMapper.updateTaskStatus(id, status);
    }

}
