package com.haojinxi;


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



    @Test
    void contextLoads() throws Exception {
        System.out.println("获取的数据库连接为:"+dataSource.getConnection());
    }

    @Test
    void ipTest(){

//        IPUtil ipUtil = new IPUtil();
//        ipUtil.iPPro();

    }
}
