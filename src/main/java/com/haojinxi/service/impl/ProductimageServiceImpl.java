package com.haojinxi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haojinxi.entity.Product;
import com.haojinxi.entity.Productimage;
import com.haojinxi.mapper.ProductimageMapper;
import com.haojinxi.service.ProductimageService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haojinxi
 * @since 2023-03-05
 */
@Service
public class ProductimageServiceImpl extends ServiceImpl<ProductimageMapper, Productimage> implements ProductimageService {

    /*根据产品id获取产品对应的第一张图片*/
    public Productimage getPImgByPid(Integer pid){
        QueryWrapper<Productimage> wrapper = new QueryWrapper<>();
        wrapper.eq("pid",pid)
                .eq("type","single")
                .last("limit 1");
        return this.getOne(wrapper);
    }



    /*获取产品对应的缩略图*/
    public void setSingleImg(Product product){
        QueryWrapper<Productimage> wrapper = new QueryWrapper<>();
        wrapper.eq("pid",product.getId())
                .eq("type","single")
                .last("limit 5");
        product.setProductSingleImages(this.list(wrapper));
    }

    /*填充产品第一张图片*/
    @Override
    public void fillFirstImage(List<Product> products) {
        for (Product product : products) {
            product.setFirstProductImage(getPImgByPid(product.getId()));
        }
    }







}
