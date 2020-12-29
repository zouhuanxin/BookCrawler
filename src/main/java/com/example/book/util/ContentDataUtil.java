package com.example.book.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.book.http.NativeHttpTool;
import com.example.book.http.OkHttpTool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ContentDataUtil {
    @Autowired
    private OkHttpTool okHttpTool;
    @Autowired
    private NativeHttpTool nativeHttpTool;

    public JSONObject BqgContent(String url) {
        JSONObject data = new JSONObject();
        String msg = null;
        if (url.indexOf("http") == -1) {
            msg = nativeHttpTool.Get(Constant.bqg + url);
        } else {
            msg = nativeHttpTool.Get(url);
        }
        if (msg == null) {
            return null;
        }
        if (msg.length() < 10) {
            return null;
        }
        Document document = Jsoup.parse(msg);
        String content = document.getElementById("chaptercontent").html().replace("<script>mchaptererror();</script>","")
                .replace("\n","").replace("&nbsp","").replace("<br>","\n");
        data.put("chaptercontent",content.replace("笔趣阁阅读网址：m.bqkan.com",""));
        Pattern pattern = Pattern.compile("(?<=\\()(.+?)(?=\\))");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            data.put("currentpage",matcher.group(0));
        }
        data.put("onurl",document.getElementById("pb_prev").attr("href"));
        data.put("nexturl",document.getElementById("pb_next").attr("href"));
        return data;
    }

}
