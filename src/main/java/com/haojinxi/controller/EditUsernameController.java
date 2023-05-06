package com.haojinxi.controller;

import com.haojinxi.entity.User;
import com.haojinxi.service.UserService;
import org.apache.coyote.http11.filters.VoidInputFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Controller
public class EditUsernameController {

    @Autowired
    HttpSession session;

    @Autowired
    UserService userService;

    @GetMapping("/getUsername")
    @ResponseBody
    public String getUsername(){
        User user = (User) session.getAttribute("user");
        String name = user.getName();
        return name;
    }


    @PostMapping("/editUsername")
    @ResponseBody
    public String editUsername(@RequestBody String newName) {
        try {
            newName = URLDecoder.decode(newName, StandardCharsets.UTF_8.name());
            newName = newName.replaceAll("=","");
            System.out.println(newName+"这是我的新名字");
            User user = (User) session.getAttribute("user");
            user.setName(newName);
            userService.saveOrUpdate(user);
            return "修改成功";
        }catch (Exception err){
            err.printStackTrace();
            return "修改失败";
        }

    }
}
