package com.unionpay.modules.system.service.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.unionpay.modules.system.service.dto.BasePage;


@EqualsAndHashCode(callSuper = true)
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
