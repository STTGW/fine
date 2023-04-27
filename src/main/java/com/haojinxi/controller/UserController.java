package com.haojinxi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haojinxi.entity.Category;
import com.haojinxi.entity.User;
import com.haojinxi.result.Result;
import com.haojinxi.service.OrderitemService;
import com.haojinxi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author haojinxi
 * @since 2023-03-05
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderitemService orderitemService;

    //用户注册
    @PostMapping("/foreregister")
    public Result regist(@RequestBody User user){
        log.info("user:"+user);
        //1.判断用户名是否存在
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name",user.getName());
        User user1 = userService.getOne(wrapper);
        if(user1 !=null){//用户已存在
            return  Result.fail("用户已存在");
        }else{
            //2.保存用户
            //对user进行加盐加密
            //盐是随机数    63a9f0ea7bb98050796b649e85481845
            String salt = UUID.randomUUID().toString();
            //12363a9f0ea7bb98050796b649e85481845
            String md5Pwd = user.getPassword()+salt;
            //MD5加密
            md5Pwd= DigestUtils.md5DigestAsHex(md5Pwd.getBytes());
            user.setPassword(md5Pwd);
            user.setSalt(salt);
            userService.save(user);
            return Result.success();
        }
    }

    //用户登录
    @PostMapping("/forelogin")
    public Result login(@RequestBody User user, HttpSession session){
        //1.根据用户名拿到盐
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name",user.getName());
        User user1 = userService.getOne(wrapper);
        if(user1 == null) return Result.fail("信息有误");
        //2.对密码进行加盐加密
        String md5Pwd =DigestUtils.md5DigestAsHex((user.getPassword()+user1.getSalt()).getBytes());
        user.setPassword(md5Pwd);
        //3.判断用户名和密码是否正确
        wrapper = new QueryWrapper<>();
        wrapper.eq("name",user.getName())
                .eq("password",user.getPassword());
        User user2 =userService.getOne(wrapper);
        if(user2 == null){
            return Result.fail("你的信息有误");
        }else{
            //把user2存入session
            session.setAttribute("user",user2);

            //统计购物车数量 session.cartTotalItemNumber
            int count = orderitemService.getCartNumber(user2);
            session.setAttribute("cartTotalItemNumber",count);


            String path = (String) session.getAttribute("currentPath");
            return Result.success(path);

            //统计购物车数量 session.cartTotalItemNumber
//            int count = orderitemService.getCartNumber(user2);
//            session.setAttribute("cartTotalItemNumber",count);
//            String path = (String) session.getAttribute("currentPath");
//            return Result.success();
        }

    }

    //后台查询所有用户
    @GetMapping("/users")
    public PageInfo<User> list(@RequestParam(value = "start",required = true,defaultValue = "1") Integer start,
                                   @RequestParam(value = "size",required = true,defaultValue = "3") Integer size,
                                   HttpServletResponse response){
        PageHelper.startPage(start,size);

        List<User> users = userService.list();
        PageInfo<User> pageInfo = new PageInfo<>(users);

        return  pageInfo;
    }

}

