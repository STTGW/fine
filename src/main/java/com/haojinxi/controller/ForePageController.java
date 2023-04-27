package com.haojinxi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ForePageController {

    //跳转到注册页面
    @GetMapping("/registerPage")
    public String registerPage() { return "/fore/register"; }

    //跳转到注册成功页面
    @GetMapping("/registerSuccess")
    public String registerSuccess(){ return "/fore/registerSuccess"; }

    /*跳转到登录页面*/
    @GetMapping("/login")
    public String login(){ return "/fore/login"; }

    /*跳转到home页面*/
    @GetMapping({"/home","/","null"})
    public String home(){ return "/fore/home"; }

    /*跳转到产品详情页*/
    @GetMapping(value="/product")
    public String product(){ return "fore/product"; }

    /*跳转到购物车页面*/
    @GetMapping("/cart")
    public String cart(){ return "fore/cart"; }

    /*跳转到创建订单页面*/
    @GetMapping("/buy")
    public String buy(){ return "/fore/buy"; }

    /*退出 forelogout*/
    @GetMapping("/forelogout")
    public String forelogout(HttpSession session){
        session.invalidate();//清空session
        return "redirect:/home";
    }

    /*跳转到支付页面*/
    @GetMapping("/alipay")
    public String aliPay(){ return "/fore/alipay"; }

    /*跳转到我的订单页面*/
    @GetMapping("/bought")
    public String bought(){ return "/fore/bought"; }

    /*跳转到确认收货页面*/
    @GetMapping("/confirmPay")
    public String confirmPay(){ return "/fore/confirmPay"; }

    /*跳转到确认支付*/
    @GetMapping("/orderConfirmed")
    public String orderConfirmed(){ return "/fore/orderConfirmed"; }


    /*跳转到评价页面*/
    @GetMapping("/review")
    public String review(){ return "/fore/review"; }

    /*跳转到搜索页面*/
    @GetMapping("/search")
    public String search(){ return "/fore/search"; }

    /*跳转到分类页*/
    @GetMapping("/category")
    public String category(){ return "/fore/category"; }
}
