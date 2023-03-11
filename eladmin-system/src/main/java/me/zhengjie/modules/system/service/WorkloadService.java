package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.service.dto.TaskDTO;

public interface WorkloadService {
    int addRWWorkLoad(TaskDTO taskDTO);

    int deleteRWWorkLoad(int id);

}
