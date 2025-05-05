package com.assessment.zhihunes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assessment.zhihunes.R
import com.assessment.zhihunes.databinding.ItemCommentsBinding
import com.assessment.zhihunes.domain.Comment
import com.assessment.zhihunes.viewmodel.CommentsViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * description ： TODO:类的作用
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/3 11:29
 */
class CommentsAdapter(private val context: Context, private val viewModel: CommentsViewModel) :
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {


    inner class ViewHolder(binding: ItemCommentsBinding) : RecyclerView.ViewHolder(binding.root) {
        val mImage = binding.userimage
        val mUserName = binding.username
        val mContent = binding.content
        val mReplyTo = binding.replyto
        val mPopularity = binding.popularity

        fun bind(comment: Comment) {
            //加载网络图片
            val requestOptions: RequestOptions =
                RequestOptions().placeholder(R.drawable.placeholder)
                    .fallback(R.drawable.placeholder)
            Glide.with(context).load(comment.avatar).apply(requestOptions).into(mImage)

            mUserName.text = comment.author
            mContent.text = comment.content

            if (comment.likes!=0){
                mPopularity.text = comment.likes.toString()
            }
            if (comment.reply_to != null) {
                mReplyTo.text = "// ${comment.reply_to.author}: ${comment.reply_to.content}"
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCommentsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return viewModel.adapterComments.value!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel.adapterComments.value!![position])
    }

}