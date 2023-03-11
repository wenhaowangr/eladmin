package me.zhengjie.modules.system.domain.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import me.zhengjie.modules.system.service.dto.TaskDTO;

import java.util.Date;

@Data
@SuperBuilder
public class TaskDO {

    private Integer id;
    private String name;
    private Integer businessLineId;
    private Integer requirementId;
    private Integer sprintId;
    private Integer devEmployeeId;
    private Date dueDate;
    private String story;
    private String description;
    private String remark;
    private Integer priority;
    private Integer status;

    public TaskDO() {
    }


    public TaskDO(TaskDTO taskDTO) {
        this.id = taskDTO.getId();
        this.name = taskDTO.getName();
        this.businessLineId = taskDTO.getBusinessLineId();
        this.requirementId = taskDTO.getRequirementId();
        this.sprintId = taskDTO.getSprintId();
        this.story = taskDTO.getStory();
        this.priority = taskDTO.getPriority();
        this.devEmployeeId = taskDTO.getDevEmployeeId();
        this.dueDate = taskDTO.getDueDate();
        this.description = taskDTO.getDescription();
        this.remark = taskDTO.getRemark();
        this.status = taskDTO.getStatus();
    }


    public TaskDO(String name, Integer businessLineId, Integer requirementId, Integer sprintId, Integer devEmployeeId,
                  Date dueDate, String story, String description, String remark, Integer priority, Integer status) {
        this.name = name;
        this.businessLineId = businessLineId;
        this.requirementId = requirementId;
        this.sprintId = sprintId;
        this.devEmployeeId = devEmployeeId;
        this.dueDate = dueDate;
        this.story = story;
        this.description = description;
        this.remark = remark;
        this.priority = priority;
        this.status = status;
    }
}
