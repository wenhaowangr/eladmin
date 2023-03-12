package me.zhengjie.modules.system.service.impl;

import com.google.common.collect.Lists;
import me.zhengjie.exception.BizException;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.exception.EntityNotFoundException;
import me.zhengjie.modules.system.utils.CheckUtils;
import me.zhengjie.modules.system.dao.mapper.BusinessLineMapper;
import me.zhengjie.modules.system.dao.mapper.RequirementMapper;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.domain.entity.*;
import me.zhengjie.modules.system.service.vo.PageVO;
import me.zhengjie.modules.system.service.RequirementManageService;
import me.zhengjie.modules.system.service.TaskManageService;
import me.zhengjie.modules.system.service.dto.RequirementDTO;
import me.zhengjie.modules.system.service.vo.RequirementManageFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequirementManageServiceImpl implements RequirementManageService {

    @Resource
    private RequirementMapper requirementMapper;
    @Resource
    private BusinessLineMapper businessLineMapper;
    @Resource
    private TaskManageService taskManageService;


    @Override
    public int add(RequirementDO requirementDO) {
        // 必填校验
        CheckUtils.checkNonNullWithMsg("需求不能为空！", requirementDO);
        CheckUtils.checkNonNullWithMsg("需求归属条线不能为空！", requirementDO.getBusinessLineId());
        CheckUtils.checkNonNullWithMsg("需求名字不能为空！", requirementDO.getName());
        CheckUtils.checkNonNullWithMsg("需求截止日期不能为空！", requirementDO.getDueDate());
        // 需求名称唯一性校验
        if (requirementMapper.findByRequirementName(requirementDO.getName()) != null) {
            throw new EntityExistException(RequirementDO.class, "RequirementName", requirementDO.getName());
        }
        // 条线存在校验
        if (businessLineMapper.findByBusinessLineId(requirementDO.getBusinessLineId()) != null) {
            throw new BizException("条线不存在！");
        }
        return requirementMapper.insertRequirement(requirementDO);
    }


    @Override
    public int update(RequirementDO requirementDO) {
        // 必填校验
        CheckUtils.checkNonNullWithMsg("需求不能为空！", requirementDO);
        CheckUtils.checkNonNullWithMsg("需求归属条线不能为空！", requirementDO.getBusinessLineId());
        CheckUtils.checkNonNullWithMsg("需求名字不能为空！", requirementDO.getName());
        CheckUtils.checkNonNullWithMsg("需求截止日期不能为空！", requirementDO.getDueDate());
        // 需求存在校验
        if (requirementMapper.findByRequirementId(requirementDO.getId()) == null) {
            throw new EntityNotFoundException(RequirementDO.class, "RequirementId", String.valueOf(requirementDO.getId()));
        }
        // 条线存在校验
        if (businessLineMapper.findByBusinessLineId(requirementDO.getBusinessLineId()) != null) {
            throw new BizException("条线不存在！");
        }
        return requirementMapper.updateRequirement(requirementDO);
    }


    /**
     * 删除需求
     * 1. 删除任务(包括删除workload)
     * 2. 删除需求
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(int id) {
        if (requirementMapper.findByRequirementId(id) == null) {
            throw new EntityNotFoundException(User.class, "RequirementId", String.valueOf(id));
        }
        List<TaskDO> taskDOList = taskManageService.queryTaskByRequirementId(id);
        taskDOList.forEach((taskDO -> {
            taskManageService.delete(taskDO.getId());
        }));
        return requirementMapper.deleteRequirement(id);
    }


    @Override
    public PageVO<RequirementDTO> queryRequirement(RequirementManageFilter requirementManageFilter) {
        int pageSize = requirementManageFilter.getPageSize();
        int pageNum = requirementManageFilter.getPageNum();
        Integer totalCount = requirementMapper.queryRequirementTotalCount(requirementManageFilter);
        if (totalCount == 0) {
            return new PageVO<RequirementDTO>(pageSize, pageNum, totalCount, Lists.newArrayList());
        }
        List<RequirementDO> requirementDOList = requirementMapper.queryRequirementByPage(requirementManageFilter);
        List<RequirementDTO> requirementDTOList = new ArrayList<>();
        requirementDOList.forEach((requirementDO) -> {
            requirementDTOList.add(convertDO2DTO(requirementDO));
        });

        return new PageVO<RequirementDTO>(pageSize, pageNum, totalCount, requirementDTOList);
    }


    @Override
    public RequirementDO getRequirementByName(String name) {
        return requirementMapper.getRequirementByName(name);
    }

    private RequirementDTO convertDO2DTO(RequirementDO requirementDO) {
        RequirementDTO requirementDTO = new RequirementDTO();
        BeanUtils.copyProperties(requirementDO, requirementDTO);
        BusinessLineDO businessLineDO = businessLineMapper.findByBusinessLineId(requirementDO.getBusinessLineId());
        requirementDTO.setBusinessLineName(businessLineDO.getName());
        return requirementDTO;
    }
}
