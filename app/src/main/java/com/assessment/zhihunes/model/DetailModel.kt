package com.assessment.zhihunes.model

import android.util.Log
import com.assessment.zhihunes.api.RetrofitClient
import com.assessment.zhihunes.domain.ExtraStory

/**
 * description ： 详情页的model
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/3 9:07
 */
class DetailModel {

    /**
     * 根据文章id获取额外消息
     * @param id 文章id
     * @param onSuccess 成功回调
     * @param onError 失败回调
     */
    suspend fun doGetExtraStory(
        id: Long,
        onSuccess: (ExtraStory) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            RetrofitClient.apiService.getExtraStory(id).run(onSuccess)
        } catch (e: Exception) {
            Log.d("DetailModel", "${e.message}");
            onError("网络访问超时")
        }

    }


    /**
     * 按照日期获取新闻
     * @param date 日期 yyyyMMdd格式
     * @param onError 失败回调
     * @param onSuccess 成功回调
     */
    suspend fun doGetBeforeNews(date: String) = RetrofitClient.apiService.getNewsByDate(date)
}