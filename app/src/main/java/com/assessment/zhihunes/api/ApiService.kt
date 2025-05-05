package com.assessment.zhihunes.api

import LatestNews
import com.assessment.zhihunes.domain.BeforeNews
import com.assessment.zhihunes.domain.Comments
import com.assessment.zhihunes.domain.ExtraStory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * description ： 网络访问的接口
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/1 20:14
 */
interface ApiService {
    companion object {
        val BASE_URL = "https://news-at.zhihu.com/api/4/"
    }

    /**
     * 获取最新消息
     */
    @GET("news/latest")
    suspend fun getLatestNews(): LatestNews


    /**
     * 过往消息
     * @param date 时间
     */
    @GET("news/before/{date}")
    suspend fun getNewsByDate(@Path("date") date: String): BeforeNews


    /**
     * 获取额外信息
     * @param id 文章id
     */
    @GET("story-extra/{id}")
    suspend fun getExtraStory(@Path("id") id: Long): ExtraStory

    /**
     * 获取长评论
     * @param id 文章id
     */
    @GET("story/{id}/long-comments")
    suspend fun getLongComments(@Path("id") id: Long): Comments


    /**
     * 获取短评论
     * @param id 文章id
     */
    @GET("story/{id}/short-comments")
    suspend fun getShortComments(@Path("id") id: Long): Comments


}