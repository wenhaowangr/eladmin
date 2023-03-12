
package me.zhengjie.modules.system.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.dao.mapper.UserMapperV2;
import me.zhengjie.exception.EntityExistException;
import me.zhengjie.exception.EntityNotFoundException;
import me.zhengjie.modules.system.domain.entity.UserDO;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {


    @Resource
    UserMapperV2 userMapperV2;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(UserDO resources) {
        if (userMapperV2.findByUsername(resources.getUsername()) != null) {
            throw new EntityExistException(UserDO.class, "username", resources.getUsername());
        }
        if (userMapperV2.findByEmail(resources.getEmail()) != null) {
            throw new EntityExistException(UserDO.class, "email", resources.getEmail());
        }
        if (userMapperV2.findByPhone(resources.getPhone()) != null) {
            throw new EntityExistException(UserDO.class, "phone", resources.getPhone());
        }
        userMapperV2.insertUser(resources);
    }

    @Override
    public UserLoginDto getLoginData(String userName) {
        UserDO user = userMapperV2.findByUsername(userName);
        if (user == null) {
            throw new EntityNotFoundException(UserDO.class, "name", userName);
        } else {
            return buildUserLoginDto(user);
        }
    }

    private UserLoginDto buildUserLoginDto(UserDO userDO) {
        if (userDO == null ) {
            return null;
        }
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setId(Long.valueOf(userDO.getUserId()));
        userLoginDto.setEmail(userDO.getEmail());
        userLoginDto.setEnabled(userDO.getEnabled() == 1);
        userLoginDto.setUsername(userDO.getUsername());
        userLoginDto.setNickName(userDO.getNickName());
        userLoginDto.setGender(userDO.getGender());
        userLoginDto.setPhone(userDO.getPhone());
        userLoginDto.setPwdResetTime(userDO.getPasswordResetTime());
        return userLoginDto;
    }
}
