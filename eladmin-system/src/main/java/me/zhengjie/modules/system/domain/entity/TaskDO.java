package me.zhengjie.modules.system.domain.entity;

import lombok.Data;
import me.zhengjie.modules.system.domain.vo.TaskDTO;

import java.util.Date;

@Data
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
}
