package com.assessment.zhihunes.domain

/**
 * description ： 往期消息
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/2 00:02
 */
data class BeforeNews(
    val date: String,
    val stories: List<StoryBefore>
)

data class StoryBefore(
    val ga_prefix: String,
    val hint: String,
    val id: Long,
    val image_hue: String,
    val images: List<String>,
    val title: String,
    val type: Int,
    val url: String //url
)