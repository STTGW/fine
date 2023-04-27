package com.haojinxi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AdminPageController {

    //后台访问跳转到首页
    @GetMapping("/admin")
    public String adminPage() {
        return "forward:admin_category_list";
    }

    //后台接受请求转发跳转后台首页
    @GetMapping("/admin_category_list")
    public String categoryList(){
        return "/admin/listCategory";
    }

    //后台跳转编辑分类页面
    @GetMapping("/admin_category_edit")
    public String categoryEdit(){ return "/admin/editCategory"; }

    //跳转属性管理页面
    @GetMapping("/admin_property_list")
    public String propergyList(){ return "/admin/listProperty"; }

    /*跳转到属性编辑页面*/
    @GetMapping("/admin_property_edit")
    public String admin_property_edit(){return "/admin/editProperty";}

    /*跳转产品管理页面*/
    @GetMapping("/admin_product_list")
    public String productList(){ return "/admin/listProduct"; }

    /*跳转到产品编辑页面*/
    @GetMapping("/admin_product_edit")
    public String admin_product_edit(){return "/admin/editProduct";}

    /*跳转到产品设置属性页面*/
    @GetMapping("/admin_propertyValue_edit")
    public String admin_propertyValue_edit(){return "/admin/editPropertyValue";}

    /*跳转到产品图片列表*/
    @GetMapping("/admin_productImage_list")
    public String admin_productImage_list(){return "/admin/listProductImage";}

    /*跳转到订单管理*/
    @GetMapping("/admin_order_list")
    public String admin_order_list(){ return "/admin/listOrder"; }

    /*跳转到用户管理*/
    @GetMapping("/admin_user_list")
    public String admin_user_list(){return "/admin/listUser";}
}
