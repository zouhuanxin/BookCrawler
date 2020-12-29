package com.example.book.http;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 原生网络请求
 */
@Component
public class NativeHttpTool {

    public String Get(String url, String code) {
        Writer write = null;
        // 定义一个字符串用来存储网页内容
        String result = null;
        // 定义一个缓冲字符输入流
        BufferedReader in = null;
        try {
            // 将string转成url对象
            URL realUrl = new URL(url);
            // 初始化一个链接到那个url的连接
            URLConnection connection = realUrl.openConnection();
            // 开始实际的连接
            connection.connect();
            // 初始化 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), code));
            // 用来临时存储抓取到的每一行的数据
            String line;
            while ((line = in.readLine()) != null) {
                // 遍历抓取到的每一行并将其存储到result里面
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 处理跳转链接，获取重定向地址
     * @param url   源地址
     * @return      目标网页的绝对地址
     */
    public String getAbsUrl(String url){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext context = HttpClientContext.create();
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;
        String absUrl = null;
        try {
            response = httpclient.execute(httpget, context);
            HttpHost target = context.getTargetHost();
            List<URI> redirectLocations = context.getRedirectLocations();
            URI location = URIUtils.resolve(httpget.getURI(), target, redirectLocations);
            absUrl = location.toASCIIString();
        }catch(IOException e){
            e.printStackTrace();
        }catch (URISyntaxException e) {
            e.printStackTrace();
        }finally {
            try {
                httpclient.close();
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return absUrl;
    }

    public String Get(String url) {
        return Get(url, "gbk");
    }
}
