package me.zhengjie.modules.system.domain.entity;

import lombok.Data;


@Data
public class TaskInfoDO {

    // 任务信息
    private Integer id;  // 任务id
    private String name;  // 任务名称
    private Integer businessLineId;  // 条线ID
    private Integer requirementId;  // 需求ID
    private Integer sprintId;  // 冲刺ID

    // 开发者信息
    private Integer devEmployeeId;  // 开发者ID
    private String devEmployeeName;  //

    // 工作量信息
    private Double workLoad;  // 工作量
    private String type;  // 工作量类型
    private String quarter;  // 所属季度

}
