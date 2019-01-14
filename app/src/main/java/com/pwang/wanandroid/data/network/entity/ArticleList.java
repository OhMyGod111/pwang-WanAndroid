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
public class ArticleList implements Serializable {
    private static final long serialVersionUID = 74280078145821232L;

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<ArticleDetail> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ArticleDetail> getDatas() {
        return datas;
    }

    public void setDatas(List<ArticleDetail> datas) {
        this.datas = datas;
    }
}
