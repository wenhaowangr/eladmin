package com.unionpay.modules.system.dao;

import com.unionpay.modules.system.entity.WorkLoadDO;

public interface WorkloadMapper {

    Integer insertRW(WorkLoadDO workLoadDO);

    WorkLoadDO findRWWorkLoadByTaskId(Integer taskId);

    Integer deleteRWWorkLoad(Integer id);
}
