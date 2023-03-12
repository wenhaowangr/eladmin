package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import me.zhengjie.modules.system.entity.TaskDO;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class TaskDTO extends TaskDO {

    private Double workload;

}
