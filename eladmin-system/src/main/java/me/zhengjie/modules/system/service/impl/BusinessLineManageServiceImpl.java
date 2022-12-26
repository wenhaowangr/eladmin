package me.zhengjie.modules.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.exception.EntityNotFoundException;
import me.zhengjie.modules.system.CheckUtils;
import me.zhengjie.modules.system.dao.mapper.BusinessLineMapper;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.domain.entity.BusinessLineDO;
import me.zhengjie.modules.system.domain.entity.BusinessLineManageFilter;
import me.zhengjie.modules.system.domain.vo.PageVO;
import me.zhengjie.modules.system.service.BusinessLineManageService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class BusinessLineManageServiceImpl implements BusinessLineManageService {

    @Resource
    private BusinessLineMapper businessLineMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(BusinessLineDO businessLineDO) {
        if (businessLineMapper.findByBusinessLineName(businessLineDO.getName()) != null) {
            throw new EntityExistException(User.class, "BusinessLineName", businessLineDO.getName());
        }
        return businessLineMapper.insertBusinessLine(businessLineDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(int id) {
        if (businessLineMapper.findByBusinessLineId(id) == null){
            throw new EntityNotFoundException(User.class, "BusinessLineId", String.valueOf(id));
        }
        return businessLineMapper.deleteBusinessLine(id);
    }


    @Override
    public List<BusinessLineDO> findAllBusinessLines() {
        return businessLineMapper.findAllBusinessLines();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageVO<BusinessLineDO> queryBusinessLineByPage(BusinessLineManageFilter businessLineManageFilter) {

        CheckUtils.checkNonNullWithMsg("查询对象为空！", businessLineManageFilter);
        int pageSize = businessLineManageFilter.getPageSize();
        int pageNum = businessLineManageFilter.getPageNum();
        int totalCount = businessLineMapper.queryBusinessLineTotalCount(businessLineManageFilter);


        if (totalCount == 0) {
            return new PageVO<BusinessLineDO>(pageSize, pageNum, totalCount, Lists.newArrayList());
        }

        List<BusinessLineDO> allBusinessLineDOS = businessLineMapper.queryBusinessLineByPage(businessLineManageFilter);
        List<BusinessLineDO> result = new ArrayList<>();
        // filter里的memberIds
        List<String> memberIds = JSON.parseArray(businessLineManageFilter.getMemberIds(), String.class);
        if (CollectionUtils.isNotEmpty(memberIds)) {
            for (BusinessLineDO businessLineDO : allBusinessLineDOS) {
                List<String> ids = JSON.parseArray(businessLineDO.getMemberIds(), String.class);
                if (ids.containsAll(memberIds)) {
                    result.add(businessLineDO);
                }
            }
        }
        // 若filter里的memberIds为空,直接返回filter中其它条件查询的结果
        else {
            result = allBusinessLineDOS;
        }
        return new PageVO<BusinessLineDO>(pageSize, pageNum, totalCount, result);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(BusinessLineDO businessLineDO) {

        if (businessLineMapper.findByBusinessLineId(businessLineDO.getId()) == null) {
            throw new EntityNotFoundException(User.class, "businessLineID", String.valueOf(businessLineDO.getId()));
        }
        return businessLineMapper.updateBusinessLine(businessLineDO);
    }

}
