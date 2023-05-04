package com.haojinxi.utils;

import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.concurrent.TimeUnit;


public class IPUtil {
//    public static void main(String[] args) {

    public static String   iPPro(String ip1) {
        // 1、创建 searcher 对象
        String dbPath = "D:\\360broswers\\ip2regionTest\\src\\main\\resources\\ip2region\\ip2region.xdb";
        Searcher searcher = null;
        try {
            searcher = Searcher.newWithFileOnly(dbPath);
        } catch (IOException e) {
            System.out.printf("failed to create searcher with `%s`: %s\n", dbPath, e);
            return null;
        }

        // 2、查询

        String result = null;
        try {
            String ip = ip1;
            long sTime = System.nanoTime();
            String region = searcher.search(ip);

            String[] arr = region.split("\\|");
            String result1 = arr[2] + "|" + arr[3] + "|" + arr[4];
            result=result1;
            System.out.println(result);

            long cost = TimeUnit.NANOSECONDS.toMicros((long) (System.nanoTime() - sTime));
            System.out.printf("{region: %s, ioCount: %d, took: %d μs}\n", region, searcher.getIOCount(), cost);
        } catch (Exception e) {
            System.out.printf("failed to search(%s): %s\n", "ip", e);
        }

        // 3、关闭资源
        try {
            searcher.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
        // 备注：并发使用，每个线程需要创建一个独立的 searcher 对象单独使用。
    }
}
//}
