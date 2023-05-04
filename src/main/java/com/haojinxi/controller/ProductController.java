package com.haojinxi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haojinxi.entity.Category;
import com.haojinxi.entity.Product;
import com.haojinxi.result.Result;
import com.haojinxi.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author haojinxi
 * @since 2023-03-05
 */
@Slf4j
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductimageService productimageService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private PropertyvalueService propertyvalueService;

    @Autowired
    private OrderitemService orderitemService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private CategoryService categoryService;

    //后台产品分页查询
    @GetMapping("/categories/{cid}/products")
    public PageInfo<Product> productList(@PathVariable("cid") Integer cid,
                                         @RequestParam(value = "start",required = true,defaultValue = "1") Integer start,
                                         @RequestParam(value = "size",required = true,defaultValue = "5") Integer size){
        PageHelper.startPage(start,size);
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("cid",cid);
        List<Product> productList = productService.list(wrapper);

        for(int i = 0;i < productList.size();i++){
            productList.get(i).setFirstProductImage(
                    productimageService.getPImgByPid(productList.get(i).getId()));
        }

        PageInfo<Product> pageInfo = new PageInfo<>(productList);
        log.info("pageInfo1:"+pageInfo);
        return pageInfo;

    }

    //前台产品详情页
    @GetMapping("/foreproduct/{pid}")
    public Result getProductById(@PathVariable("pid") Integer pid){
        Product product = productService.getById(pid);
        //设置产品对应的分类
        product.setCategory(new Category(product.getCid()));
//        //设置第一张图片
        product.setFirstProductImage(productimageService.getPImgByPid(product.getId()));
//        //设置缩略图
        productimageService.setSingleImg(product);
        /*设置评价和评价总数*/
//        reviewService.setReviewsAndCount(product);

        /*设置评价用户*/
//        reviewService.setReviewUser(product);

        /*设置产品属性*/
        propertyvalueService.setPv(product);

        /*设置销量*/
        orderitemService.setSalCount(product);
        return Result.success(product);


    }

    // foresearch  var url = this.uri + "?keyword=" + keyword;
    //前台搜索框查商品
    @PostMapping("/foresearch")
    public List<Product> foresearch(String keyword){
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.like("name",keyword).or().like("subTitle",keyword);
        List<Product> products = productService.list(wrapper);
        //填充第一张图片
        productimageService.fillFirstImage(products);
        for (Product product : products) {
            //填充销量
            orderitemService.setSalCount(product);
            //填充评价
//            reviewService.setReviewsAndCount(product);
        }
        return products;
    }


    /*新增产品*/
    @PostMapping("/products")
    public void addProducts(@RequestBody Product product){
        log.info("product2:"+product);
        product.setCid(product.getCategory().getId());
        productService.save(product);

    }

    /*删除产品*/
    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable("id") Integer id){
        productService.removeById(id);

    }

    /*后台根据id获取数据*/
    @GetMapping("/products/{id}")
    public Product findById(@PathVariable("id") Integer id){

        Product byId = productService.getById(id);
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("id", byId.getCid());
        Category one = categoryService.getOne(wrapper);
        byId.setCategory(one);
        return byId;
    }

    /*根据id更改产品*/
    @PutMapping("/products/{id}")
    public Result updateProperty(@RequestBody Product Product, HttpServletRequest request){
        System.out.println("测试一下"+ request.getRequestURI());
        Integer cid = Product.getCid();
        HashMap<String, Object> obj = new HashMap<>();
        obj.put("cid",cid);
        productService.saveOrUpdate(Product);
        System.out.println("测试一下"+ request.getRequestURI());

        return Result.success(obj);
    }
}

