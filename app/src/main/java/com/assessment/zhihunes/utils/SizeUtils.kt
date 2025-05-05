package com.assessment.zhihunes.utils

import android.content.Context

/**
 * description ： 像素工具
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/2 08:54
 */
object SizeUtils {
    /**
     * 将dp转换成px
     * @param context 上下文
     * @param dp dp
     */
    fun dpToPx(context: Context, dp: Int):Int{
        //context.resources.displayMetrics当前设备的显示参数
        val scale = context.resources.displayMetrics.density//屏幕密度缩放系数
        //val px = (dp * scale + 0.5f) as Int

        val px = (dp * scale + 0.5f).toInt()
        return px
    }
}