package com.redeyesncode.cachingviewpager.ui.repo

import com.redeyesncode.cachingviewpager.base.Resource
import com.redeyesncode.cachingviewpager.base.safeCall
import com.redeyesncode.cachingviewpager.data.ResponseMarsPhotos
import com.redeyesncode.cachingviewpager.retrofit.NetworkInstance

class DashboardCalls: DashboardRepo {
    override suspend fun getMARSPhotos(): Resource<ArrayList<ResponseMarsPhotos>> {
        return safeCall {
            safeCall {
                val response =
                    NetworkInstance.api.getMarsPhotos()
                Resource.Success(response.body()!!)
            }
        }
    }
}