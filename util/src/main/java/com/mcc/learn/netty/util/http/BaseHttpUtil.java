package com.mcc.learn.netty.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mcc.learn.netty.util.exception.HttpCallException;
import com.squareup.okhttp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

/**
 * Created by Mingchenchen on 2017/9/21
 *
 * 基础HTTPClient包
 */
public class BaseHttpUtil {
    private static Logger logger = LoggerFactory.getLogger(BaseHttpUtil.class);

    public static <T> T get(String url) {
        return get(url, new TypeReference<T>() {});
    }

    public static <T> T get(String url, TypeReference<T> type) {
        Response response = null;
        OkHttpClient client = null;

        try {
            client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                return JSON.parseObject(response.body().string(), type);
            }
        } catch (IOException e) {
            logger.error("BaseHttpUtil doGet() error: ", e);
            throw new HttpCallException(e);
        }

        throw new HttpCallException(response);
    }

    public static <T> T post(String url, String jsonBody) {
        return post(url, jsonBody, new TypeReference<T>() {});
    }

    public static <T> T post(String url, String jsonBody, TypeReference<T> type) {
        Response response = null;
        OkHttpClient client = null;

        try {
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonBody);

            client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                return JSON.parseObject(response.body().string(), type);
            }
        } catch (IOException e) {
            logger.error("BaseHttpUtil doPost() error: ", e);
            throw new HttpCallException(e);
        }

        throw new HttpCallException(response);
    }
}
