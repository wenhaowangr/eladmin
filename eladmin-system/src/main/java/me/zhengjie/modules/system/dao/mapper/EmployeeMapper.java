package me.zhengjie.modules.system.dao.mapper;

import me.zhengjie.modules.system.domain.entity.EmployeeDO;

import java.util.List;


public interface EmployeeMapper {

    EmployeeDO getEmployeeByName(String name);

    EmployeeDO getEmployeeById(int id);

    EmployeeDO getEmployeeByUserId(long userId);

    List<EmployeeDO> getAllEmployee();
}
