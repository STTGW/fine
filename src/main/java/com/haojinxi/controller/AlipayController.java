package com.haojinxi.controller;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haojinxi.config.AlipayConfig;
import com.haojinxi.entity.Order;
import com.haojinxi.service.OrderService;
import com.haojinxi.service.OrderitemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@Slf4j
public class AlipayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderitemService orderitemService;

    @RequestMapping(value = "/alipay", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String goAlipay(String oid, double total, HttpServletRequest request, HttpServletRequest response) throws Exception {
        Order order = orderService.getById(oid);

        order.setPayDate(new Date());
        orderService.updateById(order);
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
//        String out_trade_no = order.getOrderCode()+ UUID.randomUUID().toString().replace("-","").substring(0,46);
        String out_trade_no = order.getOrderCode();
        //付款金额，必填
        DecimalFormat df   = new DecimalFormat("######0.00");
        log.info("df.format(total):"+df.format(total));
        String total_amount = df.format(total);//order.getOrderAmount();
        //订单名称，必填
//        String subject = order.getMobile();//product.getName();
//        String subject = "order.getMobile()";//product.getName();
        String subject = "欢迎使用支付功能";//product.getName();
        //商品描述，可空
        String body = "用户订购商品" ;//+ order.getBuyCounts();

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "1c";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();

        return result;
    }

    @RequestMapping(value = "/alipayReturnNotice")
    public String alipayReturnNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {

        log.info("支付成功, 进入同步通知接口...");

        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        ModelAndView mv = new ModelAndView("alipaySuccess");
        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水
//       orderService.updateOrderStatus(out_trade_no, trade_no, total_amount);
//
//
//       Orders order = orderService.getOrderById(out_trade_no);
//       Product product = productService.getProductById(order.getProductId());

            log.info("********************** 支付成功(支付宝同步通知) **********************");
            log.info("* 订单号: {}", out_trade_no);
            log.info("* 支付宝交易号: {}", trade_no);
            log.info("* 实付金额: {}", total_amount);
//        log.info("* 购买产品: {}", product.getName());
            log.info("***************************************************************");


//            model.addAttribute("oid", out_trade_no);
//            model.addAttribute("trade_no", trade_no);
//            model.addAttribute("total", total_amount);
//        mv.addObject("productName", product.getName());


            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("ordercode",out_trade_no);
            Order order = new Order();
            order.setStatus("waitDelivery");
            orderService.update(order,wrapper);
        }else {
            log.info("支付, 验签失败...");
        }

        return "/fore/payed";
    }
}
