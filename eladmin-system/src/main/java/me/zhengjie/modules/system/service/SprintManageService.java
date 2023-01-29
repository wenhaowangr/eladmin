package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.entity.SprintDO;
import me.zhengjie.modules.system.domain.entity.SprintManageFilter;
import me.zhengjie.modules.system.domain.vo.MyCurTaskVO;
import me.zhengjie.modules.system.domain.vo.PageVO;
import me.zhengjie.modules.system.domain.vo.SprintVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


public interface SprintManageService {

    int add(SprintDO sprintDO);
    int delete(int id);
    PageVO<SprintDO> querySprintByPage(SprintManageFilter sprintManageFilter);
    int update(SprintDO sprintDO);

    SprintDO getSprintByDate(Date date);

    List<SprintVO> getSprintAndReq();

    List<MyCurTaskVO> getMyCurTask(String username);

    SprintDO getSprintByName(String name);
}
