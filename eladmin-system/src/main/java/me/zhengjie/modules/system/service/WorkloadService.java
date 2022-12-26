package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.entity.WorkLoadDO;
import me.zhengjie.modules.system.domain.vo.TaskVO;
import org.springframework.stereotype.Service;

public interface WorkloadService {
    int addRWWorkLoad(TaskVO taskVO);

    int deleteRWWorkLoad(int id);

}
