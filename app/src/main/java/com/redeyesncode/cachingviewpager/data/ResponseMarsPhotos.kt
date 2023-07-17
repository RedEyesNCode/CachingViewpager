package com.redeyesncode.cachingviewpager.data

import com.google.gson.annotations.SerializedName

data class ResponseMarsPhotos(@SerializedName("id"      ) var id     : String? = null,
                              @SerializedName("img_src" ) var imgSrc : String? = null)