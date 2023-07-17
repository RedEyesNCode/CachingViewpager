package com.redeyesncode.cachingviewpager.ui.repo

import com.redeyesncode.cachingviewpager.base.Resource
import com.redeyesncode.cachingviewpager.data.ResponseMarsPhotos

interface DashboardRepo {
    suspend fun getMARSPhotos(): Resource<ArrayList<ResponseMarsPhotos>>


}