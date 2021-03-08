package com.example.infonavit.data.network


import android.util.Log
import com.example.infonavit.data.models.benevits.BenevitsResponse
import com.example.infonavit.data.models.login.BodyLogin
import com.example.infonavit.data.models.login.LoginResponse
import com.example.infonavit.data.models.login.Member
import com.example.infonavit.data.models.login.User
import com.example.infonavit.data.models.wallets.WalletResponse
import com.example.infonavit.vo.Resource
import com.example.infonavit.vo.RetrofitClient


class RemoteDataSource() {



    suspend fun getDataLogin(email: String, password: String): Resource<LoginResponse> {

        var responseDefault =
            LoginResponse("",0,
            Member("",0,"","","","",0,0),
            "",0,"")

        val mResponse = RetrofitClient.webService.login(BodyLogin(User(email, password)))
        if (mResponse.isSuccessful){
            val loginResponse: LoginResponse = mResponse.body()!!
            loginResponse.token = mResponse.headers().get("Authorization").toString()
            return Resource.Success(loginResponse)
        }else{
            return Resource.Success(responseDefault)
        }

    }

    suspend fun getBenevits(token: String): Resource.Success<BenevitsResponse>{
        return Resource.Success(RetrofitClient.webService.getBenevits(token))
    }

    suspend fun getWallets(): Resource.Success<WalletResponse> {
        return Resource.Success(RetrofitClient.webService.getWallets())
    }


    suspend fun logout(token: String): Resource.Success<Boolean>{

        val mResponse = RetrofitClient.webService.logout(token)
        Log.e("LOGOOUT", mResponse.isSuccessful.toString())
        return Resource.Success(mResponse.isSuccessful)
    }

}
