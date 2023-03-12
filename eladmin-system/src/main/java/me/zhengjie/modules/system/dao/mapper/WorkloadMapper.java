package me.zhengjie.modules.system.dao.mapper;

import me.zhengjie.modules.system.domain.entity.WorkLoadDO;

public interface WorkloadMapper {

    Integer insertRW(WorkLoadDO workLoadDO);

    WorkLoadDO findRWWorkLoadByTaskId(Integer taskId);

    Integer deleteRWWorkLoad(Integer id);
}
