package com.haojinxi.listen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;

//@WebListener
@Component
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener, ServletContextListener {

    private int onlineCount = 0;//记录session的数量
        @Autowired
        private HttpServletRequest request;

    public int getOnlineCount(){
        return this.onlineCount;
    }



    /**
     * session创建后执行
     */
//    @Override
//    public void sessionCreated(HttpSessionEvent se) {
//        System.out.println(request.getRequestURI());
//        onlineCount++;
//        System.out.println("【HttpSessionListener监听器】 sessionCreated, onlineCount:" + onlineCount);
//        //将最新的onlineCount值存起来
//        se.getSession().getServletContext().setAttribute("onlineCount", onlineCount);
//
//    }

    /**
     * session失效后执行
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        if (onlineCount > 0) {
            onlineCount--;
        }
        System.out.println("【HttpSessionListener监听器】 sessionDestroyed, onlineCount:" + onlineCount);
        //将最新的onlineCount值存起来
        se.getSession().getServletContext().setAttribute("onlineCount", onlineCount);
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        String name = se.getName();
        if("user".equals(name)){
            this.onlineCount++;
            se.getSession().getServletContext().setAttribute("onlineCount",this.onlineCount);
        }
        System.out.println("在线人数"+this.onlineCount);

    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        sce.getServletContext().setAttribute("onlineCount",this.onlineCount);

    }
}