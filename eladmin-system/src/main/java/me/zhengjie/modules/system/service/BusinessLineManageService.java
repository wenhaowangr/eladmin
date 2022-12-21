package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.entity.BusinessLineDO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BusinessLineManageService {

    int add(BusinessLineDO businessLineDO);

    int delete(long id);

    List<BusinessLineDO> findAllBusinessLines();

}
