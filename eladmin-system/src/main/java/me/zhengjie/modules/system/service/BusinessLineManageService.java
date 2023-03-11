package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.entity.BusinessLineDO;
import me.zhengjie.modules.system.domain.entity.BusinessLineManageFilter;
import me.zhengjie.modules.system.domain.entity.SprintDO;
import me.zhengjie.modules.system.domain.vo.PageVO;
import me.zhengjie.modules.system.service.dto.BusinessLineDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BusinessLineManageService {

    int add(BusinessLineDTO businessLineDTO);

    int delete(int id);

    List<BusinessLineDO> findAllBusinessLines();

    PageVO<BusinessLineDTO> queryBusinessLineByPage(BusinessLineManageFilter filter);

    int update(BusinessLineDTO businessLineDTO);

    BusinessLineDO getBusinessLineByName(String name);
}
