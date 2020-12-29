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
public class SearchDataUtil {
    @Autowired
    private OkHttpTool okHttpTool;
    @Autowired
    private NativeHttpTool nativeHttpTool;

    public JSONObject BqgSearch(String q) {
        JSONObject data = new JSONObject();
        String msg = nativeHttpTool.Get(Constant.bqgSearch + "?ie=gbk" + "&siteid=biqukan.com" + "&q=" + q, "utf-8");
        if (msg == null) {
            return null;
        }
        if (msg.length() < 10) {
            return null;
        }
        Document document = Jsoup.parse(msg);
        try {
            Elements searchDivs = document.getElementsByClass("mybook").get(0).getElementsByTag("div");
            JSONArray searchArray = new JSONArray();
            for (int i = 0; i < searchDivs.size(); i++) {
                JSONObject object = new JSONObject();
                object.put("num", searchDivs.get(i).getElementsByTag("span").get(0).text());
                object.put("url", searchDivs.get(i).getElementsByTag("a").get(0).attr("href"));
                object.put("title", searchDivs.get(i).getElementsByTag("a").get(0).getElementsByTag("p").get(0).text());
                object.put("author", searchDivs.get(i).getElementsByTag("a").get(0).getElementsByTag("p").get(1).text());
                searchArray.add(object);
            }
            data.put("data", searchArray);
        } catch (Exception e) {
            e.printStackTrace();
            //没有搜索到相关数据
            return data;
        }
        return data;
    }

}
