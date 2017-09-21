package com.mcc.learn.netty.demo;

import com.mcc.learn.netty.demo.client.HelloNettyClient;
import com.mcc.learn.netty.demo.server.HelloNettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Mingchenchen on 2017/9/20
 */
public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void startAll() throws Exception {
        int port = 8080;

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new HelloNettyServer(port));

        System.out.println("------------------------------");

        Thread.sleep(1000);

        HelloNettyClient client = new HelloNettyClient();
        client.connect("127.0.0.1", port);
        //浏览器访问http://localhost:8080 即可看到输出
    }

    public static void main(String[] args) throws Exception {
        logger.debug("debug");
        logger.trace("trace");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
        logger.info("test: {}", "bbb");
        //startAll();
    }
}
