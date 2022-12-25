package me.zhengjie.modules.system.service.impl;

import me.zhengjie.exception.EntityExistException;
import me.zhengjie.exception.EntityNotFoundException;
import me.zhengjie.modules.system.dao.mapper.BusinessLineMapper;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.domain.entity.BusinessLineDO;
import me.zhengjie.modules.system.service.BusinessLineManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
        return null;
    }
}
