package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.entity.SprintDO;
import me.zhengjie.modules.system.service.vo.SprintManageFilter;
import me.zhengjie.modules.system.service.vo.MyCurTaskVO;
import me.zhengjie.modules.system.service.vo.PageVO;
import me.zhengjie.modules.system.service.vo.SprintVO;

import java.util.Date;
import java.util.List;


public interface SprintManageService {

    int add(SprintDO sprintDO);
    int delete(int id);
    PageVO<SprintDO> querySprintByPage(SprintManageFilter sprintManageFilter);
    int update(SprintDO sprintDO);

    SprintDO getSprintByDate(Date date);

    List<SprintVO> getSprintAndReq();

    List<MyCurTaskVO> getMyCurTask(Long userId);

    SprintDO getSprintByName(String name);

    List<SprintVO> getAllSprint();

}
