package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.entity.EmployeeDO;

import java.util.List;


public interface EmployeeManageService {

    EmployeeDO getEmployeeByName(String name);

    EmployeeDO getEmployeeById(int id);

    List<EmployeeDO> getAllEmployee();
}
