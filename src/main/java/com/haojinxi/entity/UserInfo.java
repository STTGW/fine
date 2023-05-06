package com.haojinxi.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("user_info")
@EqualsAndHashCode(callSuper = false)
public class UserInfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer uid;

    private String sex;

    private String realname;

    private String content;

}
