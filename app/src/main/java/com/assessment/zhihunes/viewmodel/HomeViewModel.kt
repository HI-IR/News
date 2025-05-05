package com.assessment.zhihunes.viewmodel

import LatestNews
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.zhihunes.domain.BeforeNews

import com.assessment.zhihunes.model.HomeModel
import com.assessment.zhihunes.utils.DateUtils
import kotlinx.coroutines.launch

/**
 * description ： Home页的ViewModel
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/1 23:31
 */
class HomeViewModel: ViewModel() {
    private val model by lazy { HomeModel() }

    private val currentDayAgo:MutableLiveData<Int> = MutableLiveData(-1)
    private val currentDateNews:MutableLiveData<String> = MutableLiveData(DateUtils.getDateAgo(currentDayAgo.value!!))
    val isLoading = MutableLiveData(false)


    private val _LatestNews = MutableLiveData<LatestNews>()
    val LatestNews:LiveData<LatestNews> = _LatestNews

    private val _LatestNewsError = MutableLiveData<String>()
    val LatestNewsError:LiveData<String> = _LatestNewsError

    private val _BeforeNews = MutableLiveData<BeforeNews>()
    val BeforeNews:LiveData<BeforeNews> = _BeforeNews

    fun doGetLatestNews(){
        try {
            viewModelScope.launch {
                model.doGetLatestNews({_LatestNews.value = it},{_LatestNewsError.value = it})
            }
        } catch (e: Exception) {
            _LatestNewsError.value = "网络访问失败"
        }
    }

    //更新往期消息
    fun loadBeforeDate(){
        isLoading.value = true
        viewModelScope.launch {
            model.doGetBeforeNews(currentDateNews.value!!,{
                _BeforeNews.value = it
                currentDayAgo.value = currentDayAgo.value?.plus(1)
                currentDateNews.value = DateUtils.getDateAgo(currentDayAgo.value!!)
                isLoading.value = false
            },{
                isLoading.value = false
            })
        }
    }



}