package com.haojinxi.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.haojinxi.service.impl.OrderServiceImpl;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("order_table")
@EqualsAndHashCode(callSuper = false)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("orderCode")
    private String orderCode;

    private String address;

    @TableField(exist = false)
    private String post;

    private String receiver;

    private String mobile;

    @TableField(value = "userMessage",exist = false)
    private String userMessage;

    @TableField(value = "createDate",fill = FieldFill.INSERT)
    private Date createDate;

    @TableField("payDate")
    private Date payDate;

    @TableField("deliveryDate")
    private Date deliveryDate;

    @TableField("confirmDate")
    private Date confirmDate;

    private Integer uid;

    private String status;

    @TableField(exist = false)
    private List<Orderitem> orderItems;

    @TableField(exist = false)
    private Integer totalNumber;

    @TableField(exist = false)
    private Double total;

    @TableField(exist = false)
    private User user;

    public String getStatusDesc(){
        String desc ="非法";
        switch(status){
          case OrderServiceImpl.waitPay:
              desc="待付款";
              break;
          case OrderServiceImpl.waitDelivery:
              desc="待发货";
              break;
          case OrderServiceImpl.waitConfirm:
              desc="待收货";
              break;
//          case OrderServiceImpl.waitReview:
//              desc="等评价";
//              break;
          case OrderServiceImpl.finish:
              desc="完成";
              break;
          case OrderServiceImpl.delete:
              desc="刪除";
              break;
          default:
              desc="非法";
        }
        return desc;
    }



}
