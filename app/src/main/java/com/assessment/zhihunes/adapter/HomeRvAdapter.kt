package com.assessment.zhihunes.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.assessment.zhihunes.R
import com.assessment.zhihunes.databinding.ItemDateBinding
import com.assessment.zhihunes.databinding.ItemFooterLoadingBinding
import com.assessment.zhihunes.databinding.ItemRvBannerBinding
import com.assessment.zhihunes.databinding.ItemRvNewsBinding
import com.assessment.zhihunes.domain.HomeRVData
import com.assessment.zhihunes.domain.StoryBefore
import com.assessment.zhihunes.view.DetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * description ： Home页Rv的Adapter
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/2 15:40
 */
class HomeRvAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var homeBannerAdapter: HomeBannerAdapter? = null

    fun sethomeBannerAdapter(homeBannerAdapter: HomeBannerAdapter) {
        this.homeBannerAdapter = homeBannerAdapter
    }

    private var dataList = mutableListOf<HomeRVData?>(null)
    //用null表示 footer的加载中


    companion object {
        val NEWS_VIEW = 0
        val LOADING_VIEW = 1
        val BANNER_VIEW = 2
        val DATE_VIEW = 3
    }


    /**
     * 添加显示的新闻
     */
    fun addNews(news: List<StoryBefore>) {
        val start = dataList.size
        news.forEach {
            dataList.add(HomeRVData.News(it))
        }
        notifyItemRangeInserted(start, news.size)
    }

    /**
     * 添加加载
     */
    fun addLoadingFooter() {
        dataList.add(null)//加载项 用null表示加载
        notifyItemInserted(dataList.size - 1)

    }

    /**
     * 移除加载
     */
    fun removeLoadingFooter(date: String) {
        val index = dataList.indexOfLast { it == null }//获取加载项在list的位置
        if (index != -1) {
            dataList[index] = HomeRVData.DateSeparator(date)//在列表中移除加载项,加入日期
            notifyItemChanged(index)//通知更新
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return BANNER_VIEW
        if (dataList[position] is HomeRVData.DateSeparator) return DATE_VIEW
        if (dataList[position] == null ) return LOADING_VIEW else return NEWS_VIEW
        //如果获取到的元素是null则说明是loading图，否则则是新闻


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            NEWS_VIEW -> return NewsViewHolder(
                ItemRvNewsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            LOADING_VIEW -> return LoadingViewHolder(
                ItemFooterLoadingBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            DATE_VIEW -> return DateViewHolder(
                ItemDateBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> return BannerViewHolder(
                ItemRvBannerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        //根据viewType创建对应的ViewHolder
    }

    override fun getItemCount(): Int = dataList.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NewsViewHolder) {
            holder.bind((dataList[position]!! as HomeRVData.News).data)
        }
        if (holder is BannerViewHolder) {
            holder.bind(homeBannerAdapter!!)
        }
        if (holder is DateViewHolder) {
            holder.bind((dataList[position]!! as HomeRVData.DateSeparator).data)
        }

    }


    inner class NewsViewHolder(binding: ItemRvNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        val mImage = binding.imageRvImage
        val mTitle = binding.tvRvTitle
        val mHint = binding.tvRvHint
        val mItem = binding.itemRv

        //定义点击事件
        fun initClick() {

            mItem.setOnClickListener {
                val news = (dataList[this.position] as HomeRVData.News).data

                var dateIndex: Int =-1
                //日期数据的索引
                for (i in this.position downTo 0) {
                    val item = dataList[i]
                    if (item is HomeRVData.DateSeparator) {
                        dateIndex = i
                        break
                    }
                }
                Log.d("ld00","${news.id}");
                DetailActivity.startActivity(context,news.id,(dataList[dateIndex] as HomeRVData.DateSeparator).data)
                //TODO(定义点击事件接口)
            }
        }

        //绑定信息
        fun bind(new: StoryBefore) {
            mHint.text = new.hint
            mTitle.text = new.title
            //加载网络图片
            val requestOptions: RequestOptions =
                RequestOptions().placeholder(R.drawable.placeholder)
                    .fallback(R.drawable.placeholder)
            Glide.with(context).load(new.images[0]).apply(requestOptions).into(mImage)
        }

        //init里面注册点击事件，防止多次注册点击事件
        init {
            initClick()
        }

    }

    inner class LoadingViewHolder(binding: ItemFooterLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    inner class BannerViewHolder(private val binding: ItemRvBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val banner = binding.banner

        fun bind(adapter: HomeBannerAdapter) {
            banner.setData(adapter)
        }
    }

    inner class DateViewHolder(private val binding: ItemDateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(date: String) {
            val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
            val date = LocalDate.parse(date, formatter)
            val month = date.monthValue  // 获取数字月份：5
            val day = date.dayOfMonth    // 获取日：2
            binding.date.text = "${month}月${day}日"
        }
    }


}