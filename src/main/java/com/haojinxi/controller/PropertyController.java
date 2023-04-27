package com.haojinxi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haojinxi.entity.Property;
import com.haojinxi.result.Result;
import com.haojinxi.service.ProductService;
import com.haojinxi.service.PropertyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private ProductService productService;

    //查询属性分页功能
    @GetMapping("/categories/{cid}/properties")
    public PageInfo<Property> propertyList(@PathVariable("cid") Integer cid,
                                           @RequestParam(value = "start",required = true,defaultValue = "1") Integer start,
                                           @RequestParam(value = "size",required = true,defaultValue = "5") Integer size){
        PageHelper.startPage(start,size);
        QueryWrapper<Property> wrapper = new QueryWrapper<>();
        wrapper.eq("cid",cid);
        List<Property> propertyList = propertyService.list(wrapper);
        PageInfo<Property> pageInfo = new PageInfo<>(propertyList);
        log.info("pageInfo1:"+pageInfo);
        return pageInfo;
    }

    //POST 增加属性
    @PostMapping("/properties")
    public void addProperty(@RequestBody Property property){
        log.info("property2:"+property);
        //id=0, cid=null, name=bbb, category=Category(id=83, name=null)
        //category的id赋给Property的cid
        property.setCid(property.getCategory().getId());
        propertyService.save(property);
    }


    /*删除属性名称*/
    @DeleteMapping("/properties/{id}")
    public void deleteProperty(@PathVariable("id") Integer id){
        propertyService.removeById(id);
    }

    /*根据id获取数据*/
    @GetMapping("/properties/{id}")
    public Property findById(@PathVariable("id") Integer id){
        Property byId = propertyService.getById(id);
//        byId.getCategory()
        return byId;

    }

    /*根据id更新属性名称location.href = vue.listURL + "?cid=" + vue.category.id;*/
    @PutMapping("/properties/{id}")
    public Result updateProperty(@RequestBody Property property){
        Integer cid = property.getCid();
        HashMap<String, Object> obj = new HashMap<>();
        obj.put("cid",cid);
        propertyService.saveOrUpdate(property);

        return Result.success(obj);
    }


}

