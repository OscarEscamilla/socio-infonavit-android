package com.example.infonavit.repository

import com.example.infonavit.data.models.benevits.BenevitsResponse
import com.example.infonavit.data.models.login.LoginResponse
import com.example.infonavit.data.models.wallets.WalletResponse
import com.example.infonavit.vo.Resource

interface IRepository {

    suspend fun login(email: String, password: String): Resource<LoginResponse>

    suspend fun getWallets():  Resource<WalletResponse>

    suspend fun getBenevits(token: String): Resource<BenevitsResponse>

    suspend fun logout(token: String): Resource<Boolean>

}