package com.haojinxi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haojinxi.entity.Orderitem;
import com.haojinxi.entity.Product;
import com.haojinxi.entity.Productimage;
import com.haojinxi.entity.User;
import com.haojinxi.mapper.OrderitemMapper;
import com.haojinxi.mapper.ProductMapper;
import com.haojinxi.service.OrderitemService;
import com.haojinxi.service.ProductimageService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OrderitemServiceImpl extends ServiceImpl<OrderitemMapper, Orderitem> implements OrderitemService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductimageService productimageService;

    @Autowired
    private OrderitemMapper orderitemMapper;

    @Override
    public void setSalCount(Product product) {
        QueryWrapper<Orderitem> wrapper = new QueryWrapper<>();
        wrapper.eq("pid",product.getId());
        List<Orderitem> orderitems = this.list(wrapper);
        int count = 0;
        for (Orderitem orderitem : orderitems) {
            count+=orderitem.getNumber();
        }
        product.setSaleCount(count);
    }

    @Override
    public int getCartNumber(User user) {
        QueryWrapper<Orderitem> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",user.getId())
                .isNull("oid");
        List<Orderitem> orderitems = this.list(wrapper);
        int count = 0;
        for (Orderitem orderitem : orderitems) {
            count+=orderitem.getNumber();
        }
        return count;
    }

    @Override
    public void saveOrderItem(Orderitem orderitem) {
        //1.根据pid 和 uid查找是否有数据
        QueryWrapper<Orderitem> wrapper = new QueryWrapper<>();
        wrapper.isNull("oid")
                .eq("pid",orderitem.getPid())
                .eq("uid",orderitem.getUid());
        Orderitem one = this.getOne(wrapper);
        if(one == null){
            this.save(orderitem);
        }else{
            int newNum = one.getNumber()+orderitem.getNumber();
            orderitem.setNumber(newNum);
            this.update(orderitem,wrapper);
        }
    }

//    @Override
//    public List<Orderitem> queryAll(User user) {
//        QueryWrapper<Orderitem> wrapper = new QueryWrapper<>();
//        wrapper.isNull("oid")
//                .eq("uid",user.getId());
//        return this.list(wrapper);
//    }

    @Override
    public List<Orderitem> queryAll(User user) {

        return orderitemMapper.queryAll(user);
    }

    @Override
    public void fillPorduct(List<Orderitem> orderitems) {
        for (Orderitem orderitem : orderitems) {
            Product product= productMapper.selectById(orderitem.getPid());
            product.setFirstProductImage(productimageService.getPImgByPid(product.getId()));
            orderitem.setProduct(product);
        }
    }

    @Override
    public double getTotal(List<Orderitem> orderitems) {
        double total = 0.0;
        for (Orderitem orderitem : orderitems) {
            Product product = orderitem.getProduct();
            total+= product.getPromotePrice()*orderitem.getNumber();

        }
        return total;
    }

    @Override
    public List<Orderitem> queryOrderItems(String oiid) {
        return orderitemMapper.queryOrderItems(oiid);
    }

    @Override
    public Product getProduct(Integer oid) {
        QueryWrapper<Orderitem> wrapper = new QueryWrapper<>();
        wrapper.eq("oid",oid)
                .last("limit 1");
        Orderitem orderitem = orderitemMapper.selectOne(wrapper);
        Product product = productMapper.selectById(orderitem.getPid());
        Productimage pImgByPid = productimageService.getPImgByPid(orderitem.getPid());
        product.setFirstProductImage(pImgByPid);
        return product;
    }

}
