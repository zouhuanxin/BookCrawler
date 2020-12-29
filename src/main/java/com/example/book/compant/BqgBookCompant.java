package com.example.book.compant;

import com.alibaba.fastjson.JSONObject;
import com.example.book.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 笔趣阁
 */
@Component
public class BqgBookCompant implements BookSpecification {
    @Autowired
    private HomeDataUtil homeDataUtil;
    @Autowired
    private TypeDataUtil typeDataUtil;
    @Autowired
    private AriticleDataUtil ariticleDataUtil;
    @Autowired
    private ContentDataUtil contentDataUtil;
    @Autowired
    private SearchDataUtil searchDataUtil;

    @Override
    public String home() {
        JSONObject rep = new JSONObject();
        rep.put("code", 200);
        rep.put("msg", "成功");
        JSONObject data = homeDataUtil.BqgHome();
        if (data == null) {
            rep.put("code", 301);
            rep.put("msg", "失败");
        }
        rep.put("data", data);
        return rep.toJSONString();
    }

    @Override
    public String types(String url) {
        JSONObject rep = new JSONObject();
        rep.put("code", 200);
        rep.put("msg", "成功");
        JSONObject data = typeDataUtil.BqgType(url);
        if (data == null) {
            rep.put("code", 301);
            rep.put("msg", "失败");
        }
        rep.put("data", data);
        return rep.toJSONString();
    }

    @Override
    public String queryArticle(String url) {
        JSONObject rep = new JSONObject();
        rep.put("code", 200);
        rep.put("msg", "成功");
        JSONObject data = ariticleDataUtil.BqgArticle(url);
        if (data == null) {
            rep.put("code", 301);
            rep.put("msg", "失败");
        }
        rep.put("data", data);
        return rep.toJSONString();
    }

    @Override
    public String queryContent(String url) {
        JSONObject rep = new JSONObject();
        rep.put("code", 200);
        rep.put("msg", "成功");
        JSONObject data = contentDataUtil.BqgContent(url);
        if (data == null) {
            rep.put("code", 301);
            rep.put("msg", "失败");
        }
        rep.put("data", data);
        return rep.toJSONString();
    }

    @Override
    public String search(String ie, String siteid, String s, String q) {
        JSONObject rep = new JSONObject();
        try {
            String str = URLEncoder.encode(q, "gb2312");
            rep.put("code", 200);
            rep.put("msg", "成功");
            JSONObject data = searchDataUtil.BqgSearch(str);
            if (data == null) {
                rep.put("code", 301);
                rep.put("msg", "失败");
            }
            rep.put("data", data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return rep.toJSONString();
    }
}
