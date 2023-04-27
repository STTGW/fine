package com.haojinxi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haojinxi.entity.Product;
import com.haojinxi.entity.Property;
import com.haojinxi.entity.Propertyvalue;
import com.haojinxi.mapper.PropertyMapper;
import com.haojinxi.mapper.PropertyvalueMapper;
import com.haojinxi.service.PropertyService;
import com.haojinxi.service.PropertyvalueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haojinxi
 * @since 2023-03-05
 */
@Service
public class PropertyvalueServiceImpl extends ServiceImpl<PropertyvalueMapper, Propertyvalue> implements PropertyvalueService {

    @Autowired
    private PropertyMapper propertyMapper;

    @Autowired
    private PropertyvalueMapper propertyvalueMapper;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyvalueService propertyvalueService;

    @Override
    public void setPv(Product product) {
        //根据产品id设置属性
        QueryWrapper<Propertyvalue> wrapper = new QueryWrapper<>();
        wrapper.eq("pid",product.getId());
        List<Propertyvalue> propertyvalues = propertyvalueMapper.selectList(wrapper);
        for(int i=0;i<propertyvalues.size();i++ ){
            int ptid = propertyvalues.get(i).getPtid();
            Property property = propertyMapper.selectById(ptid);
            propertyvalues.get(i).setProperty(property);
        }
        product.setPvs(propertyvalues);

    }

    @Override
    public void init(Product p) {
        QueryWrapper<Property> wrapper = new QueryWrapper<>();
        wrapper.eq("cid",p.getCid());
        List<Property> pts = propertyService.list(wrapper);
        for (Property pt : pts) {
            QueryWrapper<Propertyvalue> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("pid", p.getId())
                    .eq("ptid", pt.getId());
            Propertyvalue pv = propertyvalueMapper.selectOne(wrapper1);

            //根据属性id和产品id获取属性值，这个方法的返回值可能是null，因为数据库不存在对应的pv记录
            if (pv == null) {            //已获取的属性值对象可能是null，如果是null就初始化,向数据库插入记录
                pv = new Propertyvalue();
                pv.setPid(p.getId());
                pv.setPtid(pt.getId());
//                    pv.setProduct(p);
//                    pv.setProperty(pt);
                propertyvalueMapper.insert(pv);
                System.out.println("nihoa");
            }

        }

    }


}
