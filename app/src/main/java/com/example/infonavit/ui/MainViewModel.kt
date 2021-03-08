package com.example.infonavit.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infonavit.repository.RepositoryImpl
import com.example.infonavit.vo.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(private val repo: RepositoryImpl): ViewModel() {


    private val _logoutResponse: MutableLiveData<Resource<Boolean>> = MutableLiveData()

    val logoutResponse: LiveData<Resource<Boolean>>
        get() = _logoutResponse


    fun logout(token: String) = viewModelScope.launch {
        _logoutResponse.value = Resource.Loading()
        try {
            _logoutResponse.value = repo.logout(token)
        }catch (e: Exception){
            e.printStackTrace()
            e.message?.let { Log.e("fail: ", it) }
            _logoutResponse.value = Resource.Failure(e)
        }
    }
}