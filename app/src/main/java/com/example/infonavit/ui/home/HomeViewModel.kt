package com.example.infonavit.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.example.infonavit.data.models.benevits.BenevitsResponse
import com.example.infonavit.data.models.login.LoginResponse
import com.example.infonavit.data.models.wallets.WalletResponse
import com.example.infonavit.repository.RepositoryImpl
import com.example.infonavit.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.Exception

class  HomeViewModel(private val repo: RepositoryImpl) : ViewModel() {


    private val _walletsResponse: MutableLiveData<Resource<WalletResponse>> = MutableLiveData()

    val walletResponse: LiveData<Resource<WalletResponse>>
        get() = _walletsResponse


    private val _benevitsResponse: MutableLiveData<Resource<BenevitsResponse>> = MutableLiveData()

    val benevitsResponse: LiveData<Resource<BenevitsResponse>>
        get() = _benevitsResponse



    init {
        getWallets()
    }

    fun getBenevits(token:String) = viewModelScope.launch {
        _benevitsResponse.value = Resource.Loading()
        try {
            _benevitsResponse.value = repo.getBenevits(token)
        }catch (e: Exception){
            e.printStackTrace()
            e.message?.let { Log.e("fail: ", it) }
            _benevitsResponse.value = Resource.Failure(e)
        }
    }


    fun getWallets() = viewModelScope.launch {
        _walletsResponse.value = Resource.Loading()
        try {
            _walletsResponse.value = repo.getWallets()
        }catch (e: Exception){
            e.printStackTrace()
            e.message?.let { Log.e("fail: ", it) }
            _walletsResponse.value = Resource.Failure(e)
        }
    }


}