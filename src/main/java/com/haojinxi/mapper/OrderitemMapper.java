package com.haojinxi.mapper;

import com.haojinxi.entity.Orderitem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haojinxi.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author haojinxi
 * @since 2023-03-05
 */
@Repository
public interface OrderitemMapper extends BaseMapper<Orderitem> {

    @Select("SELECT id,pid,oid,uid,SUM(number) as number FROM orderitem WHERE (uid = #{id} AND oid IS NULL) GROUP BY pid ")
    List<Orderitem> queryAll(User user);


    @Select("SELECT id,pid,oid,uid,number FROM orderitem WHERE id IN (${value})")
    List<Orderitem> queryOrderItems(String oiid);
}
