package com.assessment.zhihunes.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.assessment.zhihunes.databinding.BottomToolbarBinding

/**
 * description ： 底部工具栏
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/2 23:36
 */
class BottomToolBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: BottomToolbarBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = BottomToolbarBinding.inflate(inflater, this, true)

    }

    /**
     * 设置数据
     * @param comments 评论数
     * @param popularity 点赞数
     */
    fun setData(comments: Int, popularity: Int) {
        binding.tvBottomtoolbarComments.text = comments.toString()
        binding.tvBottomtoolbarPopularity.text = popularity.toString()
    }


    /**
     * 返回按钮的点击事件接口
     */
    fun setOnBackClickListener(listener: OnClickListener) {
        binding.ivBottomtoolbarBack.setOnClickListener(listener)
    }

    /**
     * 评论按钮的点击事件
     */
    fun setOnCommentsClickListener(listener: OnClickListener) {
        binding.comments.setOnClickListener(listener)
    }

    /**
     * 点赞按钮的点击事件
     */
    fun setOnPopularityClickListener(listener: OnClickListener) {
        binding.popularity.setOnClickListener(listener)
    }

    /**
     * 收藏按钮的点击事件
     */
    fun setOnCollectClickListener(listener: OnClickListener) {
        binding.collect.setOnClickListener(listener)
    }

    /**
     * 分享按钮的点击事件
     */
    fun setOnShareClickListener(listener: OnClickListener) {
        binding.share.setOnClickListener(listener)
    }


}