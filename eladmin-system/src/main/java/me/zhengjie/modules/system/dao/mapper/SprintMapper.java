package me.zhengjie.modules.system.dao.mapper;

import me.zhengjie.modules.system.domain.entity.SprintDO;
import me.zhengjie.modules.system.domain.entity.SprintManageFilter;
import org.apache.ibatis.annotations.Param;

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

//    BusinessLineDO findBusinessLineById(@Param("id") int id);
//
//    List<BusinessLineDO> findAllBusinessLines();
}
