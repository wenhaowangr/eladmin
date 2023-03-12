package com.unionpay.modules.system.dao;

import com.unionpay.modules.system.entity.SprintDO;
import com.unionpay.modules.system.service.vo.SprintManageFilter;

import java.util.Date;
import java.util.List;

public interface SprintMapper {

    SprintDO findBySprintName(String name);

    Integer insertSprint(SprintDO sprintDO);

    SprintDO findBySprintId(int id);

    Integer deleteSprint(int id);

    List<SprintDO> querySprintByPage(SprintManageFilter sprintManageFilter);

    Integer querySprintTotalCount(SprintManageFilter sprintManageFilter);

    Integer updateSprint(SprintDO sprintDO);

    SprintDO getSprintByDate(Date date);

    List<SprintDO> findAllByYear(int yyyy);

    SprintDO getSprintByName(String name);

    List<SprintDO> findAllSprint();

//    BusinessLineDO findBusinessLineById(@Param("id") int id);
//
//    List<BusinessLineDO> findAllBusinessLines();
}
