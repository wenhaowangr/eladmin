package me.zhengjie.modules.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.exception.EntityNotFoundException;
import me.zhengjie.modules.system.utils.CheckUtils;
import me.zhengjie.modules.system.enums.ReqStatusEnum;
import me.zhengjie.modules.system.dao.BusinessLineMapper;
import me.zhengjie.modules.system.dao.EmployeeMapper;
import me.zhengjie.modules.system.dao.RequirementMapper;
import me.zhengjie.modules.system.entity.BusinessLineDO;
import me.zhengjie.modules.system.service.vo.BusinessLineManageFilter;
import me.zhengjie.modules.system.entity.RequirementDO;
import me.zhengjie.modules.system.service.vo.BusinessLineVO;
import me.zhengjie.modules.system.service.vo.PageVO;
import me.zhengjie.modules.system.service.BusinessLineManageService;
import me.zhengjie.modules.system.service.RequirementManageService;
import me.zhengjie.modules.system.service.dto.BusinessLineDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BusinessLineManageServiceImpl implements BusinessLineManageService {

    @Resource
    private BusinessLineMapper businessLineMapper;

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private RequirementMapper requirementMapper;

    @Resource
    private RequirementManageService requirementManageService;

    @Override
    public int add(BusinessLineDTO businessLineDTO) {
        // 必填校验
        CheckUtils.checkNonNullWithMsg("入参校验失败!", businessLineDTO, businessLineDTO.getName(), businessLineDTO.getManagerId(),
                businessLineDTO.getContext(), businessLineDTO.getMilestone());
        // 条线名称唯一性校验
        String name = businessLineDTO.getName();
        if (businessLineMapper.findByBusinessLineName(name) != null) {
            throw new EntityExistException(BusinessLineDTO.class, "BusinessLineName", name);
        }
        return businessLineMapper.insertBusinessLine(convertDTO2DO(businessLineDTO));
    }

    /**
     * 删除条线
     * 1. 删除条线
     * 2. 删除需求(包括任务, 工作量)
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(int id) {
        if (businessLineMapper.findByBusinessLineId(id) == null) {
            throw new EntityNotFoundException(BusinessLineDTO.class, "BusinessLineId", String.valueOf(id));
        }
        List<RequirementDO> requirementDOList = requirementMapper.queryReqByBusinessLineId(id);
        requirementDOList.forEach((requirementDO) -> {
            requirementManageService.delete(requirementDO.getId());
        });
        return businessLineMapper.deleteBusinessLine(id);
    }


    @Override
    public List<BusinessLineDO> findAllBusinessLines() {
        return businessLineMapper.findAllBusinessLines();
    }


    @Override
    public PageVO<BusinessLineDTO> queryBusinessLineByPage(BusinessLineManageFilter businessLineManageFilter) {

        CheckUtils.checkNonNullWithMsg("查询对象为空！", businessLineManageFilter);
        int pageSize = businessLineManageFilter.getPageSize();
        int pageNum = businessLineManageFilter.getPageNum();
        int totalCount = businessLineMapper.queryBusinessLineTotalCount(businessLineManageFilter);
        if (totalCount == 0) {
            return new PageVO<BusinessLineDTO>(pageSize, pageNum, totalCount, Lists.newArrayList());
        }

        List<BusinessLineDO> allBusinessLineDOS = businessLineMapper.queryBusinessLineByPage(businessLineManageFilter);
        List<BusinessLineDO> businessLineDOList = new ArrayList<>();

        // filter里的memberIds
        List<Integer> memberIds = businessLineManageFilter.getMemberIds();
        if (CollectionUtils.isEmpty(memberIds)) {
            businessLineDOList = allBusinessLineDOS;
        } else {
            for (BusinessLineDO businessLineDO : allBusinessLineDOS) {
                List<Integer> ids = JSON.parseArray(businessLineDO.getMemberIds(), Integer.class);
                if (ids.containsAll(memberIds)) {
                    businessLineDOList.add(businessLineDO);
                }
            }
        }
        List<BusinessLineDTO> businessLineDTOList = new ArrayList<>();
        businessLineDOList.forEach((businessLineDO) -> {
            businessLineDTOList.add(convertDO2DTO(businessLineDO));
        });
        return new PageVO<BusinessLineDTO>(pageSize, pageNum, totalCount, businessLineDTOList);
    }


    @Override
    public int update(BusinessLineDTO businessLineDTO) {

        // 必填校验
        CheckUtils.checkNonNullWithMsg("入参校验失败!", businessLineDTO, businessLineDTO.getName(), businessLineDTO.getManagerId(),
                businessLineDTO.getContext(), businessLineDTO.getMilestone());
        // 条线名称唯一性校验
        String name = businessLineDTO.getName();
        if (businessLineMapper.findByBusinessLineName(name) != null) {
            throw new EntityExistException(BusinessLineDTO.class, "BusinessLineName", name);
        }
        // 条线是否存在校验
        if (businessLineMapper.findByBusinessLineId(businessLineDTO.getId()) == null) {
            throw new EntityNotFoundException(BusinessLineDTO.class, "businessLineID", String.valueOf(businessLineDTO.getId()));
        }
        return businessLineMapper.updateBusinessLine(convertDTO2DO(businessLineDTO));
    }

    @Override
    public BusinessLineDO getBusinessLineByName(String name) {
        return businessLineMapper.getBusinessLineByName(name);
    }

    @Override
    public List<BusinessLineVO> getAllBusinessLineAndReq() {
        List<BusinessLineVO> businessLineVOList = new ArrayList<>();
        List<BusinessLineDO> allBusinessLines = businessLineMapper.findAllBusinessLines();
        allBusinessLines.forEach((businessLineDO) -> {
            BusinessLineVO businessLineVO = new BusinessLineVO();
            int businessLineId = businessLineDO.getId();
            String businessLineName = businessLineDO.getName();
            businessLineVO.setBusinessLineId(businessLineId);
            businessLineVO.setBusinessLineName(businessLineName);
            List<RequirementDO> allRequirementList = requirementMapper.queryReqByBusinessLineId(businessLineId);
            List<RequirementDO> requirementDOList = allRequirementList.stream().filter((requirementDO) -> {
                return requirementDO.getStatus() == ReqStatusEnum.DOING.getCode();
            }).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(requirementDOList)) {
                List<Integer> requirementIds = requirementDOList.stream().map(RequirementDO::getId).collect(Collectors.toList());
                List<String> requirementNames = requirementDOList.stream().map(RequirementDO::getName).collect(Collectors.toList());
                businessLineVO.setRequirementIds(requirementIds);
                businessLineVO.setRequirementNames(requirementNames);
            }
            businessLineVOList.add(businessLineVO);
        });
        return businessLineVOList;
    }


    private BusinessLineDO convertDTO2DO(BusinessLineDTO businessLineDTO) {
        BusinessLineDO businessLineDO = new BusinessLineDO();
        BeanUtils.copyProperties(businessLineDTO, businessLineDO);
        businessLineDO.setMemberIds(JSON.toJSONString(businessLineDTO.getMemberIds()));
        return businessLineDO;
    }

    private BusinessLineDTO convertDO2DTO(BusinessLineDO businessLineDO) {
        BusinessLineDTO businessLineDTO = new BusinessLineDTO();
        BeanUtils.copyProperties(businessLineDO, businessLineDTO);
        int managerId = businessLineDO.getManagerId();
        List<Integer> memberIds = JSON.parseArray(businessLineDO.getMemberIds(), Integer.class);
        List<String> memberNames = new ArrayList<>();
        memberIds.forEach((memberId) -> {
            memberNames.add(employeeMapper.getEmployeeById(memberId).getName());
        });
        businessLineDTO.setMemberNames(memberNames);
        businessLineDTO.setMemberIds(memberIds);
        businessLineDTO.setManagerName(employeeMapper.getEmployeeById(managerId).getName());
        return businessLineDTO;
    }

}
