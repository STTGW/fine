package com.haojinxi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//处理上传图片回显异常，addResourceHandler（） 添加的是访问路径，
//addResourceLocations（）添加的是映射后的真实路径，映射的真实路径末尾必须加 / ,不然映射不到
@Configuration
public class ImageUploadConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置与前端图片展示的相对路径一致
//        registry.addResourceHandler("/img/category/**","img/productSingle/**")
                //图片上传的路径
//                .addResourceLocations("file:D:\\gra\\finemall\\src\\main\\resources\\static\\img\\category\\","file:D:\\gra\\finemall\\src\\main\\resources\\static\\img\\productSingle\\");
        registry.addResourceHandler("/img/category/**")
                .addResourceLocations("file:D:\\gra\\finemall\\src\\main\\resources\\static\\img\\category\\");
        registry.addResourceHandler("img/productSingle/**")
                .addResourceLocations("file:D:\\gra\\finemall\\src\\main\\resources\\static\\img\\productSingle\\");
        registry.addResourceHandler("img/productSingle_middle/**")
                .addResourceLocations("file:D:\\gra\\finemall\\src\\main\\resources\\static\\img\\productSingle_middle\\");
        registry.addResourceHandler("img/productSingle_small/**")
                .addResourceLocations("file:D:\\gra\\finemall\\src\\main\\resources\\static\\img\\productSingle_small\\");
    }
}
