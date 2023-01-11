package com.hsmnzaydn.chatgptv2.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel constructor(): ViewModel() {


    val loadingState = MutableLiveData<Boolean>()
    private val errorState = MutableLiveData<String>()
    private val messageState = MutableLiveData<String>()

    fun showLoading(){
        loadingState.value = true
    }

    fun hideLoading(){
        loadingState.value = false
    }

    fun showMessage(message:String){
        messageState.value = message
    }
    fun showError(errorMessage:String){
        errorState.value = errorMessage
    }
}