package com.haojinxi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String content;

    private Integer uid;

    private Integer pid;

    @TableField("createDate")
    private LocalDateTime createDate;

    public LocalDateTime getCreatedate() {
        return createDate;
    }

    @TableField(exist = false)
    private User user;

    public String getcDate(){
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String d = dtf2.format(createDate);
        return d;
    }

//    @TableField(value = "createDate",fill = FieldFill.INSERT)
//    private Date createDate;

//    public Date getCreatedate() {
//        return createDate;
//    }

//    public String getcDate(){
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        return simpleDateFormat.format(createDate);
//    }


}
