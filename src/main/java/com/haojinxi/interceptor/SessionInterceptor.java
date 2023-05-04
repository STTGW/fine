//package com.haojinxi.interceptor;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.util.concurrent.atomic.AtomicInteger;
//
//@Component
//public class SessionInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession();
//        AtomicInteger onlineCount = (AtomicInteger) session.getServletContext().getAttribute("onlineCount");
//        if (onlineCount == null) {
//            onlineCount = new AtomicInteger(0);
//            session.getServletContext().setAttribute("onlineCount", onlineCount);
//        }
//        onlineCount.incrementAndGet();
//        return true;
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        HttpSession session = request.getSession();
//        AtomicInteger onlineCount = (AtomicInteger) session.getServletContext().getAttribute("onlineCount");
//        if (onlineCount != null && onlineCount.get() > 0) {
//            onlineCount.decrementAndGet();
//        }
//    }
//}
