package com.assessment.zhihunes.model

import LatestNews
import android.util.Log
import com.assessment.zhihunes.api.RetrofitClient
import com.assessment.zhihunes.domain.ExtraStory

/**
 * description ： TODO:类的作用
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/3 22:07
 */
class TopDetailModel {
    /**
     * 根据文章id获取额外消息
     * @param id 文章id
     * @param onSuccess 成功回调
     * @param onError 失败回调
     */
    suspend fun doGetExtraStory(
        id: Int,
        onSuccess: (ExtraStory) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            RetrofitClient.apiService.getExtraStory(id.toLong()).run(onSuccess)
        } catch (e: Exception) {
            Log.d("DetailModel", "${e.message}");
            onError("网络访问超时")
        }

    }

    /**
     * @param onSuccess 获取数据成功回调
     * @param onError 获取数据失败回调
     */
    suspend fun doGetLatestNews(onSuccess: (LatestNews) -> Unit, onError: (String) -> Unit) {
        try {
            RetrofitClient.apiService.getLatestNews().run(onSuccess)

        } catch (e: Exception) {
            Log.d("ld", "${e.message}");
            onError("网络访问超时")
        }
    }

}