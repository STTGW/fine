package com.haojinxi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haojinxi.entity.Orderitem;
import com.haojinxi.entity.User;
import com.haojinxi.result.Result;
import com.haojinxi.service.OrderitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author haojinxi
 * @since 2023-03-05
 */
@RestController
public class OrderitemController {

    @Autowired
    private OrderitemService orderitemService;


    /*加入购物车前判断用户是否登录  forecheckLogin*/
    @GetMapping("/forecheckLogin")
    public Result forecheckLogin(HttpSession session){
        User user = (User) session.getAttribute("user");
        return user == null? Result.fail("未登录"): Result.success();
    }

    /*添加购物车 "foreaddCart?pid=" + pid + "&num=" + num;*/
    @GetMapping("/foreaddCart")
    public Result foreaddCart(Integer pid, Integer num, HttpSession session){
        Orderitem orderitem = new Orderitem();
        orderitem.setPid(pid);
        orderitem.setNumber(num);
        User user = (User) session.getAttribute("user");
        orderitem.setUid(user.getId());

        orderitemService.saveOrderItem(orderitem);

        //统计购物车中物品数量
        int count = orderitemService.getCartNumber(user);
        return  Result.success(count);
    }

    /*显示购物车中的数据  forecart axios.get(url).then(function (response) {
                        vue.orderItems = response.data;*/
    @GetMapping("/forecart")
    public List<Orderitem> forecart(HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Orderitem> orderitems = orderitemService.queryAll(user);
        /*填充产品*/
        orderitemService.fillPorduct(orderitems);
        return orderitems;
    }

    //http://localhost:8080/forechangeOrderItem?pid=901&num=2
    /*修改购物车中商品数量*/
    @GetMapping("/forechangeOrderItem")
    public Result forechangeOrderItem(Integer pid, Integer num, HttpSession session){
        Orderitem orderitem = new Orderitem();
        orderitem.setPid(pid);
        orderitem.setNumber(num);
        User user = (User) session.getAttribute("user");
        QueryWrapper<Orderitem> wrapper = new QueryWrapper<>();
        wrapper.eq("pid",pid)
                .eq("uid",user.getId())
                .isNull("oid");
        orderitemService.update(orderitem,wrapper);
        return Result.success();
    }

    /*删除购物车物品*/
    @GetMapping("/foredeleteOrderItem")
    public Result foredeleteOrderItem(Integer oiid){
        orderitemService.removeById(oiid);
        return Result.success();
    }

    //订单页获取购物车产品信息
    @GetMapping("/forebuy")
    public Result forebuy(String oiid, HttpSession session){
        List<Orderitem> orderitems = orderitemService.queryOrderItems(oiid);
        //填充产品
        orderitemService.fillPorduct(orderitems);
        double total = orderitemService.getTotal(orderitems);
        Map<String,Object> data = new HashMap<>();
        data.put("total",total);
        data.put("orderItems",orderitems);
        //把订单明细id保存到session
        session.setAttribute("orderItems",orderitems);
        return  Result.success(data);
    }
}

