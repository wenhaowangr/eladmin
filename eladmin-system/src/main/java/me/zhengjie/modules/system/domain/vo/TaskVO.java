package me.zhengjie.modules.system.domain.vo;

import lombok.Data;

import java.util.Date;
@Data
public class TaskVO {

    private Integer id;
    private String name;
    private String businessLineName;
    private String requirementName;
    private String sprintName;
    private String devEmployeeName;
    private Date dueDate;
    private String story;
    private String description;
    private String remark;
    private Integer priority;
    private Integer status;
    private Double workload;
}
