package com.assessment.zhihunes.adapter

import TopStory
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assessment.zhihunes.R
import com.assessment.zhihunes.databinding.ItemBannerBinding
import com.assessment.zhihunes.view.TopDetailActivity

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * description ： 首页的轮播图的Adapter
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/1 23:31
 */
class HomeBannerAdapter(val topList:List<TopStory>, private val context: Context) : RecyclerView.Adapter<HomeBannerAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(binding)
        return holder
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = topList[position%topList.size]
        with(holder){
            mTitle.text = item.title
            mAuthor.text = item.hint

            //加载网络图片
            val requestOptions: RequestOptions = RequestOptions().placeholder(R.drawable.placeholder)
                .fallback(R.drawable.placeholder)
            Glide.with(context).load(item.image).apply(requestOptions).into(mImage)

        }

    }
    inner class ViewHolder(binding: ItemBannerBinding): RecyclerView.ViewHolder(binding.root){
        val mTitle = binding.tvCustombannerTitle
        val mAuthor = binding.tvCustombannerAuthor
        val mImage = binding.imgCustombannerItem

        fun initClick(){
            mImage.setOnClickListener{
                TopDetailActivity.startActivity(context,topList[position%topList.size].id)
            }
        }
        init {
            initClick()
        }

    }
}