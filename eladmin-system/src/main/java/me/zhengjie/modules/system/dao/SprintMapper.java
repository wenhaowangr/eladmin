package me.zhengjie.modules.system.dao;

import me.zhengjie.modules.system.entity.SprintDO;
import me.zhengjie.modules.system.service.vo.SprintManageFilter;

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
