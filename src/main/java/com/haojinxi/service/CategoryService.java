package com.haojinxi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.haojinxi.entity.Category;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author haojinxi
 * @since 2023-03-05
 */
public interface CategoryService extends IService<Category> {

    void fillProduct(List<Category> categories);

    void fillProductsByRow(List<Category> categories);

    void fillCaProduct(Category category);

    void fillSalesAndReviews(Category category);

    void sortProduct(Category category, String sort);
}
