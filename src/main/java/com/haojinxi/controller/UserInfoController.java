package com.haojinxi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haojinxi.entity.Propertyvalue;
import com.haojinxi.entity.User;
import com.haojinxi.entity.UserInfo;
import com.haojinxi.mapper.UserinfoMapper;
import com.haojinxi.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class UserInfoController {

    @Autowired
    HttpSession session;

    @Autowired
    UserinfoMapper userinfoMapper;

    @Autowired
    UserInfoService userInfoService;

    //返回用户信息
    @GetMapping("/getUserInfo")
    @ResponseBody
    public UserInfo getUserInfo(){
        //根据session查用户
        User user = (User) session.getAttribute("user");
        Integer id = user.getId();
        //根据用户id查询userinfo
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",id);
//        UserInfo userInfo = userinfoMapper.selectById(id);
        UserInfo userInfo = userinfoMapper.selectOne(wrapper);

        //新增初始化
        if (userInfo == null) {            //已获取的用户信息对象可能是null，如果是null就初始化,向数据库插入记录
            userInfo = new UserInfo();
            userInfo.setUid(id);
            userInfo.setContent("简介");
            userInfo.setSex("性别");
            userInfo.setRealname("真实姓名");
            userInfoService.save(userInfo);
        }

        return  userInfo;

    }

    //修改信息
    @PostMapping("/editUserinfo")
    @ResponseBody
    public String editUserinfo(@RequestBody Map<String, String> map){

//        //根据session查用户
        User user = (User) session.getAttribute("user");
        Integer id = user.getId();

        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",id);
        UserInfo userInfo = userInfoService.getOne(wrapper);
        System.out.println(userInfo);
        Integer id1 = userInfo.getId();
        Integer uid = userInfo.getUid();

        String content = map.get("content");
        String sex = map.get("sex");
        String realname = map.get("realname");

        UserInfo userInfo1 = new UserInfo();
        userInfo1.setContent(content);
        userInfo1.setSex(sex);
        userInfo1.setRealname(realname);
        userInfo1.setId(id1);
        userInfo1.setUid(uid);







       userInfoService.saveOrUpdate(userInfo1);
       return "修改成功";
    }
}
