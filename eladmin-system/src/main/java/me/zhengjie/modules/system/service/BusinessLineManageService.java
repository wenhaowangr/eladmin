package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.entity.BusinessLineDO;
import me.zhengjie.modules.system.domain.entity.BusinessLineManageFilter;
import me.zhengjie.modules.system.domain.entity.SprintDO;
import me.zhengjie.modules.system.domain.vo.PageVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BusinessLineManageService {

    int add(BusinessLineDO businessLineDO);

    int delete(int id);

    List<BusinessLineDO> findAllBusinessLines();

    PageVO<BusinessLineDO> queryBusinessLineByPage(BusinessLineManageFilter filter);

    @Transactional(rollbackFor = Exception.class)
    int update(BusinessLineDO businessLineDO);
}
