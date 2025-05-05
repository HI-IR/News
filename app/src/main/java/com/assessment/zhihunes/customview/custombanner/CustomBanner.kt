package com.assessment.zhihunes.customview.custombanner

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import com.assessment.zhihunes.databinding.CustomBannerBinding
import com.assessment.zhihunes.utils.SizeUtils
import com.assessment.zhihunes.adapter.HomeBannerAdapter


/**
 * description ： 轮播图
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/1 22:49
 */
class CustomBanner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private lateinit var adapter: HomeBannerAdapter

    //把View添加到容器,并初始化
    private val binding: CustomBannerBinding

    //缓存view小白点
    private val viewList: MutableList<View> = mutableListOf()

    init {
        val inflater = LayoutInflater.from(context)
        binding = CustomBannerBinding.inflate(inflater, this, true)

    }

    //处理逻辑
    private fun initEvent() {
        binding.vp2Custombanner.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            //切换
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            //停止切换
            override fun onPageSelected(position: Int) {
                //切换焦点
                updatePoint()

            }

            //状态改变
            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    /**
     * 根据缓存更新Point
     */
    private fun updatePoint() {
        val selectedIndex = binding.vp2Custombanner.currentItem%adapter.topList.size
        for ((index, view) in viewList.withIndex()) {
            val color = if (index == selectedIndex){
                (view.background as GradientDrawable).setColor(Color.parseColor("#FFFFFF"))

            }else{
                (view.background as GradientDrawable).setColor(Color.parseColor("#A9A9A9"))
            }


        }
    }


    /**
     * 设置轮播图数据
     * @param adapter Vp2的适配器
     */
    fun setData(adapter: HomeBannerAdapter) {
        this.adapter = adapter
        binding.vp2Custombanner.setAdapter(adapter)

        // 先清空旧的圆点与缓存
        binding.pointContainer.removeAllViews()
        viewList.clear()

        //设置初始页面在VP2的中间最靠近 取模等于0的位置(以防轮播图首页不正确)
        binding.vp2Custombanner.setCurrentItem(Int.MAX_VALUE/2 -(Int.MAX_VALUE/2 % adapter.topList.size),false)
        Log.d("ld","${Int.MAX_VALUE/2 -(Int.MAX_VALUE % adapter.topList.size)}");
        //动态增加小圆点个数(因为做了假无限，所以adapter.itemCount不是真实的值)
        setUpPoint(adapter.topList.size)

        initEvent()
    }

    /**
     * 动态增加小圆点个数
     * @param itemCount 数据个数
     */
    private fun setUpPoint(itemCount: Int) {

        Log.d("test", "setUpPoint: ${viewList.size}")
        for (i in 0..itemCount-1) {
            val view = View(context)
            // 设置背景为圆形
            view.background = GradientDrawable().apply {
                shape = GradientDrawable.OVAL // 设置为圆形
                setColor(Color.parseColor("#A9A9A9"))}// 灰色填充

            //设置大小与左右间距
            //最后一个右间距为20
            val size = SizeUtils.dpToPx(context, 5)
            val marginL = SizeUtils.dpToPx(context, 10)
            val marginR = if (i == itemCount - 1) {
                SizeUtils.dpToPx(context, 20)
            } else {
                SizeUtils.dpToPx(context, 10)
            }
            val layoutParams = LinearLayout.LayoutParams(size, size).apply {
                setMargins(marginL, 0, marginR, 0)
            }
            view.layoutParams = layoutParams



            //添加到容器中
            binding.pointContainer.addView(view)

            //将新建的小白点加入缓存
            viewList.add(view)
            Log.d("ld1","${viewList.size}");
        }
        updatePoint()
    }
}