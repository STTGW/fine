package com.haojinxi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haojinxi.entity.Product;
import com.haojinxi.entity.Propertyvalue;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haojinxi
 * @since 2023-03-05
 */
public interface PropertyvalueService extends IService<Propertyvalue> {

    void setPv(Product product);


    void init(Product p);
}
