package com.haojinxi.config;

import com.haojinxi.interceptor.LoginInterceptor;
import com.haojinxi.interceptor.SearchInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SearchConfigurer implements WebMvcConfigurer {


    @Bean
    public SearchInterceptor getSearchIntercepter() {
        return new SearchInterceptor();
    }

    @Bean
    public LoginInterceptor getLoginInterceptor(){return  new LoginInterceptor();}

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(getSearchIntercepter())
                .addPathPatterns("/**");
        registry.addInterceptor(getLoginInterceptor())
                .addPathPatterns("/**");

    }
}
