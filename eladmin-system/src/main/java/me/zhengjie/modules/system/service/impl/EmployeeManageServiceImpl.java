package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.dao.mapper.EmployeeMapper;
import me.zhengjie.modules.system.domain.entity.EmployeeDO;
import me.zhengjie.modules.system.service.EmployeeManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class EmployeeManageServiceImpl implements EmployeeManageService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public EmployeeDO getEmployeeByName(String name) {
        return employeeMapper.getEmployeeByName(name);
    }

}
