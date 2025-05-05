package com.assessment.zhihunes.domain

/**
 * description ： 用于集合HomeRvAdapter的数据，统一数据
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/2 22:29
 */
sealed class HomeRVData{
    data class News(val data: StoryBefore): HomeRVData()
    data class DateSeparator(val data: String):HomeRVData()
}