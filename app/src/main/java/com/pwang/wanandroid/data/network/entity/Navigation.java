package com.pwang.wanandroid.data.network.entity;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/14
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class Navigation implements Serializable {
    private int cid;
    private String name;
    private List<ArticleDetail> articles;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArticleDetail> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleDetail> articles) {
        this.articles = articles;
    }
}
