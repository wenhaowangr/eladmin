package me.zhengjie.modules.system.domain.entity;

import lombok.Data;
import me.zhengjie.modules.system.service.dto.BasePage;


@Data
public class TaskFilter extends BasePage {

    private String name;
    private Integer businessLineId;
    private Integer requirementId;
    private Integer sprintId;
    private Integer devEmployeeId;
    private Integer status;
    private Integer requirementStatus;

}
