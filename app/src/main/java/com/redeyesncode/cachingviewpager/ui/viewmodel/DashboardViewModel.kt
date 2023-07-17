package com.redeyesncode.cachingviewpager.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.redeyesncode.cachingviewpager.base.Event
import com.redeyesncode.cachingviewpager.base.Resource
import com.redeyesncode.cachingviewpager.data.ResponseMarsPhotos
import com.redeyesncode.cachingviewpager.retrofit.AppEndPoint
import com.redeyesncode.cachingviewpager.ui.repo.DashboardRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel(val app: Application,
                         private val repos: DashboardRepo,
                         private val dispatchers: CoroutineDispatcher = Dispatchers.Main,
                         val appApi: AppEndPoint
) : AndroidViewModel(app) {
    private val _marsPhotosObserver = MutableLiveData<Event<Resource<ArrayList<ResponseMarsPhotos>>>>()
    val marsPhotos: LiveData<Event<Resource<ArrayList<ResponseMarsPhotos>>>> = _marsPhotosObserver
    fun callMarsPhotoApi(){
        _marsPhotosObserver.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.Main) {
            val result = repos.getMARSPhotos()
            _marsPhotosObserver.postValue(Event(result))
        }
    }
}