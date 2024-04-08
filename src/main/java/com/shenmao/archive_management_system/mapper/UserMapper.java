package com.shenmao.archive_management_system.mapper;

import com.shenmao.archive_management_system.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{username}")
    User findByUsername(String username);


    @Insert("insert into user (username, password) values(#{username}, #{password})")
    void add(String username, String password);

    @Update("update user set nickname = #{nickname} where id = #{id}")
    void update(String nickname);

    @Update("update user set userUrl = #{avatarUrl} where id = #{id}")
    void updateAvatar(String avatarUrl, Integer id);

    @Update("update user set password = #{newPwd} where id = #{id}")
    void updatePwd(String newPwd, Integer id);

    @Update("update user set status = #{status} where id = #{id}")
    void updateStatus(String id, String status);
}
