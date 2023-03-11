package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.WorkTypeEnum;
import me.zhengjie.modules.system.dao.mapper.SprintMapper;
import me.zhengjie.modules.system.dao.mapper.WorkloadMapper;
import me.zhengjie.modules.system.domain.entity.WorkLoadDO;
import me.zhengjie.modules.system.service.dto.TaskDTO;
import me.zhengjie.modules.system.service.WorkloadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WorkloadServiceImpl implements WorkloadService {

    @Resource
    WorkloadMapper workloadMapper;
    @Resource
    SprintMapper sprintMapper;

    @Override
    public int addRWWorkLoad(TaskDTO taskDTO) {
        WorkLoadDO workloadDO = new WorkLoadDO(taskDTO);
        workloadDO.setType(WorkTypeEnum.RW.name());
        // 如果填了冲刺, 则获取其季度信息
        if (taskDTO.getSprintId() != null) {
            workloadDO.setQuarter(sprintMapper.findBySprintId(taskDTO.getSprintId()).getQuarter());
        }
        return workloadMapper.insertRW(workloadDO);
    }

    @Override
    public int deleteRWWorkLoad(int id) {
        return workloadMapper.deleteRWWorkLoad(id);
    }

}
