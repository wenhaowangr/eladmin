package com.unionpay.modules.system.service.impl;

import com.unionpay.modules.system.dao.EmployeeMapper;
import com.unionpay.modules.system.entity.EmployeeDO;
import com.unionpay.modules.system.service.EmployeeManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class EmployeeManageServiceImpl implements EmployeeManageService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public EmployeeDO getEmployeeByName(String name) {
        return employeeMapper.getEmployeeByName(name);
    }

    @Override
    public EmployeeDO getEmployeeById(int id) {
        return employeeMapper.getEmployeeById(id);
    }

    @Override
    public List<EmployeeDO> getAllEmployee() {
        return employeeMapper.getAllEmployee();
    }

}
