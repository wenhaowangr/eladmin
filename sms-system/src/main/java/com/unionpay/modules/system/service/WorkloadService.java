package com.unionpay.modules.system.service;

import com.unionpay.modules.system.entity.WorkLoadDO;
import com.unionpay.modules.system.service.dto.TaskDTO;

public interface WorkloadService {
    int addRWWorkload(TaskDTO taskDTO);

    int deleteRWWorkload(int id);

    WorkLoadDO queryWorkloadByTaskId(int taskId);


}
