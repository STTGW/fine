package com.haojinxi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haojinxi.entity.Order;
import com.haojinxi.entity.Product;
import com.haojinxi.entity.Review;
import com.haojinxi.entity.User;
import com.haojinxi.mapper.OrderMapper;
import com.haojinxi.mapper.ReviewMapper;
import com.haojinxi.mapper.UserMapper;
import com.haojinxi.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review> implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;
    @Override
    public void setReviewsAndCount(Product product) {
        QueryWrapper<Review> wrapper = new QueryWrapper<>();
        wrapper.eq("pid",product.getId());
        List<Review> reviews = reviewMapper.selectList(wrapper);
        product.setReviews(reviews);
        product.setReviewCount(reviews.size());

    }

    @Override
    public void setReviewUser(Product product) {
        for(int i=0;i<product.getReviews().size();i++){
            User user = userMapper.selectById(product.getReviews().get(i));
            product.getReviews().get(i).setUser(user);
        }
    }


    @Transactional
    @Override
    public void saveAndUpdateStatus(Review review, Integer oid) {

        reviewMapper.insert(review);
        Order order = new Order();
        order.setId(oid);
        order.setStatus("finish");
        orderMapper.updateById(order);

    }
}

