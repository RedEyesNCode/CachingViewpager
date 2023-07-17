package com.redeyesncode.cachingviewpager.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.redeyesncode.cachingviewpager.retrofit.AppEndPoint
import com.redeyesncode.cachingviewpager.ui.repo.DashboardRepo
import kotlinx.coroutines.CoroutineDispatcher

class ViewmodelProvider(
    val app: Application,
    private val repos: DashboardRepo,
    private val dispatchers: CoroutineDispatcher,
    private val appApi: AppEndPoint

): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DashboardViewModel(app,repos,dispatchers,appApi) as T
    }
}