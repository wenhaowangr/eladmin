package com.unionpay.modules.system.service;

import com.unionpay.modules.system.entity.SprintDO;
import com.unionpay.modules.system.service.vo.SprintManageFilter;
import com.unionpay.modules.system.service.vo.MyCurTaskVO;
import com.unionpay.modules.system.service.vo.PageVO;
import com.unionpay.modules.system.service.vo.SprintVO;

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
