package me.zhengjie.modules.system.dao.mapper;

import me.zhengjie.modules.system.domain.entity.UserTestDO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface UserTestMapper {

    UserTestDO findUserById(@Param("id") int id);

    List<UserTestDO> findAllUsers();
}
