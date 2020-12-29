package com.example.book.compant;

public interface BookSpecification {

    /**
     * 首页信息
     */
    public String home();

    /**
     * 分类信息
     */
    public String types(String url);

    /**
     * 查询文章介绍
     */
    public String queryArticle(String url);

    /**
     * 查询文章内容(根据章节来查)
     */
    public String queryContent(String url);

    /**
     * 搜索图书
     */
    public String search(String ie,String siteid,String s,String q);

}
