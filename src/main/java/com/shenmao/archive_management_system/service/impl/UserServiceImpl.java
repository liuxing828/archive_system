package com.shenmao.archive_management_system.service.impl;

import com.shenmao.archive_management_system.mapper.UserMapper;
import com.shenmao.archive_management_system.pojo.User;
import com.shenmao.archive_management_system.service.UserService;
import com.shenmao.archive_management_system.utils.Md5Util;
import com.shenmao.archive_management_system.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUsername(String username) {
        User u = userMapper.findByUsername(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        //加密
        String md5String = Md5Util.getMD5String(password);
        userMapper.add(username, md5String);
    }

    @Override
    public void update(String nickname) {
        userMapper.update(nickname);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl, id);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd),id);
    }

    @Override
    public void updateStatus(String id, String status) {
        userMapper.updateStatus(id, status);
    }
}
