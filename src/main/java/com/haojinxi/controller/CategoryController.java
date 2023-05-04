package com.haojinxi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haojinxi.entity.Category;
import com.haojinxi.service.CategoryService;
import com.haojinxi.utils.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //后台首页获取分类
    @RequestMapping("/categories")
    public PageInfo<Category> list(@RequestParam(value = "start",required = true,defaultValue = "1") Integer start,
                                   @RequestParam(value = "size",required = true,defaultValue = "6") Integer size,
                                   HttpServletResponse response){
        PageHelper.startPage(start,size);

        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Category> categories = categoryService.list(wrapper);
        PageInfo<Category> pageInfo = new PageInfo<>(categories);

        log.info("pageInfo:" + pageInfo);
        log.info("categories:" + categories);

//        response.setHeader("Cache-Control", "no-cache");
//        response.setHeader("Pragme", "no-cache");

        return  pageInfo;
    }

    //后台添加分类
    @PostMapping("/categories")
    public void addCategory(Category category, MultipartFile image, HttpServletRequest request) throws IOException {
        //1.把分类名称保存到数据表
        categoryService.save(category);
        //2.把图片存到服务器
        String filename = image.getOriginalFilename();
        log.info("category:"+category);
        log.info("filename:"+filename);

//        File imageFolder= new File(request.getServletContext().getRealPath("img/category"));
        File imageFolder= new File("D:\\gra\\finemall\\src\\main\\resources\\static\\img\\category");

//        log.info("path:"+request.getServletContext().getRealPath("img/category"));


        File file = new File(imageFolder,category.getId()+".jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
    }


    //后台删除分类
    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable("id") Integer id,HttpServletRequest request){
        categoryService.removeById(id);
        //删除本地目录图片
        //File imageFolder= new File(request.getServletContext().getRealPath("img/category"));

        File imageFolder= new File("D:\\gra\\finemall\\src\\main\\resources\\static\\img\\category");

        File file = new File(imageFolder,id+".jpg");
        if(file.getParentFile().exists()){
            file.delete();
        }
    }

    //(后台编辑分类)根据id获取数据
    @GetMapping("/categories/{id}")
    public Category findById(@PathVariable("id") Integer id){
        Category c = categoryService.getById(id);
        return c;
    }

    //后台编辑分类修改数据
    @PutMapping("/categories/{id}")
    public void updateCategory(Category category, MultipartFile image, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("updateCategory:"+category);

        //1.修改数据
        categoryService.saveOrUpdate(category);
        //2.修改保存的图片
        if(image == null) return;
        log.info("image-name:"+image.getOriginalFilename());
        uploadFile(category, image, request);

        return;

    }

    //后台编辑页面图片更新
    private void uploadFile(Category category, MultipartFile image, HttpServletRequest request) throws IOException {
//        File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
//        log.info("path:" + request.getServletContext().getRealPath("img/category"));
        File imageFolder= new File("D:\\gra\\finemall\\src\\main\\resources\\static\\img\\category");

        File file = new File(imageFolder, category.getId() + ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
    }

    //axios.min.js:8 GET http://localhost:8080/forecategory/60?sort=null
    //前台分类搜索
    @GetMapping("/forecategory/{cid}")
    public Category forecategory(@PathVariable("cid") Integer cid, String sort){
        //sort=all  null  综合
        //sort=review  人气
        //sort=date  新品
        //sort=saleCount  销量
        //sort=price 价格
        //1.根据id找分类
        Category category = categoryService.getById(cid);
        //2.填充分类中的产品
        categoryService.fillCaProduct(category);
        //3.填充产品销量和回复数量
        categoryService.fillSalesAndReviews(category);
        //4.排序sort
        categoryService.sortProduct(category,sort);
        return category;
    }



}

