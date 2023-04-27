package com.haojinxi.controller;

import com.haojinxi.entity.Category;
import com.haojinxi.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    //前台首页分类
    @GetMapping("/forehome")
    public List<Category> getHomeData(HttpServletRequest request){
        List<Category> categories = categoryService.list();
        categoryService.fillProduct(categories);
        categoryService.fillProductsByRow(categories);
        log.info("categories:"+categories);
        return categories;
    }
}
