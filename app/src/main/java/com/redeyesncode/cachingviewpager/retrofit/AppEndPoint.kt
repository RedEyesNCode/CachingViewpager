package com.redeyesncode.cachingviewpager.retrofit

import com.redeyesncode.cachingviewpager.data.ResponseMarsPhotos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface AppEndPoint {
    @GET("photos")
    suspend fun getMarsPhotos(): Response<ArrayList<ResponseMarsPhotos>>
}