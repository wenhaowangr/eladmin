package com.unionpay.modules.system.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import com.unionpay.modules.system.entity.TaskDO;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class TaskDTO extends TaskDO {

    private Double workload;

}
