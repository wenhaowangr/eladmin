
package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.entity.UserDO;
import me.zhengjie.modules.system.service.dto.UserLoginDto;

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
