package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.entity.WorkLoadDO;
import me.zhengjie.modules.system.domain.vo.TaskDTO;
import org.springframework.stereotype.Service;

public interface WorkloadService {
    int addRWWorkLoad(TaskDTO taskDTO);

    int deleteRWWorkLoad(int id);

}
