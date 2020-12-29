package com.example.book.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.book.http.OkHttpTool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TypeDataUtil {
    @Autowired
    private OkHttpTool okHttpTool;

    public JSONObject BqgType(String url) {
        JSONObject data = new JSONObject();
        String msg = null;
        if (url.indexOf("http") == -1) {
            msg = okHttpTool.Get(Constant.bqg + url);
        } else {
            msg = okHttpTool.Get(url);
        }
        if (msg == null) {
            return null;
        }
        if (msg.length() < 10) {
            return null;
        }
        Document document = Jsoup.parse(msg);
        //拿到火爆书籍
        Elements hotElements = document.getElementsByClass("hot").get(0).getElementsByClass("p10");
        JSONArray hots = new JSONArray();
        for (int i = 0; i < hotElements.size(); i++) {
            JSONObject object = new JSONObject();
            Element imageDiv = hotElements.get(i).getElementsByTag("div").get(0);
            Element textDiv = hotElements.get(i).getElementsByTag("div").get(0);
            object.put("imageurl", imageDiv.getElementsByTag("img").attr("src"));
            object.put("url", imageDiv.getElementsByTag("a").attr("href"));
            object.put("title", textDiv.getElementsByTag("dt").text());
            object.put("author", textDiv.getElementsByTag("span").text());
            object.put("intrduce", textDiv.getElementsByTag("dd").text());
            hots.add(object);
        }
        data.put("hot", hots);
        //拿到首页推荐书籍
        Elements recommendedElements = document.getElementsByClass("block");
        JSONArray recommendeds = new JSONArray();
        for (int i = 0; i < recommendedElements.size(); i++) {
            JSONObject object1 = new JSONObject();
            JSONArray array = new JSONArray();
            Elements lis = recommendedElements.get(i).getElementsByTag("ul").get(0).getElementsByTag("li");
            for (int j = 0; j < lis.size(); j++) {
                JSONObject object2 = new JSONObject();
                Elements spans = lis.get(j).getElementsByTag("span");
                object2.put("url", spans.get(1).getElementsByTag("a").get(0).attr("href"));
                object2.put("title", spans.get(0).text() + spans.get(1).text());
                object2.put("author", spans.get(2).text());
                array.add(object2);
            }
            object1.put("recommendedTitle", recommendedElements.get(i).getElementsByTag("h2").text());
            object1.put("content", array);
            recommendeds.add(object1);
        }
        data.put("recommended", recommendeds);
        return data;
    }
}
