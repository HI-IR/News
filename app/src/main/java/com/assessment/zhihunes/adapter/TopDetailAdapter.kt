package com.assessment.zhihunes.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.recyclerview.widget.RecyclerView
import com.assessment.zhihunes.databinding.ItemWebBinding
import com.assessment.zhihunes.databinding.PlaceHolderBinding
import com.assessment.zhihunes.viewmodel.TopDetailViewModel

/**
 * description ： 轮播图的VP2适配器
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/3 22:14
 */
class TopDetailAdapter(private val viewModel: TopDetailViewModel) : RecyclerView.Adapter<TopDetailAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemWebBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return viewModel.TopStory.value!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel.TopStory.value!![position].url)
    }

    inner class ViewHolder(private val binding: ItemWebBinding):RecyclerView.ViewHolder(binding.root){
            @SuppressLint("SetJavaScriptEnabled")
            fun bind(mUrl: String) {
                with(binding.webview) {
                    settings.cacheMode = android.webkit.WebSettings.LOAD_NO_CACHE
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()
                    loadUrl(mUrl)
                }
            }
    }
}