package com.pwang.wanandroid.data.network;

import com.pwang.wanandroid.data.network.entity.ArticleList;
import com.pwang.wanandroid.data.network.entity.Banner;
import com.pwang.wanandroid.data.network.entity.BaseResponse;
import com.pwang.wanandroid.data.network.entity.Knowledge;
import com.pwang.wanandroid.data.network.entity.Navigation;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * <pre>
 *     author : Wang Pan
 *     e-mail : 501098462@qq.con
 *     time   : 2019/01/14
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface ApiService {

    //<editor-fold desc="主页Api">
    /**
     *  首页文章列表
     *  http://www.wanandroid.com/article/list/0/json
     * @param page 页码
     * @return
     */

    @GET("/article/list/{page}/json")
    Observable<BaseResponse<ArticleList>> getHomePageArticleList(@Path("page")int page);

    /**
     *  首页banner图
     *  http://www.wanandroid.com/banner/json
     * @return
     */
    @GET("/banner/json")
    Observable<BaseResponse<List<Banner>>> getHomePageBanner();

    /**
     * 知识体系目录 （一级目录）
     * http://www.wanandroid.com/tree/json
     * @return
     */
    @GET("/tree/json")
    Observable<BaseResponse<Knowledge>> getKnowledgeHierarchy();

    /**
     * 知识体系目录下的文章 （二级目录）
     * http://www.wanandroid.com/article/list/0?cid=60
     * @return
     */
    @GET("/article/list/{page}/json")
    Observable<BaseResponse<ArticleList>> getSecondLevelKnowledgeHierarchy(@Path("page")int page, @Query("cid") int cid);

    /**
     * 导航数据
     * http://www.wanandroid.com/navi/json
     * @return
     */
    @GET("/navi/json")
    Observable<BaseResponse<Navigation>> getNavigationArticle();

    /**
     * 项目分类
     * http://www.wanandroid.com/project/tree/json
     * @return
     */
    @GET("/project/tree/json")
    Observable<BaseResponse<Knowledge.Children>> getProjectClassify();

    /**
     *  项目分类列表数据
     *  http://www.wanandroid.com/project/list/1/json?cid=294
     * @return
     */
    @GET("/project/list/{page}/json")
    Observable<BaseResponse<ArticleList>> getProjectClassifyList(@Path("page")int page, @Query("cid")int cid);

    /**
     * 搜索热词
     * http://www.wanandroid.com/hotkey/json
     * @return
     */
    @GET("/hotkey/json")
    Observable<BaseResponse<ArticleList>> getHotkey();

    /**
     * 搜索
     * http://www.wanandroid.com/article/query/0/json
     * @return
     */
    @POST("/article/query/{page}/json")
    @FormUrlEncoded
    Observable<BaseResponse<ArticleList>> getSearchList(@Path("page")int page, @Field("k")String key);

    //</editor-fold>


    //<editor-fold desc="知识体系Api">
    //</editor-fold>


    //<editor-fold desc="导航Api">
    //</editor-fold>

    //<editor-fold desc="项目Api">
    //</editor-fold>

}
