package com.assessment.zhihunes.model

import android.util.Log
import com.assessment.zhihunes.api.RetrofitClient
import com.assessment.zhihunes.domain.Comment

/**
 * description ： TODO:类的作用
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/4 11:30
 */
class CommentsModel {
    suspend fun getComments(id: Long, onSuccess:(MutableList<Comment>)->Unit,onError:(String)->Unit) {
        try {
            val commentsLong = RetrofitClient.apiService.getLongComments(id)
            val commentsShort = RetrofitClient.apiService.getShortComments(id)
            val comments: MutableList<Comment> = mutableListOf()
            comments.addAll(commentsLong.comments)
            comments.addAll(commentsShort.comments)
            Log.d("commentsld","${comments}");
            onSuccess(comments)
        } catch (e: Exception) {
            Log.d("commentsld","${e.message}");
            onError(e.message.toString())
        }

    }
}