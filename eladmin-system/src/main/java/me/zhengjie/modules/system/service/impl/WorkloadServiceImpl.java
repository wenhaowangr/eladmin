package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.WorkTypeEnum;
import me.zhengjie.modules.system.dao.mapper.SprintMapper;
import me.zhengjie.modules.system.dao.mapper.WorkloadMapper;
import me.zhengjie.modules.system.domain.entity.WorkLoadDO;
import me.zhengjie.modules.system.domain.vo.TaskVO;
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
    public int addRWWorkLoad(TaskVO taskVO) {
        WorkLoadDO workloadDO = new WorkLoadDO(taskVO);
        workloadDO.setType(WorkTypeEnum.RW.name());
        workloadDO.setQuarter(sprintMapper.findBySprintId(taskVO.getSprintId()).getQuarter());
        return workloadMapper.insertRW(workloadDO);
    }

    @Override
    public int deleteRWWorkLoad(int id) {
        return workloadMapper.deleteRWWorkLoad(id);
    }

}
