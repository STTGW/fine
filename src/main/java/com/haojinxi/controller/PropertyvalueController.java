package com.haojinxi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haojinxi.entity.Product;
import com.haojinxi.entity.Property;
import com.haojinxi.entity.Propertyvalue;
import com.haojinxi.mapper.PropertyMapper;
import com.haojinxi.mapper.PropertyvalueMapper;
import com.haojinxi.service.CategoryService;
import com.haojinxi.service.ProductService;
import com.haojinxi.service.PropertyService;
import com.haojinxi.service.PropertyvalueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author haojinxi
 * @since 2023-03-05
 */
@RestController
//@RequestMapping("/propertyvalue")
public class PropertyvalueController {

    @Autowired
    PropertyvalueService propertyvalueService;

    @Autowired
    ProductService productService;

    @Autowired
    PropertyvalueMapper propertyvalueMapper;

    @Autowired
    CategoryService categoryService;

    @Autowired
    PropertyService propertyService;

    @Autowired
    PropertyMapper propertyMapper;

    //回显属性和属性值
    @GetMapping("/products/{pid}/propertyValues")
    public List<Propertyvalue> list(@PathVariable("pid") int pid) {
        Product p = productService.getById(pid);

        propertyvalueService.init(p);
        QueryWrapper<Propertyvalue> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", p.getId());
        List<Propertyvalue> propertyvalues = propertyvalueService.list(wrapper);
        for (int i = 0; i < propertyvalues.size(); i++) {
            int ptid = propertyvalues.get(i).getPtid();
            Property property = propertyMapper.selectById(ptid);
            propertyvalues.get(i).setProperty(property);

        }
        return propertyvalues;

    }

    //修改属性
    @PutMapping("/propertyValues")
    public Object update(@RequestBody Propertyvalue bean) {
        propertyvalueService.saveOrUpdate(bean);
        return bean;

    }

}

