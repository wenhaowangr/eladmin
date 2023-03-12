package me.zhengjie.modules.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.system.entity.BusinessLineDO;
import me.zhengjie.modules.system.entity.EmployeeDO;
import me.zhengjie.modules.system.entity.RequirementDO;
import me.zhengjie.modules.system.entity.SprintDO;
import me.zhengjie.modules.system.enums.TaskStateEnum;
import me.zhengjie.modules.system.dao.BusinessLineMapper;
import me.zhengjie.modules.system.dao.EmployeeMapper;
import me.zhengjie.modules.system.dao.RequirementMapper;
import me.zhengjie.modules.system.dao.SprintMapper;
import me.zhengjie.modules.system.service.vo.TaskVO;
import me.zhengjie.modules.system.service.TaskUploadService;
import me.zhengjie.modules.system.service.dto.TaskDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TaskUploadServiceImpl implements TaskUploadService {


    @Resource
    BusinessLineMapper businessLineMapper;
    @Resource
    SprintMapper sprintMapper;
    @Resource
    RequirementMapper requirementMapper;
    @Resource
    EmployeeMapper employeeMapper;

    private static final int EXCEL_COLUMN_NUM = 12;

    private static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

    /**
     * 将excel的内容转换成TaskDO
     *
     * @param excelInfo
     * @param resultMap
     * @return
     */
    public List<TaskDTO> excel2TaskDOList(Map<Integer, List<String>> excelInfo, Map<Integer, String> resultMap) {

        List<TaskDTO> taskDTOList = new ArrayList<>();
        List<String> firstRow = new ArrayList<>();
        int row = 0;
        try {
            for (Map.Entry<Integer, List<String>> entry : excelInfo.entrySet()) {
                row++;
                StringBuffer rowResult = new StringBuffer();
                List<String> content = entry.getValue();
                if (content.size() != EXCEL_COLUMN_NUM) {
                    continue;
                }
                if (content.contains("任务名") || content.contains("条线")) {
                    firstRow = content;
                    continue;
                }
                TaskVO taskVO = excelRow2TaskVO(firstRow, content, rowResult);
                TaskDTO taskDTO = taskVO2TaskDTO(taskVO, rowResult);
                taskDTOList.add(taskDTO);
                if (rowResult.length() != 0) {
                    resultMap.put(row, rowResult.toString());
                }
            }
        } catch (Exception e) {
            resultMap.put(-1, "运行异常！");
        }
        return taskDTOList;
    }

    /**
     * 将每一行的内容转换成TaskVO
     *
     * @param firstRow 表头标题
     * @param content  行内容
     * @return
     */
    private TaskVO excelRow2TaskVO(List<String> firstRow, List<String> content, StringBuffer rowResult) {

        TaskVO taskVO = new TaskVO();
        for (int i = 0; i < firstRow.size(); i++) {
            String colName = firstRow.get(i);
            String cellContent = content.get(i);
            if ("条线".equals(colName)) {
                taskVO.setBusinessLineName(cellContent);
            } else if ("需求".equals(colName)) {
                taskVO.setRequirementName(cellContent);
            } else if ("用户故事".equals(colName)) {
                taskVO.setStory(cellContent);
            } else if ("任务名".equals(colName)) {
                taskVO.setName(cellContent);
            } else if ("任务详细描述".equals(colName)) {
                taskVO.setDescription(cellContent);
            } else if ("优先级".equals(colName)) {
                try {
                    taskVO.setPriority(Integer.valueOf(cellContent));
                } catch (Exception e) {
                    rowResult.append("优先级格式错误!");
                }
            } else if ("开发者".equals(colName)) {
                taskVO.setDevEmployeeName(cellContent);
            } else if ("工作量".equals(colName)) {
                try {
                    taskVO.setWorkload(Double.valueOf(cellContent));
                } catch (Exception e) {
                    rowResult.append("工作量格式错误!");
                }
            } else if ("DUE".equals(colName)) {
                try {
                    taskVO.setDueDate(yyyyMMdd.parse(cellContent));
                } catch (Exception e) {
                    rowResult.append("DUE格式错误!");
                }
            } else if ("备注".equals(colName)) {
                taskVO.setRemark(cellContent);
            } else if ("冲刺".equals(colName)) {
                taskVO.setSprintName(cellContent);
            } else if ("状态".equals(colName)) {
                int status = TaskStateEnum.getCodeByName(cellContent);
                if (status == -1) {
                    rowResult.append("状态格式错误!");
                }
                taskVO.setStatus(status);
            }
        }
        return taskVO;
    }


    public TaskDTO taskVO2TaskDTO(TaskVO taskVO, StringBuffer rowResult) {

        String name = taskVO.getName();
        Date dueDate = taskVO.getDueDate();
        String story = taskVO.getStory();
        String description = taskVO.getDescription();
        String remark = taskVO.getRemark();
        Integer priority = taskVO.getPriority();
        Integer status = taskVO.getStatus();
        Double workload = taskVO.getWorkload();

        Integer businessLineId = 0;
        Integer requirementId = 0;
        Integer springId = 0;
        Integer devEmployeeId = 0;

        String businessLineName = taskVO.getBusinessLineName();
        BusinessLineDO businessLineDO = businessLineMapper.getBusinessLineByName(businessLineName);
        if (businessLineDO == null) {
            rowResult.append("条线不存在!");
        } else {
            businessLineId = businessLineDO.getId();
        }

        String requirementName = taskVO.getRequirementName();
        RequirementDO requirementDO = requirementMapper.getRequirementByName(requirementName);
        if (requirementDO == null) {
            rowResult.append("需求不存在!");
        } else {
            requirementId = requirementDO.getId();
        }

        String sprintName = taskVO.getSprintName();
        SprintDO sprintDO = sprintMapper.getSprintByName(sprintName);
        if (sprintDO == null) {
            rowResult.append("冲刺不存在!");
        } else {
            springId = sprintDO.getId();
        }

        String devEmployeeName = taskVO.getDevEmployeeName();
        EmployeeDO employeeDO = employeeMapper.getEmployeeByName(devEmployeeName);
        if (employeeDO == null) {
            rowResult.append("开发者不存在!");
        } else {
            devEmployeeId = employeeDO.getId();
        }
        return TaskDTO.builder()
                .name(name)
                .businessLineId(businessLineId)
                .sprintId(springId)
                .description(description)
                .devEmployeeId(devEmployeeId)
                .requirementId(requirementId)
                .dueDate(dueDate)
                .priority(priority)
                .remark(remark)
                .status(status)
                .story(story)
                .workload(workload).build();
    }


}
