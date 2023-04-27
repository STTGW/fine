package com.haojinxi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haojinxi.entity.Order;
import com.haojinxi.entity.User;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haojinxi
 * @since 2023-03-05
 */
public interface OrderService extends IService<Order> {

    void saveOrderAndOrderItem(Order order, HttpSession session);

    void fillOrderItems(List<Order> orders, User user);

    void fillUser(List<Order> list);

    void fillOrderItems(List<Order> orders);
}
