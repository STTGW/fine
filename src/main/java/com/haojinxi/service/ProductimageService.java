package com.haojinxi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haojinxi.entity.Product;
import com.haojinxi.entity.Productimage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haojinxi
 * @since 2023-03-05
 */
public interface ProductimageService extends IService<Productimage> {

    public static final String type_single = "single";

    Productimage getPImgByPid(Integer pid);

    public void setSingleImg(Product product);

    void fillFirstImage(List<Product> products);
}
