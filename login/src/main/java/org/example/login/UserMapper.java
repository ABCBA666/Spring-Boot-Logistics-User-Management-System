package org.example.login;

import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface UserMapper {

    // 登录
    @Select("select * from t_user where uname=#{uname} and pwd=#{pwd}")
    User login(@Param("uname") String uname, @Param("pwd") String pwd);

    // 注册
    @Update("insert into t_user values(default,#{uname},#{pwd})")
    @Transactional
    void register(User user);

    // 注册时判断用户是否存在
    @Select("select * from t_user where uname=#{uname}")
    User registerByName(@Param("uname") String uname);

    // 根据用户ID查询用户信息
    @Select("select * from t_user where uid=#{uid}")
    User getUserById(@Param("uid") Integer uid);

    // 更新用户信息
    @Update("update t_user set uname=#{uname}, pwd=#{pwd} where uid=#{uid}")
    int updateUserInfo(User user);

    // 分配角色给用户
    @Update("insert into user_role(uid, role_id) select #{uid}, id from role where name=#{roleName}")
    int assignRole(@Param("uid") Integer uid, @Param("roleName") String roleName);

    // 查询用户权限
    @Select("select rp.permission_id from role_permission rp join user_role ur on rp.role_id = ur.role_id where ur.uid=#{uid}")
    List<String> getUserPermissions(@Param("uid") Integer uid);

    // 插入操作日志
    @Insert("insert into operation_log(user_id, operation_type, details) values(#{userId}, #{operationType}, #{details})")
    void insertOperationLog(@Param("userId") Integer userId, @Param("operationType") String operationType, @Param("details") String details);

    // 删除用户
    @Delete("delete from t_user where uid=#{uid}")
    int deleteUser(@Param("uid") Integer uid);
}
