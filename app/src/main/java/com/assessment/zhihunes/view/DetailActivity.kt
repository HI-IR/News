package com.assessment.zhihunes.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.assessment.zhihunes.adapter.DetailAdapter
import com.assessment.zhihunes.customview.CustomShare
import com.assessment.zhihunes.databinding.ActivityDetailBinding
import com.assessment.zhihunes.utils.CopyUtils
import com.assessment.zhihunes.utils.DateUtils
import com.assessment.zhihunes.utils.ShareUtils
import com.assessment.zhihunes.viewmodel.NewDetailViewModel


class DetailActivity : AppCompatActivity() {
    private var date: String = ""
    private var id: Long = -1
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private val viewModel: NewDetailViewModel by lazy {
        ViewModelProvider(this)[NewDetailViewModel::class.java]
    }
    private var adapter: DetailAdapter? = null

    companion object {
        fun startActivity(context: Context, id: Long, date: String) {
            //TODO("带实现跳转")
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("date", date)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        enableEdgeToEdge()
        id = intent.getLongExtra("id", 0)
        date = intent.getStringExtra("date")!!
        date = viewModel.getNextDate(date)
        Log.d("ld", date);
        initViewModel()
        initEvent()


    }


    private fun initEvent() {

        binding.bottomtoolbar.setOnBackClickListener { finish() }
        binding.bottomtoolbar.setOnShareClickListener {
            val customShare: CustomShare = CustomShare(this)

            // 设置不同分享渠道的点击监听器
            customShare.setOnQqClickListener {
                var link =""
                //寻找到当前页的链接，并复制
                viewModel.netDataList.value!!.stories.forEach {
                    if (it.id.toLong() == id){
                        link = it.url
                    }
                }
                ShareUtils.shareToQQ(this@DetailActivity,link)
                customShare.dismiss()
            }

            customShare.setOnWxClickListener {
                var link =""
                //寻找到当前页的链接，并复制
                viewModel.netDataList.value!!.stories.forEach {
                    if (it.id.toLong() == id){
                        link = it.url
                    }
                }
                ShareUtils.shareToWX(this@DetailActivity,link)
                customShare.dismiss()
            }

            customShare.setOnLinkClickListener {

                //寻找到当前页的链接，并复制
                viewModel.netDataList.value!!.stories.forEach {
                    if (it.id.toLong() == id){
                        CopyUtils.copy(this@DetailActivity ,it.url)
                    }
                }

                Toast.makeText(this, "已复制链接", Toast.LENGTH_SHORT).show()

                customShare.dismiss()
            }

            customShare.setOnBrowseClickListener {
                var link =""
                //寻找到当前页的链接，并复制
                viewModel.netDataList.value!!.stories.forEach {
                    if (it.id.toLong() == id){
                        link = it.url
                    }
                }
                ShareUtils.shareToBrowser(this@DetailActivity,link)
                customShare.dismiss()
            }

            // 显示对话框
            customShare.show()



        }
        binding.bottomtoolbar.setOnCommentsClickListener {
            CommentsActivity.startActivity(this,id,viewModel.currentExtraStory.value!!.comments)
        }
        binding.bottomtoolbar.setOnPopularityClickListener {
            //TODO(点赞)
        }
        binding.bottomtoolbar.setOnCollectClickListener {
            //TODO(收藏)
        }




        binding.vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position == 0) {
                    //因为日期获取20250503是拿到的5月2日的新闻，所以最新的新闻应该是今天日期+1
                    viewModel.loadNextDay()

                }

                //滑到了最后一页
                if (position == viewModel.adapterDataList.value!!.size - 1) {
                    viewModel.loadPrevDay()
                }

                if(position!=0 && position!= viewModel.adapterDataList.value!!.size -1){
                    id = viewModel.adapterDataList.value!![position]!!.id
                    viewModel.getExtraStory(viewModel.adapterDataList.value!![position]!!.id)
                }


            }

        })

    }


    private fun initViewModel() {
        viewModel.initView(date)
        viewModel.isInit.observe(this) {
            if (!it) {
                if (adapter == null) {
                    adapter = DetailAdapter(viewModel)
                    binding.vp2.adapter = adapter
                    adapter!!.notifyItemRangeChanged(1, viewModel.netDataList.value!!.stories.size)
                    viewModel.adapterDataList.value!!.forEachIndexed { index, storyBefore ->
                        if (index!=0 && index!= viewModel.adapterDataList.value!!.size-1){
                            if (storyBefore!!.id == id) binding.vp2.setCurrentItem(index, false)
                        }
                    }

                }
            }
        }

        viewModel.isLoadingPrev.observe(this) {
            if (!it) {
                adapter!!.notifyItemRangeChanged(1, viewModel.netDataList.value!!.stories.size)
                binding.vp2.setCurrentItem(1, false)
            }

        }

        viewModel.isLoadingNext.observe(this) {
            if (!it) {
                adapter!!.notifyItemRangeChanged(1, viewModel.netDataList.value!!.stories.size)
                if (viewModel.netDataList.value!!.date.toLong()+1 != viewModel.showDate.value!!.toLong()){
                    Log.d("date", DateUtils.formateDate(viewModel.netDataList.value!!.date));
                    Log.d("date", "${viewModel.showDate.value}");
                    Toast.makeText(this,"没有更多咯",Toast.LENGTH_SHORT).show()
                    viewModel.loadPrevDay()
                }else{
                    Log.d("date2", viewModel.netDataList.value!!.date);
                    Log.d("date2", "${viewModel.showDate.value}");
                    binding.vp2.setCurrentItem(viewModel.adapterDataList.value!!.size - 2, false)

                }

            }
        }

        viewModel.currentExtraStory.observe(this) {
            binding.bottomtoolbar.setData(it.comments, it.popularity)
        }

    }


}