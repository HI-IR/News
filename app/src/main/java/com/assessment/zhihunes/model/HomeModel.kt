package com.assessment.zhihunes.model

import LatestNews
import android.util.Log
import com.assessment.zhihunes.api.RetrofitClient
import com.assessment.zhihunes.domain.BeforeNews


/**
 * description ： 首页的model
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/1 23:31
 */
class HomeModel {
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

    /**
     * 按照日期获取新闻
     * @param date 日期 yyyyMMdd格式
     * @param onError 失败回调
     * @param onSuccess 成功回调
     */
    suspend fun doGetBeforeNews(
        date: String,
        onSuccess: (BeforeNews) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            RetrofitClient.apiService.getNewsByDate(date).run(onSuccess)

        }catch (e:Exception){
            Log.d("HomeModel","${e.message}");
            onError("网络访问超时")
        }
    }
}