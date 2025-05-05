package com.assessment.zhihunes.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.assessment.zhihunes.databinding.ItemWebBinding
import com.assessment.zhihunes.databinding.PlaceHolderBinding
import com.assessment.zhihunes.viewmodel.NewDetailViewModel


/**
 * description ： Detail页面的Adapter(VP2)
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/3 00:52
 */
class DetailAdapter(private val viewModel: NewDetailViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {
        val NEWS_VIEW = 0
        val LOADING_VIEW = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return when (viewType) {
            NEWS_VIEW -> NewsViewHolder(
                ItemWebBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> LoadingViewHodle(
                PlaceHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {

        return viewModel.adapterDataList.value?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (viewModel.adapterDataList.value!![position] == null) LOADING_VIEW else NEWS_VIEW
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (holder is NewsViewHolder) {
            holder.bind(viewModel.adapterDataList.value!![position]!!.url)
        }


    }


    inner class NewsViewHolder(private val binding: ItemWebBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetJavaScriptEnabled")
        fun bind(mUrl: String) {
            with(binding.webview) {
                settings.cacheMode = WebSettings.LOAD_NO_CACHE
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                loadUrl(mUrl)
            }
        }
    }

    inner class LoadingViewHodle(private val binding: PlaceHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}


