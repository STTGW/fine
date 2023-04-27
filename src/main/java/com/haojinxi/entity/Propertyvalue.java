package com.haojinxi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
public class Propertyvalue implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer pid;

    private Integer ptid;

    private String value;

    @TableField(exist = false)
    private Property property;


    //
    @TableField(exist = false)
    private Product product;

}
