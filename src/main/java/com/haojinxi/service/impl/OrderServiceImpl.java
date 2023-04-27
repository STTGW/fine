package com.haojinxi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haojinxi.entity.Order;
import com.haojinxi.entity.Orderitem;
import com.haojinxi.entity.User;
import com.haojinxi.mapper.OrderMapper;
import com.haojinxi.mapper.OrderitemMapper;
import com.haojinxi.mapper.UserMapper;
import com.haojinxi.service.OrderService;
import com.haojinxi.service.OrderitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haojinxi
 * @since 2023-03-05
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderitemMapper orderitemMapper;

    @Autowired
    private OrderitemService orderitemService;

    @Autowired
    private UserMapper userMapper;

    public static final String waitPay = "waitPay";
    public static final String waitDelivery = "waitDelivery";
    public static final String waitConfirm = "waitConfirm";
//    public static final String waitReview = "waitReview";
    public static final String finish = "finish";
    public static final String delete = "delete";

    @Transactional
    @Override
    public void saveOrderAndOrderItem(Order order, HttpSession session) {
        //1.创建订单编号
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String orderCode = sdf.format(new Date());
        Random random = new Random();
        for(int i = 0;i < 4;i++){
            orderCode+=random.nextInt(10);
        }
        //2.使用mybatisplus自动填充功能
        //3.保存订单
        User user = (User) session.getAttribute("user");
        order.setOrderCode(orderCode);
        order.setUid(user.getId());
        order.setStatus(OrderServiceImpl.waitPay);
        orderMapper.insert(order);
        //3.更新订单详情表中的oiid
        List<Orderitem> orderitems = (List<Orderitem>) session.getAttribute("orderItems");
        for (Orderitem orderitem : orderitems) {
            orderitem.setOid(order.getId());
            orderitemMapper.updateById(orderitem);
        }
    }

    @Override
    public void fillOrderItems(List<Order> orders, User user) {
        for (Order order : orders) {
            QueryWrapper<Orderitem> wrapper = new QueryWrapper<>();
            wrapper.eq("oid",order.getId())
                    .eq("uid",user.getId());
            List<Orderitem> orderitems = orderitemMapper.selectList(wrapper);
            //明细对应的产品
            orderitemService.fillPorduct(orderitems);
            order.setOrderItems(orderitems);
            //每个订单中产品的数量totalNumber
            int totalNumber = 0;
            for (Orderitem orderitem : orderitems) {
                totalNumber+=orderitem.getNumber();
            }
            order.setTotalNumber(totalNumber);
            order.setTotal(orderitemService.getTotal(orderitems));
        }
    }

    @Override
    public void fillUser(List<Order> orders) {
        for (Order order : orders) {
            User user = userMapper.selectById(order.getUid());
            order.setUser(user);
        }

    }

    @Override
    public void fillOrderItems(List<Order> orders) {
        for (Order order : orders) {
            QueryWrapper<Orderitem> wrapper = new QueryWrapper<>();
            wrapper.eq("oid",order.getId())
                    .eq("uid",order.getUid());
            List<Orderitem> orderitems = orderitemMapper.selectList(wrapper);
            //明细对应的产品
            orderitemService.fillPorduct(orderitems);
            order.setOrderItems(orderitems);
            //每个订单中产品的数量totalNumber
            int totalNumber = 0;
            for (Orderitem orderitem : orderitems) {
                totalNumber+=orderitem.getNumber();
            }
            order.setTotalNumber(totalNumber);
            order.setTotal(orderitemService.getTotal(orderitems));
        }
    }
}
