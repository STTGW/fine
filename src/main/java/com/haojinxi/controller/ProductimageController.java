package com.haojinxi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haojinxi.entity.Product;
import com.haojinxi.entity.Productimage;
import com.haojinxi.service.ProductService;
import com.haojinxi.service.ProductimageService;
import com.haojinxi.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author haojinxi
 * @since 2023-03-05
 */
@RestController
public class ProductimageController {

    @Autowired
    private ProductimageService productimageService;

    @Autowired
    private ProductService productService;

    //回显图片
    @GetMapping("/products/{pid}/productImages")
    public List<Productimage> list(@RequestParam("type") String type, @PathVariable("pid") int pid){
        Product product = productService.getById(pid);

        if (productimageService.type_single.equals(type)) {
            QueryWrapper<Productimage> wrapper = new QueryWrapper<>();
            wrapper.eq("pid",pid);

            List<Productimage> list = productimageService.list(wrapper);
            return list;

        } else {
            return new ArrayList<>();
        }
    }

    //新增图片
    @PostMapping("/productImages")
    public void add(@RequestParam("pid") int pid, @RequestParam("type") String type,
                     MultipartFile image, HttpServletRequest request){
        Productimage bean = new Productimage();
        Product product = productService.getById(pid);
        bean.setPid(pid);
        bean.setType(type);
        productimageService.save(bean);

        System.out.println("---------------"+bean.getId());
        System.out.println("---------------"+pid);
        System.out.println("---------------"+type);
        System.out.println("---------------"+bean);


        File folder= new File("D:\\gra\\finemall\\src\\main\\resources\\static\\img\\productSingle");
        File file = new File(folder,bean.getId()+".jpg");
        String fileName = file.getName();
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
//        image.transferTo(file);
//        BufferedImage img = ImageUtil.change2jpg(file);
//        ImageIO.write(img, "jpg", file);
        try {
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File imageFolder_small=new File("D:\\gra\\finemall\\src\\main\\resources\\static\\img\\productSingle_small");
        File imageFolder_middle=new File("D:\\gra\\finemall\\src\\main\\resources\\static\\img\\productSingle_middle");
        File f_small = new File(imageFolder_small, fileName);
        File f_middle = new File(imageFolder_middle, fileName);
        f_small.getParentFile().mkdirs();
        f_middle.getParentFile().mkdirs();
        ImageUtil.resizeImage(file, 56, 56, f_small);
        ImageUtil.resizeImage(file, 217, 190, f_middle);


    }

    //删除图片
    @DeleteMapping("/productImages/{id}")
    public void delete(@PathVariable("id") int id, HttpServletRequest request){
        Productimage bean = productimageService.getById(id);
        productimageService.removeById(id);
        File imageFolder= new File("D:\\gra\\finemall\\src\\main\\resources\\static\\img\\productSingle");
        File file = new File(imageFolder,id+".jpg");
        if(file.getParentFile().exists()){
            file.delete();
        }

        String fileName = file.getName();
        File imageFolder_small=new File("D:\\gra\\finemall\\src\\main\\resources\\static\\img\\productSingle_small");
        File imageFolder_middle=new File("D:\\gra\\finemall\\src\\main\\resources\\static\\img\\productSingle_middle");
        File f_small = new File(imageFolder_small, fileName);
        File f_middle = new File(imageFolder_middle, fileName);
        f_small.delete();
        f_middle.delete();
    }
}

