package com.unionpay.modules.system.dao;

import com.unionpay.modules.system.entity.EmployeeDO;

import java.util.List;


public interface EmployeeMapper {

    EmployeeDO getEmployeeByName(String name);

    EmployeeDO getEmployeeById(int id);

    EmployeeDO getEmployeeByUserId(long userId);

    List<EmployeeDO> getAllEmployee();
}
