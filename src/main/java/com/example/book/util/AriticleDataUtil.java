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

@Component
public class AriticleDataUtil {
    @Autowired
    private OkHttpTool okHttpTool;
    @Autowired
    private NativeHttpTool nativeHttpTool;

    public JSONObject BqgArticle(String url) {
        JSONObject data = new JSONObject();
        String msg = null;
        if (url.indexOf("http") == -1) {
            msg = nativeHttpTool.Get(Constant.bqg + url);
        } else {
            msg = nativeHttpTool.Get(nativeHttpTool.getAbsUrl(url));
        }
        if (msg == null) {
            return null;
        }
        if (msg.length() < 10) {
            return null;
        }
        Document document = Jsoup.parse(msg);
        data.put("imageurl", document.getElementsByClass("cover").get(0).getElementsByTag("img").attr("src"));
        data.put("name", document.getElementsByClass("name").get(0).text());
        data.put("desc", document.getElementsByClass("dd_box").get(0).text() + document.getElementsByClass("dd_box").get(1).text());
        data.put("updatetime", document.getElementsByClass("book_box").get(0).getElementsByTag("dl").get(0).getElementsByTag("dd").get(2).text());
        data.put("introduce", document.getElementsByClass("book_about").get(0).getElementsByTag("dd").get(0).text());
        data.put("nexturl",document.getElementsByClass("right").get(0).getElementsByTag("a").attr("href"));
        data.put("onurl",document.getElementsByClass("left").get(0).getElementsByTag("a").attr("href"));
        //加载目录信息
        Elements dds = document.getElementsByClass("book_last").get(1).getElementsByTag("dd");
        JSONArray booklistArray = new JSONArray();
        for (int i = 0; i < dds.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("name", dds.get(i).text());
            object.put("url", dds.get(i).getElementsByTag("a").attr("href"));
            booklistArray.add(object);
        }
        data.put("booklist",booklistArray);
        //加载分页信息
        //当前页面
        Elements options = document.getElementsByClass("pageselectlist").get(0).getElementsByTag("option");
        JSONArray pageArray = new JSONArray();
        for (int i = 0; i < options.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("name",options.get(i).text());
            object.put("url",options.get(i).attr("value"));
            pageArray.add(object);
        }
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).attr("selected").equals("selected")){
                JSONObject object = new JSONObject();
                object.put("name",options.get(i).text());
                object.put("url",options.get(i).attr("value"));
                data.put("currentpage",object);
            }
        }
        data.put("page",pageArray);
        return data;
    }

}
