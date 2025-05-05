package com.assessment.zhihunes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.zhihunes.domain.Comment
import com.assessment.zhihunes.domain.Comments
import com.assessment.zhihunes.model.CommentsModel
import kotlinx.coroutines.launch

/**
 * description ： TODO:类的作用
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/4 11:30
 */
class CommentsViewModel: ViewModel() {
    private val model by lazy {
        CommentsModel()
    }

    private val _adapterComments: MutableLiveData<MutableList<Comment>> = MutableLiveData()
    val adapterComments: LiveData<MutableList<Comment>> = _adapterComments


    fun initData(id: Long){
        viewModelScope.launch {
            model.getComments(id,{
                _adapterComments.value = it
            },{})
        }
    }
}