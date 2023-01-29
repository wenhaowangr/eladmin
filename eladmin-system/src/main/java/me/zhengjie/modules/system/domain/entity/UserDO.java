package me.zhengjie.modules.system.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class UserDO {

    // 用户ID，主键
    private Integer userId;
    // 部门ID
    private Integer deptId;
    // 用户名
    private String username;
    // 昵称
    private String nickName;
    // 性别
    private String gender;
    // 手机号码
    private String phone;
    // 邮箱
    private String email;
    // 密码
    private String password;
    // 是否是管理员
    private Integer isAdmin;
    // 激活状态
    private Integer enabled;
    // 密码更新时间
    private Date passwordResetTime;

}
