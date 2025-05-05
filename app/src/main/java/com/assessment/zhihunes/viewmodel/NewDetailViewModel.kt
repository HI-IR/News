package com.assessment.zhihunes.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.zhihunes.domain.BeforeNews
import com.assessment.zhihunes.domain.ExtraStory
import com.assessment.zhihunes.domain.StoryBefore
import com.assessment.zhihunes.model.DetailModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * description ： TODO:类的作用
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/3 17:59
 */
class NewDetailViewModel: ViewModel() {
    private val model by lazy {
        DetailModel()
    }

    //给Adapter用的DataList
    private val _AdapterDataList: MutableLiveData<MutableList<StoryBefore?>> =
        MutableLiveData(mutableListOf(null,null))//用null表示加载
    val adapterDataList: LiveData<MutableList<StoryBefore?>> = _AdapterDataList

    //用于网络请求的DataList
    private val _NetDataList: MutableLiveData<BeforeNews> = MutableLiveData()
    val netDataList: LiveData<BeforeNews> = _NetDataList



    /*
    引入缓存机制，缓存当前新闻的日期新闻
     */
    //日期信息
     val showDate: MutableLiveData<String> = MutableLiveData()


    //是否在加载中
    private val _isLoadingNext: MutableLiveData<Boolean> = MutableLiveData()
    private val _isLoadingPrev: MutableLiveData<Boolean> = MutableLiveData()
    private val _isInit: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingNext: LiveData<Boolean> = _isLoadingNext
    val isLoadingPrev: LiveData<Boolean> = _isLoadingPrev
    val isInit: LiveData<Boolean> = _isInit

    //当前页的额外消息
    private val _currentExtraStory: MutableLiveData<ExtraStory> = MutableLiveData()
    val currentExtraStory:LiveData<ExtraStory> = _currentExtraStory
    private val _currentPageId: MutableLiveData<Long> = MutableLiveData()


    //设置加载的日期
    fun setDate(date: String) {
        showDate.value = date
    }

    //根据获取指定日期的前一天yyyyMMdd格式
    fun getPreviousDate(dateStr: String): String {
        val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val date = sdf.parse(dateStr) ?: return ""
        val calendar = Calendar.getInstance().apply {
            time = date
            add(Calendar.DAY_OF_MONTH, -1)
        }
        return sdf.format(calendar.time)
    }

    //获取指定日期的后一天yyyyMMdd格式
    fun getNextDate(dateStr: String): String {
        val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val date = sdf.parse(dateStr) ?: return ""
        val calendar = Calendar.getInstance().apply {
            time = date
            add(Calendar.DAY_OF_MONTH, 1)
        }
        return sdf.format(calendar.time)
    }

    //初始化日期新闻
    fun initView(date: String) {
        if (_isLoadingNext.value == true || _isLoadingPrev.value == true) return
        _isInit.value = true
        setDate(date)
        Log.d("initView","${date}}");
        viewModelScope.launch {
            try {
                val currentDeferred = async {
                    model.doGetBeforeNews(showDate.value!!)
                }

                val current = currentDeferred.await()

                //更新数据
                _NetDataList.value = current


                _AdapterDataList.value!!.addAll(1,current.stories)

            } catch (e: Exception) {
                Log.d("ld", "${e.message}");
            } finally {
                _isInit.value = false
            }
        }
    }

    //加载下一个日期的新闻
    fun loadNextDay() {
        if (_isInit.value == true || _isLoadingPrev.value == true) return

        _isLoadingNext.value = true
        //设置显示的日历为下一天
        setDate(getNextDate(showDate.value!!))
        viewModelScope.launch {
            try {
                //同时请求后一天日期的新闻

                val nextDeferred = async {
                    //加载后一天
                    model.doGetBeforeNews(showDate.value!!)
                }
                val next = nextDeferred.await()

                //更新数据
                _NetDataList.value = next

                _AdapterDataList.value!!.clear()
                _AdapterDataList.value = mutableListOf(null,null)
                _AdapterDataList.value!!.addAll(1,next.stories)
            } catch (e: Exception) {
                Log.d("ld", "${e.message}");
            } finally {
                _isLoadingNext.value = false
            }
        }
    }

    //加载上一个日期的新闻
    fun loadPrevDay() {
        if (_isInit.value == true || _isLoadingNext.value == true) return
        _isLoadingPrev.value = true
        setDate(getPreviousDate(showDate.value!!))
        Log.d("ld","${showDate.value!!}");
        viewModelScope.launch {
            try {
                val prevDeferred = async {
                    //加载前一天
                    model.doGetBeforeNews(showDate.value!!)
                }
                val prev = prevDeferred.await()

                //更新数据
                _NetDataList.value = prev

                _AdapterDataList.value!!.clear()
                _AdapterDataList.value = mutableListOf(null,null)
                _AdapterDataList.value!!.addAll(1,prev.stories)
            } catch (e: Exception) {
                Log.d("ld", "${e.message}");
            } finally {
                _isLoadingPrev.value = false
            }
        }


    }
    //获取对应新闻的额外消息
    fun getExtraStory(id: Long){
        viewModelScope.launch {
            model.doGetExtraStory(id,{
                _currentPageId.value = id
                _currentExtraStory.value = it
            },{})
        }
    }

}