package com.haojinxi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haojinxi.entity.Category;
import com.haojinxi.entity.Product;
import com.haojinxi.mapper.CategoryMapper;
import com.haojinxi.mapper.ProductMapper;
import com.haojinxi.service.CategoryService;
import com.haojinxi.service.OrderitemService;
import com.haojinxi.service.ProductimageService;
import com.haojinxi.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductimageService productimageService;

    @Autowired
    private OrderitemService orderitemService;

    @Autowired
    private ReviewService reviewService;

    @Override
    public void fillProduct(List<Category> categories) {
        //根据分类id找出对应产品
        for(int i=0;i<categories.size();i++){
            int cid = categories.get(i).getId();
            QueryWrapper<Product> wrapper = new QueryWrapper<>();
            wrapper.eq("cid",cid);
            List<Product> products = productMapper.selectList(wrapper);
            fillFirstImage(products);
            categories.get(i).setProducts(products);
        }
    }

    @Override
    public void fillProductsByRow(List<Category> categories) {
        int productNumberEachRow = 3;
        // 1 2 3 4 5 6 7
        for (Category category : categories) {
            List<Product> products =  category.getProducts();
            List<List<Product>> productsByRow =  new ArrayList<>();
            for (int i = 0; i < products.size(); i+=productNumberEachRow) {
                int size = i+productNumberEachRow;
                size= size>products.size()?products.size():size;
                List<Product> productsOfEachRow =products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            log.info("productsByRow:"+productsByRow);
            category.setProductsByRow(productsByRow);
        }
    }

    @Override
    public void fillCaProduct(Category category) {
        //1.根据id找出所有商品
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("cid",category.getId());
        List<Product> products = productMapper.selectList(wrapper);
        productimageService.fillFirstImage(products);
        category.setProducts(products);
    }

    @Override
    public void fillSalesAndReviews(Category category) {
        List<Product> products = category.getProducts();
        for (Product product : products) {
            orderitemService.setSalCount(product);
//            reviewService.setReviewsAndCount(product);
        }
    }

    @Override
    public void sortProduct(Category category, String sort) {
        List<Product> products = category.getProducts();
        //判断是否需要添加排序
        if(StringUtils.isEmpty(sort) || sort.equals("all") || sort.equals("null")){
            return;
        }else if(sort.equals("review")){
            Collections.sort(products, new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o2.getReviewCount() - o1.getReviewCount();
                }
            });
        }else if(sort.equals("saleCount")){
            Collections.sort(products, new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o2.getSaleCount() - o1.getSaleCount();
                }
            });
        }else if(sort.equals("price")){
            Collections.sort(products, new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return (int) (o1.getPromotePrice()-o2.getPromotePrice());
                }
            });
        }else if(sort.equals("date")){
            Collections.sort(products, new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    //o2.getCreatedate().isBefore(o1.getCreatedate())==true?-1:1;

//                    return o2.getCreatedate().compareTo(o1.getCreatedate());
                    return o2.getCreateDate().compareTo(o1.getCreateDate());
                }
            });
        }
    }

    public void fillFirstImage(List<Product> products){
        for (Product product : products) {
            product.setFirstProductImage(productimageService.getPImgByPid(product.getId()));
        }
    }
}
