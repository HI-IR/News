package com.assessment.zhihunes.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.assessment.zhihunes.R
import com.assessment.zhihunes.databinding.CustomToolbarBinding
import com.assessment.zhihunes.utils.DateUtils
import java.util.Calendar


/**
 * description ： 自定义ToolBar
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/1 21:23
 */
@SuppressLint("ResourceAsColor")
class CustomToolBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    //把View添加到容器,并初始化
    private val binding: CustomToolbarBinding
    init{
        val inflater = LayoutInflater.from(context)
        binding=CustomToolbarBinding.inflate(inflater,this,true)
        setHello(binding.tvToolbarHello)
        setDate(DateUtils.getCurrentDate())
    }


    /**
     * 设置日期
     * @param date 日期 格式：xxxx/xx/xx
     */
    fun setDate(date: String){
        //将日期切割
        val (templeYear,templeMonth,templeDay) = date.split("/")

        // 中文月份映射
        val chineseMonths = arrayOf(
            "一", "二", "三", "四", "五", "六",
            "七", "八", "九", "十", "十一", "十二"
        )
        val day = templeDay.trim().toInt().toString()

        //获取中文月份
        val monthIndex = templeMonth.trim().toIntOrNull()?.minus(1)
        val month = if (monthIndex in 0..11) chineseMonths[monthIndex!!] else "--月"
        //去除01前面的0
        binding.tvToolbarDate1.text = day
        binding.tvToolbarDate2.text = "${month.trim()}月"

    }

    /**
     * 设置打招呼语
     * @param textView 一个tv控件
     */

    fun setHello(textView: TextView){
        //获取当前时间
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        val greeting= when(hour){
            in 5..10 -> "早上好！"
            in 11..13 -> "中午好！"
            in 14..17 -> "下午好！"
            in 18..22 -> "晚上好！"
            else -> "早点休息~！"
        }

        textView.text = greeting
    }

    /**
     * 点击事件接口(点击头像)
     */
    fun setOnImageClickListener(listener: OnClickListener) {
        binding.tvToolbarUserimage.setOnClickListener(listener)
    }

    /**
     * 点击事件接口(点击日期)
     */
    fun setOnDateClickListener(listener: OnClickListener){
        binding.clToolbarDate.setOnClickListener(listener)
    }
}

