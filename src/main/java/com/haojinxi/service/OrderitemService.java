package com.haojinxi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haojinxi.entity.Orderitem;
import com.haojinxi.entity.Product;
import com.haojinxi.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haojinxi
 * @since 2023-03-05
 */
public interface OrderitemService extends IService<Orderitem> {
    void setSalCount(Product product);

    int getCartNumber(User user);

    void saveOrderItem(Orderitem orderitem);

    List<Orderitem> queryAll(User user);

    void fillPorduct(List<Orderitem> orderitems);

    double getTotal(List<Orderitem> orderitems);

    List<Orderitem> queryOrderItems(String oiid);

    Product getProduct(Integer oid);
}
