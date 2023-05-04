package com.haojinxi.controller;


import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haojinxi.entity.Orderitem;
import com.haojinxi.entity.Product;
import com.haojinxi.entity.User;
import com.haojinxi.result.Result;
import com.haojinxi.service.OrderitemService;
import com.haojinxi.service.ProductService;
import com.haojinxi.service.ProductimageService;
import com.haojinxi.utils.IPUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

@Controller
public class ForePageController {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    ProductService productService;

    @Autowired
    OrderitemService orderitemService;

    @Autowired
    ProductimageService productimageService;




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
    @GetMapping({"/home","/","null","/deleteSession"})
    public String home(HttpServletRequest request)  {

        //通过ip实现定位
//        String ip = request.getRemoteAddr();
        String publicIP =null;
        try {
            URL url = new URL("http://checkip.amazonaws.com");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String publicIP1 = br.readLine();
            publicIP = publicIP1;
            System.out.println("Public IP Address: " + publicIP);
        } catch (Exception e) {
            e.printStackTrace();
        }



        String myString =IPUtil.iPPro(publicIP);
        servletContext.setAttribute("result",myString);
        System.out.println(publicIP+"vvv11111111111111111111111111");


        //统计在线人数

        HttpSession  session = request.getSession();
        servletContext.setAttribute("count",session.getServletContext().getAttribute("onlineCount"));



        return "/fore/home";
    }
//        @ResponseBody
//        @GetMapping("test")
//        public String test(HttpSession session){
//        session.setAttribute("user","zhangSan");
//
//        return "success";
//}

//    @GetMapping("/delete")
//    public String   deleteSession(HttpServletResponse response)throws IOException {
//        HttpSession session = request.getSession(false);
//        session.invalidate();
//        System.out.println(request.getClass());
//        if (session != null){
//            session.invalidate();}
////            session.setMaxInactiveInterval(0);
////        System.out.println(Thread.currentThread().getName()+"我进来了");
////        System.out.println(Thread.currentThread().getName()+"Session ID: "+session.getId());
////        session.setMaxInactiveInterval(0);
////        return "/fore/home";
////        response.sendRedirect("/test.html");
//
//
//        return "/fore/successaway";
//    }

//    @GetMapping("/newPage")
//    public String newPage(){
//        System.out.println("我又进来了");
//        return "/fore/successaway";
//    }





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


    /*对ip进行定位*/
//    @GetMapping("/ip")
//    @ResponseBody
//    public String ip(){
//
//        String s = IPUtil.iPPro(ip);
//
//
//        return s;
//    }


    /*跳转到为您推荐页面*/
    @GetMapping("/recommend")
    public String recommand(){return "recomluo.html";}

    /*首页推荐产品*/
    @GetMapping("/recom")
    @ResponseBody
    public List<Product> recommandFore(){

        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("promotePrice")
                .last("limit 12");

        List<Product> list = productService.list(wrapper);
        System.out.println("lallalalala"+list);
        productimageService.fillFirstImage(list);
        for (Product product : list) {
            //填充销量
            orderitemService.setSalCount(product);
        }


        return  list;
    }




    /*立即购买*/
    @GetMapping("/forebuyone")
    @ResponseBody
    public Integer foreaddCart(Integer pid, Integer num, HttpSession session, Model model) {
        Orderitem orderitem = new Orderitem();
        orderitem.setPid(pid);
        orderitem.setNumber(num);
        User user = (User) session.getAttribute("user");
        orderitem.setUid(user.getId());
        System.out.println("测试一下"+ request.getRequestURI());
        orderitemService.saveOrderItem(orderitem);

        QueryWrapper<Orderitem> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",orderitem.getUid())
                .eq("pid",orderitem.getPid())
                .isNull("oid");

        Orderitem one = orderitemService.getOne(wrapper);


        Integer id = one.getId();
        System.out.println("这个对象" + one);
        System.out.println("我返回的id"+ id);
//        String s = id.toString();

//
//        model.addAttribute("oiid",id);

        return id;
    }
}
