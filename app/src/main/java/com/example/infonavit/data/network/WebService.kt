package com.example.infonavit.data.network

import com.example.infonavit.data.models.benevits.BenevitsResponse
import com.example.infonavit.data.models.login.LoginResponse
import com.example.infonavit.data.models.login.BodyLogin
import com.example.infonavit.data.models.wallets.WalletResponse
import retrofit2.Response
import retrofit2.http.*

interface WebService {

    @POST("login")
    suspend fun login(@Body bodyLogin: BodyLogin): Response<LoginResponse>


    @GET("member/wallets")
    suspend fun getWallets(): WalletResponse


    @GET("member/landing_benevits")
    suspend fun getBenevits(@Header("Authorization") token: String): BenevitsResponse

    @DELETE("logout")
    suspend fun logout(@Header("Authorization") token: String): Response<Boolean>

}