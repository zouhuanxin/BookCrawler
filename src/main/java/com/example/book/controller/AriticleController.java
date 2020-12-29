package com.example.book.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.book.compant.BqgBookCompant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AriticleController {
    @Autowired
    private BqgBookCompant bqgBookCompant;

    @GetMapping("getAriticle")
    public String getAriticle(String url) {
        if (url == null) {
            JSONObject rep = new JSONObject();
            rep.put("code", 302);
            rep.put("msg", "请填写url");
            return rep.toJSONString();
        }
        return bqgBookCompant.queryArticle(url);
    }

}
