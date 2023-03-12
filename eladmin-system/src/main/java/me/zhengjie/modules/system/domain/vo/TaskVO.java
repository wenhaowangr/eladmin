package me.zhengjie.modules.system.domain.vo;

import lombok.Data;

import java.util.Date;
@Data
public class TaskVO {

    private Integer id;
    private String name;
    private Integer businessLineId;
    private String businessLineName;
    private Integer requirementId;
    private String requirementName;
    private Integer sprintId;
    private String sprintName;
    private Integer devEmployeeId;
    private String devEmployeeName;
    private Date dueDate;
    private String story;
    private String description;
    private String remark;
    private Integer priority;
    private Integer status;
    private Double workload;
}
