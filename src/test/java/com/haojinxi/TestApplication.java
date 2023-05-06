package com.haojinxi;


import com.haojinxi.entity.UserInfo;
import com.haojinxi.mapper.UserinfoMapper;
import com.haojinxi.service.UserInfoService;
import com.haojinxi.utils.IPUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.net.InetAddress;

@SpringBootTest
public class TestApplication {

    @Resource
    DataSource dataSource;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    UserinfoMapper userinfoMapper;



    @Test
    void contextLoads() throws Exception {
        System.out.println("获取的数据库连接为:"+dataSource.getConnection());
    }

    @Test
    void ipTest(){

//        IPUtil ipUtil = new IPUtil();
//        ipUtil.iPPro();

    }
    @Test
    void returnPass(){
//        UserInfo userInfo = userinfoMapper.selectById(1);
        UserInfo byId = userInfoService.getById(1);
        System.out.println("你好啊老哥"+byId);

    }

    @Test
    void test(){
        System.out.println("搞啊啊"+1+1);
    }

}
