package com.mcc.learn.netty.demo;

import com.mcc.learn.netty.demo.server.HelloNettyServer;

/**
 * Created by Mingchenchen on 2017/9/20
 */
public class Main {
    public static void main(String[] args) throws Exception {
        int port = 8080;
        new HelloNettyServer(port).startServer();

        //浏览器访问http://localhost:8080 即可看到输出
    }
}
