package com.assessment.zhihunes.viewmodel

import TopStory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.zhihunes.domain.ExtraStory
import com.assessment.zhihunes.model.TopDetailModel
import kotlinx.coroutines.launch

/**
 * description ： TODO:类的作用
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/3 21:56
 */
class TopDetailViewModel: ViewModel() {
    private val model by lazy {
        TopDetailModel()
    }
    //当前页的额外消息
    private val _currentExtraStory: MutableLiveData<ExtraStory> = MutableLiveData()
    val currentExtraStory:LiveData<ExtraStory> = _currentExtraStory
    private val _currentPageId: MutableLiveData<Long> = MutableLiveData()


    private val _TopList:MutableLiveData<List<TopStory>> =MutableLiveData()
    val TopStory: LiveData<List<TopStory>> = _TopList

    fun setData(list: List<TopStory>){
        _TopList.value = list
    }

    //获取轮播图信息,初始化信息
    fun getTopInfo(){
        viewModelScope.launch {
            model.doGetLatestNews({
                _TopList.value = it.top_stories
            },{})
        }
    }

    //获取对应新闻的额外消息
    fun getExtraStory(id: Int){
        viewModelScope.launch {
            model.doGetExtraStory(id,{
                _currentPageId.value = id.toLong()
                _currentExtraStory.value = it
            },{})
        }
    }

}