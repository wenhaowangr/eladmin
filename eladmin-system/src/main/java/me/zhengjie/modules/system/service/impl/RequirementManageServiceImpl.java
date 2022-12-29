package me.zhengjie.modules.system.service.impl;

import com.google.common.collect.Lists;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.exception.EntityNotFoundException;
import me.zhengjie.modules.system.CheckUtils;
import me.zhengjie.modules.system.dao.mapper.RequirementMapper;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.domain.entity.RequirementDO;
import me.zhengjie.modules.system.domain.entity.RequirementManageFilter;
import me.zhengjie.modules.system.domain.entity.TaskDO;
import me.zhengjie.modules.system.domain.entity.TaskFilter;
import me.zhengjie.modules.system.domain.vo.PageVO;
import me.zhengjie.modules.system.domain.vo.RequirementVO;
import me.zhengjie.modules.system.domain.vo.TaskVO;
import me.zhengjie.modules.system.service.RequirementManageService;
import me.zhengjie.modules.system.service.TaskManageService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequirementManageServiceImpl implements RequirementManageService {

    @Resource
    private RequirementMapper requirementMapper;
    @Resource
    private TaskManageService taskManageService;

    @Override
    public int add(RequirementDO requirementDO) {
        if (requirementMapper.findByRequirementName(requirementDO.getName()) != null) {
            throw new EntityExistException(User.class, "RequirementName", requirementDO.getName());
        }
        CheckUtils.checkNonNullWithMsg("需求归属条线不能为空！", requirementDO.getBusinessLineId());
        CheckUtils.checkNonNullWithMsg("需求名字不能为空！", requirementDO.getName());
        CheckUtils.checkNonNullWithMsg("需求截止日期不能为空！", requirementDO.getDueDate());
        return requirementMapper.insertRequirement(requirementDO);
    }

    @Override
    public int update(RequirementDO requirementDO) {
        if (requirementMapper.findByRequirementId(requirementDO.getId()) == null) {
            throw new EntityNotFoundException(User.class, "RequirementId", String.valueOf(requirementDO.getId()));
        }
        CheckUtils.checkNonNullWithMsg("需求归属条线不能为空！", requirementDO.getBusinessLineId());
        CheckUtils.checkNonNullWithMsg("需求名字不能为空！", requirementDO.getName());
        CheckUtils.checkNonNullWithMsg("需求截止日期不能为空！", requirementDO.getDueDate());
        return requirementMapper.updateRequirement(requirementDO);
    }

    @Override
    public int delete(int id) {
        if (requirementMapper.findByRequirementId(id) == null){
            throw new EntityNotFoundException(User.class, "RequirementId", String.valueOf(id));
        }
        return requirementMapper.deleteRequirement(id);
    }

    @Override
    public PageVO<RequirementVO> queryRequirementAndTaskByPage(RequirementManageFilter requirementManageFilter) {

        int pageSize = requirementManageFilter.getPageSize();
        int pageNum = requirementManageFilter.getPageNum();
        int businessLineId = requirementManageFilter.getBusinessLineId();
        int totalCount = requirementMapper.queryTotalCountByBusinessLineId(businessLineId);//总需求数
        PageVO<RequirementVO> res = new PageVO<RequirementVO>(pageSize, pageNum, totalCount, new ArrayList<>());

        // 1.根据条线ID查询所有需求
        List<RequirementDO> requirementDOs = requirementMapper.findByBusinessLineIdAndPage(requirementManageFilter);
        if (requirementDOs == null || CollectionUtils.isEmpty(requirementDOs)) {
            return res;
        }

        List<RequirementVO> requirementVOList = new ArrayList<>();
        // 2.根据需求ID查询所有任务
        for (RequirementDO requirementDO : requirementDOs) {

            RequirementVO requirementVO = new RequirementVO(requirementDO);
            // 根据需求ID查询任务
            TaskFilter taskFilter = buildTaskFilter(requirementDO.getId());
            PageVO<TaskVO> taskVOs = taskManageService.queryTaskByPage(taskFilter);
            requirementVO.setTaskVOs((taskVOs == null) ? null :taskVOs.getData());
            requirementVOList.add(requirementVO);
        }
        res.setData(requirementVOList);
        return res;
    }


    private TaskFilter buildTaskFilter(int requirementId) {
        TaskFilter taskFilter = new TaskFilter();
        taskFilter.setRequirementId(requirementId);
        return taskFilter;
    }
}
