package com.healthcare.controller;

import com.healthcare.config.RandCode;
import com.healthcare.entity.Roles;
import com.healthcare.entity.TUser;
import com.healthcare.mapper.RolesMapper;
import com.healthcare.mapper.TUserMapper;
import com.healthcare.response.Result;
import com.healthcare.response.ResultCode;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    TUserMapper tUserMapper;
    @Autowired
    RolesMapper RoleMapper;
    @Autowired
    private JavaMailSender mailSender;
    RandCode r = new RandCode();

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 注册界面
     *
     * @return
     */
    @PostMapping("/registerUser")
    public Result registerUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (tUserMapper.getOneUser(username) != null) {
            Result result = Result.error(ResultCode.USER_ACCOUNT_ALREADY_EXIST);
            return result;
        }
        TUser tUser = new TUser();
        tUser.setUsername(username);
        tUser.setPassword(passwordEncoder.encode(password));
        Roles tRole = new Roles();
        tRole.setUsername(username);
        tRole.setRole("ROLE_user");
        tUserMapper.insert(tUser);
        RoleMapper.insert(tRole);
        Result result = Result.ok().message("user注册成功");
        return result;
    }

    @ApiOperation("按照用户名和积分变化量更改积分属性，负数为减，正数为加,成功后message返回“更改成功”,积分异常返回“积分不足”")
    @PostMapping("/changePoint")
    public Result changePoint(@ApiParam("用户名") @RequestParam("username") String username,
                              @ApiParam("更改积分数") @RequestParam("point") int point) {

        if (tUserMapper.getOneUser(username) == null) {
            Result result = Result.ok().message("用户不存在");
            return result;
        }
        TUser tUser = tUserMapper.getOneUser(username);
        Integer id = tUser.getId();
        int point1 = tUser.getPoint();
        if (point+point1>=0){
            point+=point1;
            tUserMapper.changePoint(point,id);
            Result result = Result.ok().message("更改成功");
            return result;
        }else {
            Result result = Result.ok().message("积分不足");
            return result;
        }
    }

    @PreAuthorize("hasRole('admin')")//只有admin才可
    @PostMapping("/getUser")
    public Result getUser(@RequestParam("username") String username) {
        Result result = Result.ok().message("success");
        result.data("user_information", tUserMapper.getOneUser(username));
        return result;
    }

    @PreAuthorize("hasRole('admin')")//只有admin才可
    @PostMapping("/getAllUser")
    public Result getAllUser() {
        Result result = Result.ok().message("success");
        result.data("user_information", tUserMapper.getAllUser());
        return result;
    }

    @PreAuthorize("hasRole('admin')")//只有admin才可以注册admin
    @PostMapping("/registerAdmin")
    public Result registerAdmin(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (tUserMapper.getOneUser(username) != null) {
            Result result = Result.error(ResultCode.USER_ACCOUNT_ALREADY_EXIST);
            return result;
        }
        TUser tUser = new TUser();
        tUser.setUsername(username);
        tUser.setPassword(passwordEncoder.encode(password));
        Roles tRole = new Roles();
        tRole.setUsername(username);
        tRole.setRole("ROLE_admin");
        tUserMapper.insert(tUser);
        RoleMapper.insert(tRole);
        Result result = Result.ok().message("admin注册成功");
        return result;
    }

    @PostMapping("/mail")
    public Result sendMail(@RequestParam("email") String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        String randcode = r.generateFixedLengthNum(6);//6位数验证码
        message.setFrom("1249237461@qq.com");
        message.setTo(email);
        message.setSubject("CityGarbage验证码");
        message.setText("你好，我是CityGarbage，这是你的验证码：" + randcode + "  。请不要告诉任何人。");
        mailSender.send(message);
        Result result = Result.ok().message("邮件已发送");
        result.data("RandCode", randcode);
        return result;
    }


}
