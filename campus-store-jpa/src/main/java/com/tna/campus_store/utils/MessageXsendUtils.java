package com.tna.campus_store.utils;

import java.io.IOException;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;

import net.sf.json.JSONObject;


public final class MessageXsendUtils {
    /**
     * 时间戳接口配置
     */
    private static final String TIMESTAMP = "https://api.mysubmail.com/service/timestamp";

    private static Logger logger = LogManager.getLogger(MessageXsendUtils.class);
    /**
     * API 请求接口配置
     */
    private static final String URL = "https://api.mysubmail.com/message/xsend";
    
    private static Random ran = new Random();
    
    public static String APPID_SML = "37931";

    public static String APPKEY_SML = "d80c2ce89365f9bc2fe941954ae3409d";

    public static String PROJECT_SML = "OKG5L2";

    public static boolean send( String to, JSONObject vars) {
        HttpPost httpPost = new HttpPost(URL);
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("appid", APPID_SML);
        jsonParam.put("signature", APPKEY_SML);
        jsonParam.put("project", PROJECT_SML);
        jsonParam.put("to", to);
        jsonParam.put("vars", vars);
        StringEntity entity = new StringEntity(jsonParam.toString(), "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        HttpResponse resp;

        try {
            resp = HttpClientBuilder.create().build().execute(httpPost);
            HttpEntity he = (HttpEntity) resp.getEntity();
            logger.info(EntityUtils.toString((org.apache.http.HttpEntity) he, "UTF-8"));
            return resp.getStatusLine().getStatusCode() == 200;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * 获取时间戳
     *
     * @return
     */
    private static String getTimestamp() {
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet(TIMESTAMP);
        String timestamp = null;
        try {
            HttpResponse response = closeableHttpClient.execute(httpget);
            HttpEntity httpEntity = (HttpEntity) response.getEntity();
            String jsonStr = EntityUtils.toString((org.apache.http.HttpEntity) httpEntity, "UTF-8");
            if (jsonStr != null) {
                JSONObject json = JSONObject.fromObject(jsonStr);
                timestamp = json.getString("timestamp");
            }
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
    
    
    public static String getConversionCode() {
        String conversion_code = "";
        for (int i = 0; i < 6; i++) {
            conversion_code = conversion_code + ran.nextInt(10);
        }
        return conversion_code;
    }
    
    public static boolean sendMessage(String phone_number, String conversion_code) {
        boolean flag = false;
        JSONObject vars = new JSONObject();
        String to = null;
        to = phone_number;
        if (to != null) {
            vars.put("code", conversion_code);
            flag = send(to,  vars);
        }
        return flag;
    }
}
