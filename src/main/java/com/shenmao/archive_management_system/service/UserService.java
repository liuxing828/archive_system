package com.shenmao.archive_management_system.service;

import com.shenmao.archive_management_system.pojo.User;

public interface UserService {
    //登录
    public User  findByUsername(String name);

    //注册
    public void register(String username, String password);

    //更新昵称
    void update(String nickname);

    //更新头像
    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);

    void updateStatus(String id, String status);
}
