package com.example.infonavit.repository

import com.example.infonavit.data.models.benevits.BenevitsResponse
import com.example.infonavit.data.models.login.LoginResponse
import com.example.infonavit.data.models.wallets.WalletResponse
import com.example.infonavit.data.network.RemoteDataSource
import com.example.infonavit.vo.Resource
import retrofit2.Response

class RepositoryImpl(private val remoteDataSource: RemoteDataSource): IRepository{


    override suspend fun login(email: String, password: String): Resource<LoginResponse> {
        return remoteDataSource.getDataLogin(email, password)
    }

    override suspend fun getWallets(): Resource<WalletResponse> {
        return  remoteDataSource.getWallets()
    }

    override suspend fun getBenevits(token: String): Resource<BenevitsResponse> {
        return  remoteDataSource.getBenevits(token)
    }

    override suspend fun logout(token: String): Resource<Boolean> {
        return  remoteDataSource.logout(token)
    }


}