package com.healthcare.controller;

import com.healthcare.entity.Bin;
import com.healthcare.response.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class adminController {
    @Autowired
    private JavaMailSender mailSender;

    @ApiOperation("(服务器运行时可能存在较长延迟)向指定收件人发送列表内垃圾桶信息，格式为 \"垃圾桶\"+index+\" 位于 \"+other thing+\" 垃圾量目前为 \"+level")
    @PostMapping("/mail")
    public Result sendMail(@ApiParam("收件人邮箱地址") @RequestParam("email") String email,@ApiParam("待处理垃圾桶列表") @RequestParam("BinList") List<Bin> BinList) {
        SimpleMailMessage message = new SimpleMailMessage();
        StringBuilder sb = new StringBuilder("你好，目前这些垃圾桶需要处理");
        Iterator<Bin> iterator = BinList.iterator();
        int index = 1;
        while (iterator.hasNext()) {
            Bin b1 = iterator.next();
            sb.append("垃圾桶"+index+" 位于 "+b1.getOtherThing()+" 垃圾量目前为 "+b1.getLevel()+"\n");
        }
        message.setFrom("1249237461@qq.com");
        message.setTo(email);
        message.setSubject("有垃圾桶待处理");
        message.setText(String.valueOf(sb));
        mailSender.send(message);
        Result result = Result.ok().message("邮件已发送");
        return result;
    }
}
