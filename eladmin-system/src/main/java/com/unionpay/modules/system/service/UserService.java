
package com.unionpay.modules.system.service;

import com.unionpay.modules.system.entity.UserDO;
import com.unionpay.modules.system.service.dto.UserLoginDto;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
public interface UserService {

    /**
     * 新增用户
     * @param resources /
     */
    void create(UserDO resources);

    /**
     * 根据用户名查询
     * @param userName /
     * @return /
     */
    UserLoginDto getLoginData(String userName);

}
