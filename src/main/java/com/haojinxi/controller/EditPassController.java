package com.haojinxi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haojinxi.entity.EditPassword;
import com.haojinxi.entity.User;
import com.haojinxi.result.Result;
import com.haojinxi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;

@Controller
public class EditPassController {
    @Autowired
    UserService userService;


    //用户更新密码
    @PostMapping("/editPassword")
    @ResponseBody
    public String editPassword(HttpSession session, @RequestBody Map<String, String> map) {

        String oldPass1 = map.get("oldPass");
        System.out.println(oldPass1 + "1edit");
        String newPass1 = map.get("newPass");

        System.out.println(oldPass1 + "nihao");
        System.out.println(newPass1 + "nihao");


        //1.根据session获取用户
        User user = (User) session.getAttribute("user");

        System.out.println(user.getPassword() + "用户的密码");
        System.out.println(DigestUtils.md5DigestAsHex((oldPass1 + user.getSalt()).getBytes()) + "我是前台加密的老密码");
        System.out.println(user.getPassword() + "我是数据库老密码");



        if (!DigestUtils.md5DigestAsHex((oldPass1 + user.getSalt()).getBytes()).equals(user.getPassword())) {
            return "输入原密码错误";
        } else {
            String s1 = DigestUtils.md5DigestAsHex((newPass1 + user.getSalt()).getBytes());
            user.setPassword(s1);
            System.out.println(user.getPassword());
            userService.saveOrUpdate(user);
            return "修改成功";
        }
    }


    }



//}
