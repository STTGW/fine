package com.haojinxi.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author haojinxi
 * @since 2023-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    @TableField("subTitle")
    private String subTitle;

    @TableField("originalPrice")
    private Float originalPrice;

    @TableField("promotePrice")
    private Float promotePrice;

    private Integer stock;

    private Integer cid;

    @TableField(value = "createDate",fill = FieldFill.INSERT_UPDATE)
//    private LocalDateTime createdate;
        private Date createDate;

    //这个属性在数据表中没有对应字段，需要排除
    @TableField(exist = false)
    private Category category;

    @TableField(exist = false)
    private Productimage firstProductImage;

    @TableField(exist = false)
    private List<Propertyvalue> pvs;

    @TableField(exist = false)
    private List<Review> reviews;

    /*缩略图*/
    @TableField(exist = false)
    private List<Productimage> productSingleImages;

    /*累计评价*/
    @TableField(exist = false)
    private Integer reviewCount;

    /*销量*/
    @TableField(exist = false)
    private Integer saleCount;


}
