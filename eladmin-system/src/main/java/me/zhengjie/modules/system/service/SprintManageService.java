package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.entity.SprintDO;
import me.zhengjie.modules.system.domain.entity.SprintManageFilter;
import me.zhengjie.modules.system.domain.vo.PageVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


public interface SprintManageService {

    int add(SprintDO sprintDO);
    int delete(int id);
    PageVO<SprintDO> querySprintByPage(SprintManageFilter sprintManageFilter);
    int update(SprintDO sprintDO);

    SprintDO getSprintByDate(Date date);
    int getCurSprint();
}
