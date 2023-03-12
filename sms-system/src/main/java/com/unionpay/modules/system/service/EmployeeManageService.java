package com.unionpay.modules.system.service;

import com.unionpay.modules.system.entity.EmployeeDO;

import java.util.List;


public interface EmployeeManageService {

    EmployeeDO getEmployeeByName(String name);

    EmployeeDO getEmployeeById(int id);

    List<EmployeeDO> getAllEmployee();
}
