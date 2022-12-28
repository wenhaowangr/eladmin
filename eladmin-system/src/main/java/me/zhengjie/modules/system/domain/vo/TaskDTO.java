package me.zhengjie.modules.system.domain.vo;

import lombok.Data;
import me.zhengjie.modules.system.domain.entity.TaskDO;

@Data
public class TaskDTO extends TaskDO {

    private Double workload;

}
