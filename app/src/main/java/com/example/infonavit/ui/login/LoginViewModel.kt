package com.example.infonavit.ui.login

import android.util.JsonToken
import android.util.Log
import androidx.lifecycle.*
import com.example.infonavit.data.models.login.LoginResponse
import com.example.infonavit.repository.RepositoryImpl
import com.example.infonavit.vo.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel(private val repo: RepositoryImpl): ViewModel() {




    private val email = MutableLiveData<String>()
    private val password = MutableLiveData<String>()

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()

    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse


    fun login(email: String, password: String) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading()
        try {
            _loginResponse.value = repo.login(email, password)
        }catch (e: Exception){
            e.printStackTrace()
            e.message?.let { Log.e("fail: ", it) }
            _loginResponse.value = Resource.Failure(e)
        }
    }




    fun setEmail(txt: String){
        email.value = txt
    }

    fun getEmailLiveData(): LiveData<String>{
        return email
    }

    fun setPassword(txt: String){
        password.value = txt
    }

    fun getPasswordLiveData(): LiveData<String>{
        return password
    }



}