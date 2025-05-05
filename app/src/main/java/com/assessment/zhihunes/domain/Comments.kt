package com.assessment.zhihunes.domain

/**
 * description ： 评论
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/2 00:05
 */
data class Comments(
    val comments: List<Comment>
)

data class Comment(
    val author: String,
    val avatar: String,
    val content: String,
    val id: Int,
    val likes: Int,
    val reply_to: ReplyTo,
    val time: Int
)

data class ReplyTo(
    val author: String,
    val content: String,
    val id: Int,
    val status: Int
)