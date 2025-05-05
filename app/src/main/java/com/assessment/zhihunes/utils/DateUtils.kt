package com.assessment.zhihunes.utils
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
/**
 * description ： 关于日期的工具类
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/1 22:53
 */
object DateUtils {
    /**
     * 获取当前时间
     * 格式:yyyy/MM/dd
     */
    fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Date())
    }

    /**
     * 获取几天前的数据
     *
     * @param ago 几天
     * @return yyyyMMdd
     */
    fun getDateAgo(daysAgo: Int): String{
        val calendar = Calendar.getInstance()//获取当前日期的副本
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)
        val formatter = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    /**
     * 用于将yyyy/MM/dd格式的日期转为yyyyMMdd格式
     */
    fun formateDate(originalDate:String):String{
        val formattedDate = originalDate.replace("/", "")
        return formattedDate
    }

}