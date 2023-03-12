package me.zhengjie.modules.system.dao;

import me.zhengjie.modules.system.entity.WorkLoadDO;

public interface WorkloadMapper {

    Integer insertRW(WorkLoadDO workLoadDO);

    WorkLoadDO findRWWorkLoadByTaskId(Integer taskId);

    Integer deleteRWWorkLoad(Integer id);
}
