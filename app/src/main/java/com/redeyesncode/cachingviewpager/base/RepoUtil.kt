package com.redeyesncode.cachingviewpager.base

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jakewharton.disklrucache.DiskLruCache
import java.io.IOException

val Gson = GsonBuilder().create()
inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {
    return try {
        action()
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An unknown error occured")
    }
}
inline fun <reified T> DiskLruCache.getNetworkResponse(cacheKey: String): T? {
    val snapshot = this.get(cacheKey)
    return snapshot?.use {
        val json = it.getString(0)
        val type = object : TypeToken<T>() {}.type
        Gson.fromJson(json, type)
    }
}

fun DiskLruCache.putNetworkResponse(cacheKey: String, response: Any) {
    val editor = this.edit(cacheKey)
    editor?.let {
        try {
            val json =Gson.toJson(response)
            it.set(0, json)
            it.commit()
        } catch (e: IOException) {
            e.printStackTrace()
            it.abort()
        }
    }
}