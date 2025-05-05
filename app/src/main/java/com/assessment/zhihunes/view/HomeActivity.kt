package com.assessment.zhihunes.view

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assessment.zhihunes.adapter.HomeBannerAdapter
import com.assessment.zhihunes.adapter.HomeRvAdapter
import com.assessment.zhihunes.databinding.ActivityHomeBinding
import com.assessment.zhihunes.viewmodel.HomeViewModel


class HomeActivity : AppCompatActivity() {
    private var adapter: HomeBannerAdapter? = null
    private var rvAdapter: HomeRvAdapter = HomeRvAdapter(this)
    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initTranslucentBars()
        initViewModel()
        initData()

        initEvent()
    }

    private fun initTranslucentBars() {
        // 设置状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }

    private fun initView() {
        //初始化RV布局
        binding.rvHome.adapter = rvAdapter
        binding.rvHome.layoutManager = LinearLayoutManager(this)
    }


    //注册事件
    private fun initEvent() {
        //rv的向下滑动到最底部的点击事件
        binding.rvHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // 仅在向下滑动时检查
                if (dy <= 0) return

                val lm = recyclerView.layoutManager as LinearLayoutManager
                val lastVisible = lm.findLastVisibleItemPosition()//最后一个可见的item的位置

                // 总条目数（不计正在显示的 loading footer）
                var total = rvAdapter?.itemCount
                if (viewModel.isLoading.value == true) {
                    // 如果 footer 已经添加，则真实数据条数要减 1
                    total = total!!.minus(1)
                }

                //当滑到真实数据的最后一项时，触发加载
                if (total != null) {
                    if (lastVisible == total - 1 && viewModel.isLoading.value == false) {
                        Log.d("ld", "rv测试");
                        viewModel.loadBeforeDate()
                    }
                }
            }
        })

    }

    private fun getTopData() {
        viewModel.doGetLatestNews()
    }

    private fun initViewModel() {
        viewModel.LatestNews.observe(this) {
            if (adapter == null) {
                //如果适配器没被初始化则初始化并且设置适配器
                adapter = HomeBannerAdapter(it.top_stories, this@HomeActivity)
                rvAdapter.sethomeBannerAdapter(adapter!!)
                initView()
            } else {
                adapter!!.notifyDataSetChanged()
            }
        }

        viewModel.isLoading.observe(this) {
            if (it) {
                rvAdapter.addLoadingFooter()
            } else {
                viewModel.BeforeNews.value?.let { it1 -> rvAdapter.removeLoadingFooter(it1.date) }
            }
        }

        viewModel.BeforeNews.observe(this) {
            rvAdapter.addNews(it.stories)
        }

    }

    private fun initData() {
        getTopData()
        getBeforeData()
    }

    private fun getBeforeData() {
        viewModel.loadBeforeDate()
    }


}