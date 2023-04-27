package com.haojinxi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haojinxi.entity.Property;
import com.haojinxi.mapper.PropertyMapper;
import com.haojinxi.service.PropertyService;
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
public class PropertyServiceImpl extends ServiceImpl<PropertyMapper, Property> implements PropertyService {

}
