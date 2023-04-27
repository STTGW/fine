package com.haojinxi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haojinxi.entity.Order;
import com.haojinxi.entity.Orderitem;
import com.haojinxi.entity.Product;
import com.haojinxi.entity.User;
import com.haojinxi.result.Result;
import com.haojinxi.service.OrderService;
import com.haojinxi.service.OrderitemService;
import com.haojinxi.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
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
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderitemService orderitemService;

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/forecreateOrder")
    public Result forecreateOrder(@RequestBody Order order, HttpSession session){
        orderService.saveOrderAndOrderItem(order,session);
        double total = orderitemService.getTotal((List<Orderitem>) session.getAttribute("orderItems"));
        Map<String,Object> data = new HashMap<>();
        data.put("oid",order.getId());
        data.put("total",total);
        return Result.success(data);
    }

    //forepayed?oid=202201201015039967
    @GetMapping("/forepayed")
    public Order forepayed(String oid){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("orderCode",oid);
        return orderService.getOne(wrapper);
    }

    //前台我的订单
    @GetMapping("/forebought")
    public List<Order> forebought(HttpSession session){



//        List<Order> orders = orderService.list();
        //订单明细
        //明细对应的产品
        //每个订单中产品的数量totalNumber
        //总价total
        User user = (User) session.getAttribute("user");

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",user.getId());
        List<Order> orders = orderService.list(wrapper);

        orderService.fillOrderItems(orders,user);
        return orders;
    }

    /*获取后台订单数据*/
//http://localhost:8080/orders?start=0
    @GetMapping("/orders")
    public PageInfo<Order> list(@RequestParam(value = "start",required = true,defaultValue = "1") Integer start,
                                @RequestParam(value = "size",required = true,defaultValue = "5") Integer size){
        PageHelper.startPage(start,size);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Order> orders = orderService.list(wrapper);
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        //填充用户
        orderService.fillUser(pageInfo.getList());
        //订单明细
        //明细对应的产品
        //每个订单中产品的数量totalNumber
        //总价total
        orderService.fillOrderItems(pageInfo.getList());
        log.info("pageInfo:"+pageInfo);
        return  pageInfo;
    }

    /*修改发货状态*/
//PUT http://localhost:8080/deliveryOrder/4
    @PutMapping("/deliveryOrder/{id}")
    public Result deliveryOrder(@PathVariable("id") Integer id){
        //waitConfirm 确认收货
        Order order = new Order();
        order.setId(id);
        order.setDeliveryDate(new Date());
        order.setStatus("waitConfirm");
        orderService.updateById(order);
        return Result.success();
    }


    /*axios.min.js:8 GET http://localhost:8080/foreconfirmPay?oid=4 */
//根据id获取订单信息
    @GetMapping("/foreconfirmPay")
    public Order foreconfirmPay(Integer oid){
        Order order = orderService.getById(oid);
        return order;
    }

    /*GET http://localhost:8080/foreorderConfirmed?oid=4 */
//根据id修改为待评价状态
    @GetMapping("/foreorderConfirmed")
    public void foreorderConfirmed(Integer oid){
        Order order = new Order();
        order.setId(oid);
        order.setStatus("finish");
        order.setConfirmDate(new Date());
        orderService.updateById(order);
    }

    /*GET http://localhost:8080/forereview?oid=4 */
    @GetMapping("/forereview")
    public Result forereview(Integer oid){
        Order order = orderService.getById(oid);
        //获取产品
        Product product = orderitemService.getProduct(oid);
        //设置销量
        orderitemService.setSalCount(product);
//        //设置回复
        reviewService.setReviewsAndCount(product);
//        //设置回复用户
        reviewService.setReviewUser(product);
        Map<String,Object> data = new HashMap<>();
        data.put("p",product);
        data.put("o",order);
        data.put("reviews",product.getReviews());
        return  Result.success(data);
    }

    /*var uri = "foredeleteOrder?oid=" + deleteOrderid;
                axios.put(uri).then(function (response) {*/
    //删除订单
    @PutMapping("/foredeleteOrder")
    public Result foredeleteOrder(Integer oid){
        Order order = new Order();
        order.setId(oid);
        order.setStatus("delete");
        orderService.updateById(order);
        return Result.success();
    }

}

