package com.shenmao.archive_management_system.controller;

import com.shenmao.archive_management_system.pojo.Result;
import com.shenmao.archive_management_system.pojo.User;
import com.shenmao.archive_management_system.service.UserService;
import com.shenmao.archive_management_system.utils.JwtUtil;
import com.shenmao.archive_management_system.utils.Md5Util;
import com.shenmao.archive_management_system.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //注册
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {

        //查询用户
        User u = userService.findByUsername(username);
        if (u == null) {
            //没有占用
            //注册
            userService.register(username, password);
            return Result.success();
        } else {
            //占用
            return Result.error("用户名已被占用");
        }
    }

    //登录
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password){
        //1.查询用户是否存在
        User loginUser = userService.findByUsername(username);
        if (loginUser == null){
            return Result.error("用户名错误");
        }
        //2.查询密码是否正确
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())){
            //3.登陆成功，获取登陆令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);

            //把token存进redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, 12, TimeUnit.HOURS);


            return Result.success(token);
        }
        return Result.error("密码错误");
    }


    //获取用户详情
    @GetMapping("/userInfo")
    public Result<User> userInfo(){
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUsername(username);
        return Result.success(user);
    }

    //更新昵称
    @PutMapping("/update")
    public Result update(String nickname){
        userService.update(nickname);
        return Result.success();
    }

    //更新头像
    @PutMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    //更新密码
    @PutMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token){
        //校验参数
        String oldPwd = params.get("old_Pwd");
        String newPwd = params.get("new_Pwd");
        String rePwd = params.get("re_Pwd");
        if (!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要参数");
        }
        //查询原密码
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUsername(username);
        //匹配上了
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码错误");
        }

        if (!newPwd.equals(rePwd)){
            return Result.error("两次密码不同");
        }
        userService.updatePwd(newPwd);
        //从redis删除旧token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        RedisOperations<String, String> op = operations.getOperations();
        op.delete(token);

        //调用service密码更新
        return Result.success();
    }

    @PutMapping("/status")
    public Result updateStatus(@RequestParam String id ,@RequestParam String status){
        userService.updateStatus(id, status);
        return Result.success();
    }
















}