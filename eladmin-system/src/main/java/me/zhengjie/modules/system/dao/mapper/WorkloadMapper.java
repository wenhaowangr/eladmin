package me.zhengjie.modules.system.dao.mapper;

import me.zhengjie.modules.system.domain.entity.TaskDO;
import me.zhengjie.modules.system.domain.entity.TaskFilter;
import me.zhengjie.modules.system.domain.entity.WorkLoadDO;

import java.util.List;

public interface WorkloadMapper {

    Integer insertRW(WorkLoadDO workLoadDO);

    WorkLoadDO findRWWorkLoadByTaskId(Integer taskId);

    Integer deleteRWWorkLoad(Integer id);
}
