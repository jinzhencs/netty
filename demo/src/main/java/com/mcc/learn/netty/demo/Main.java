package com.mcc.learn.netty.demo;

import com.mcc.learn.netty.demo.client.HelloNettyClient;
import com.mcc.learn.netty.demo.server.HelloNettyServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Mingchenchen on 2017/9/20
 */
public class Main {
    public static void main(String[] args) throws Exception {
        int port = 8080;

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new HelloNettyServer(port));

        System.out.println("------------------------------");

        Thread.sleep(1000);

        HelloNettyClient client = new HelloNettyClient();
        client.connect("127.0.0.1", port);
        //浏览器访问http://localhost:8080 即可看到输出
    }
}
