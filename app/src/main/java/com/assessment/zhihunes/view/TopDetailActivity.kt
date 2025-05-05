package com.assessment.zhihunes.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.assessment.zhihunes.adapter.TopDetailAdapter
import com.assessment.zhihunes.customview.CustomShare
import com.assessment.zhihunes.databinding.ActivityDetailBinding
import com.assessment.zhihunes.utils.CopyUtils
import com.assessment.zhihunes.utils.ShareUtils
import com.assessment.zhihunes.viewmodel.TopDetailViewModel

class TopDetailActivity : AppCompatActivity() {
    private var adapter: TopDetailAdapter? =null
    //当前显示页新闻的id
    private var id:Long =-1
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy {
        ViewModelProvider(this)[TopDetailViewModel::class.java]
    }
    companion object{
        fun startActivity(context: Context, id: Int){
            val intent = Intent(context,TopDetailActivity::class.java)
            intent.putExtra("id",id)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        id = intent.getIntExtra("id",-1).toLong()
        Log.d("TopLd","${id} ->2");
        initViewModel()
        initEvent()
    }

    private fun initEvent() {
        binding.vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                id = viewModel.TopStory.value!![position]!!.id.toLong()
                viewModel.getExtraStory(viewModel.TopStory.value!![position]!!.id)
            }
        })

        //跳转评论
        binding.bottomtoolbar.setOnCommentsClickListener{
            Log.d("TopLd","${id}");
            CommentsActivity.startActivity(this,id,viewModel.currentExtraStory.value!!.comments)
        }

        //返回的点击事件
        binding.bottomtoolbar.setOnBackClickListener { finish() }

        //分享的点击事件
        binding.bottomtoolbar.setOnShareClickListener {
            val customShare: CustomShare = CustomShare(this)

            // 设置不同分享渠道的点击监听器
            customShare.setOnQqClickListener {
                var link =""
                //寻找到当前页的链接，并复制
                viewModel.TopStory.value!!.forEach {
                    if (it.id.toLong() == id){
                        link = it.url
                    }
                }
                ShareUtils.shareToQQ(this@TopDetailActivity,link)
                customShare.dismiss()
            }

            customShare.setOnWxClickListener {
                var link =""
                //寻找到当前页的链接，并复制
                viewModel.TopStory.value!!.forEach {
                    if (it.id.toLong() == id){
                        link = it.url
                    }
                }
                ShareUtils.shareToWX(this@TopDetailActivity,link)
                customShare.dismiss()
            }

            customShare.setOnLinkClickListener {

                //寻找到当前页的链接，并复制
                viewModel.TopStory.value!!.forEach {
                    if (it.id.toLong() == id){
                        CopyUtils.copy(this@TopDetailActivity ,it.url)
                    }
                }

                Toast.makeText(this, "已复制链接", Toast.LENGTH_SHORT).show()

                customShare.dismiss()
            }

            customShare.setOnBrowseClickListener {
                var link =""
                //寻找到当前页的链接，并复制
                viewModel.TopStory.value!!.forEach {
                    if (it.id.toLong() == id){
                        link = it.url
                    }
                }
                ShareUtils.shareToBrowser(this@TopDetailActivity,link)
                customShare.dismiss()
            }

            // 显示对话框
            customShare.show()

        }





    }

    private fun initViewModel() {
        viewModel.getTopInfo()
        viewModel.TopStory.observe(this){
            if (adapter==null){
                adapter = TopDetailAdapter(viewModel)
                binding.vp2.adapter = adapter
                viewModel.TopStory.value!!.forEachIndexed { index, topStory ->
                    if (topStory.id.toLong() == id){
                        binding.vp2.setCurrentItem(index)
                    }
                }
            }
        }


        viewModel.currentExtraStory.observe(this) {
            binding.bottomtoolbar.setData(it.comments, it.popularity)
        }
    }


}