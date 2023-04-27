package com.haojinxi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haojinxi.entity.Product;
import com.haojinxi.mapper.ProductMapper;
import com.haojinxi.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haojinxi
 * @since 2023-03-05
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
