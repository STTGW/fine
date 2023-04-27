package com.haojinxi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haojinxi.entity.Product;
import com.haojinxi.entity.Review;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haojinxi
 * @since 2023-03-05
 */
public interface ReviewService extends IService<Review> {

    void setReviewsAndCount(Product product);

    void setReviewUser(Product product);

    void saveAndUpdateStatus(Review review, Integer oid);
}
