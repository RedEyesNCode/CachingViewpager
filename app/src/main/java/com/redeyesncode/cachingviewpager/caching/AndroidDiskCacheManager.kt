package com.redeyesncode.cachingviewpager.caching

import android.content.Context
import com.jakewharton.disklrucache.DiskLruCache
import java.io.File
import java.io.IOException

object AndroidDiskCacheManager {
    private const val CACHE_VERSION = 1
    private const val MAX_CACHE_SIZE = 10 * 1024 * 1024 // 10MB
    private const val CACHE_DIRECTORY = "app_cache"
    private const val VALUE_COUNT = 1

    private lateinit var diskLruCache: DiskLruCache

    fun initialize(context: Context) {
        try {
            val cacheDir = File(context.cacheDir, CACHE_DIRECTORY)
            diskLruCache = DiskLruCache.open(cacheDir, CACHE_VERSION, VALUE_COUNT, MAX_CACHE_SIZE.toLong())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // Other cache management methods...

    fun getDiskLruCache(): DiskLruCache {
        return diskLruCache
    }
}