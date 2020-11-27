package com.zhy.jetpack.wanandroid_jetpack.http

import com.zhy.jetpack.wanandroid_jetpack.data.model.*
import com.zhy.jetpack.wanandroid_jetpack.http.model.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


const val BASE_URL = "https://wanandroid.com/"

interface ApiService {

    //首页Banner
    @GET("/banner/json")
    suspend fun banner(): ApiResult<List<Banner>>

    //置顶文章
    @GET("/article/top/json")
    suspend fun topArticles(): ApiResult<MutableList<Article>>

    //首页文章列表
    @GET("/article/list/{page}/json")
    suspend fun articleList(@Path("page") page: Int): ApiResult<Pagination<Article>>

    //获取公众号列表
    @GET("/wxarticle/chapters/json")
    suspend fun officialAccounts(): ApiResult<MutableList<OfficialAccount>>

    //查看某个公众号历史数据
    @GET("/wxarticle/list/{chapter}/{page}/json")
    suspend fun wxArticles(
        @Path("chapter") chapter: Int, //公众号 ID
        @Path("page") page: Int //公众号页码
    ): ApiResult<Pagination<Article>>

    //项目分类
    @GET("/project/tree/json")
    suspend fun projectCategories(): ApiResult<List<ProjectCategory>>

    //项目列表数据
    @GET("project/list/{page}/json")
    suspend fun projectArticles(
        @Path("page") page: Int,//页码
        @Query("cid") cid: Int //分类的id，上面项目分类接口
    ): ApiResult<Pagination<Article>>

    //体系
    @GET("/tree/json")
    suspend fun channelTree(): ApiResult<MutableList<Channel>>

    //知识体系下的文章
    @GET("/article/list/{page}/json")
    suspend fun treeArticles(
        @Path("page") page: Int,//页码
        @Query("cid") cid: Int  //分类的id，上面项目分类接口
    ): ApiResult<Pagination<Article>>

}