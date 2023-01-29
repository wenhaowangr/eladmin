package me.zhengjie.modules.system.dao.mapper;

import me.zhengjie.modules.system.domain.entity.EmployeeDO;


public interface EmployeeMapper {

    EmployeeDO getEmployeeByName(String name);

}
