package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.entity.WorkLoadDO;
import me.zhengjie.modules.system.service.dto.TaskDTO;

public interface WorkloadService {
    int addRWWorkload(TaskDTO taskDTO);

    int deleteRWWorkload(int id);

    WorkLoadDO queryWorkloadByTaskId(int taskId);


}
