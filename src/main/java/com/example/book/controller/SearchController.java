package com.example.book.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.book.compant.BqgBookCompant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    @Autowired
    private BqgBookCompant bqgBookCompant;

    @GetMapping("Search")
    public String Search(String ie, String siteid, String s, String q) {
        if (q == null) {
            JSONObject rep = new JSONObject();
            rep.put("code", 302);
            rep.put("msg", "请填写参数");
            return rep.toJSONString();
        }
        return bqgBookCompant.search(ie, siteid, s, q);
    }

}
