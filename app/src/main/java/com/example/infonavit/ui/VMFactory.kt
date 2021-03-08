package com.example.infonavit.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.infonavit.repository.RepositoryImpl


class VMFactory(private val repo: RepositoryImpl): ViewModelProvider.Factory {



    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RepositoryImpl::class.java).newInstance(repo)
    }


}