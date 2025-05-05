package com.assessment.zhihunes.domain

/**
 * description ： 额外信息
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/2 00:04
 */
data class ExtraStory(
    val comments: Int,//评论总数
    val long_comments: Int,//长评论总数
    val popularity: Int,//点赞总数
    val short_comments: Int//短评论总数
)