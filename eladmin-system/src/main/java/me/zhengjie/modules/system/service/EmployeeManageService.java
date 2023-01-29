package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.entity.EmployeeDO;


public interface EmployeeManageService {

    EmployeeDO getEmployeeByName(String name);
}
