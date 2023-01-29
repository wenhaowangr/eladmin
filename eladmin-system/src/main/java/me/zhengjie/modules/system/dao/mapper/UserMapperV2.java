package me.zhengjie.modules.system.dao.mapper;

import me.zhengjie.modules.system.domain.entity.UserDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserMapperV2 {

    List<UserDO> findAllUser();

    UserDO findByUsername(@Param("username") String username);

    UserDO findByEmail(@Param("email") String email);

    UserDO findByPhone(@Param("phone") String phone);

    void insertUser(UserDO userDO);

    void updatePassword(@Param("username") String username, @Param("password") String password);

    void updateEmail(@Param("username") String username, @Param("email") String email);

    void deleteUserById(@Param("userId") int userId);

}
