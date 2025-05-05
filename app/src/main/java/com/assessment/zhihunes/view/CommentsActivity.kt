package com.assessment.zhihunes.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.assessment.zhihunes.adapter.CommentsAdapter
import com.assessment.zhihunes.databinding.ActivityCommentsBinding
import com.assessment.zhihunes.databinding.ActivityDetailBinding
import com.assessment.zhihunes.viewmodel.CommentsViewModel

class CommentsActivity : AppCompatActivity() {
    private val binding: ActivityCommentsBinding by lazy {
        ActivityCommentsBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy {
        ViewModelProvider(this)[CommentsViewModel::class.java]
    }
    private var adapter: CommentsAdapter? = null

    private var id: Long = -1
    private var count: Int = -1

    companion object {
        fun startActivity(context: Context, id: Long, count: Int) {
            val intent = Intent(context, CommentsActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("count", count)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        initData()
        initViewModle()
        initView()
        initEvent()
    }

    private fun initEvent() {
        binding.back.setOnClickListener{
            finish()
        }
    }

    private fun initView() {
        binding.titleCounts.text = "${count} 条评论"
        binding.counts.text = "${count} 条评论"
    }

    private fun initData() {
        id = intent.getLongExtra("id", -1)
        count = intent.getIntExtra("count", -1)
        Log.d("ld","id=${id} , count=${count}");
    }

    private fun initViewModle() {
        viewModel.initData(id)

        viewModel.adapterComments.observe(this){
            if (adapter==null){
                if (viewModel.adapterComments.value!!.size ==0){
                    return@observe
                }
                adapter = CommentsAdapter(this,viewModel)
                binding.rv.adapter = adapter
                binding.rv.layoutManager =LinearLayoutManager(this)
            }
        }
    }
}