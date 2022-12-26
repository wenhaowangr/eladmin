package me.zhengjie.modules.system.domain.entity;

import lombok.Data;
import me.zhengjie.modules.system.domain.vo.TaskVO;
import org.springframework.beans.BeanUtils;

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


    public TaskDO(TaskVO taskVO) {
        this.id = taskVO.getId();
        this.name = taskVO.getName();
        this.businessLineId = taskVO.getBusinessLineId();
        this.requirementId = taskVO.getRequirementId();
        this.sprintId = taskVO.getSprintId();
        this.story = taskVO.getStory();
        this.priority = taskVO.getPriority();
        this.devEmployeeId = taskVO.getDevEmployeeId();
        this.dueDate = taskVO.getDueDate();
        this.description = taskVO.getDescription();
        this.remark = taskVO.getRemark();
        this.status = taskVO.getStatus();
    }
}
