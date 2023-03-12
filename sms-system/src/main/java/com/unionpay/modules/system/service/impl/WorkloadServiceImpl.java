package com.unionpay.modules.system.service.impl;

import com.unionpay.modules.system.enums.WorkTypeEnum;
import com.unionpay.modules.system.dao.SprintMapper;
import com.unionpay.modules.system.dao.WorkloadMapper;
import com.unionpay.modules.system.entity.WorkLoadDO;
import com.unionpay.modules.system.service.dto.TaskDTO;
import com.unionpay.modules.system.service.WorkloadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WorkloadServiceImpl implements WorkloadService {

    @Resource
    WorkloadMapper workloadMapper;
    @Resource
    SprintMapper sprintMapper;

    @Override
    public int addRWWorkload(TaskDTO taskDTO) {
        WorkLoadDO workloadDO = new WorkLoadDO(taskDTO);
        workloadDO.setType(WorkTypeEnum.RW.name());
        // 如果填了冲刺, 则获取其季度信息
        if (taskDTO.getSprintId() != null) {
            workloadDO.setQuarter(sprintMapper.findBySprintId(taskDTO.getSprintId()).getQuarter());
        }
        return workloadMapper.insertRW(workloadDO);
    }

    @Override
    public int deleteRWWorkload(int id) {
        return workloadMapper.deleteRWWorkLoad(id);
    }

    @Override
    public WorkLoadDO queryWorkloadByTaskId(int taskId) {
        return workloadMapper.findRWWorkLoadByTaskId(taskId);
    }

}
