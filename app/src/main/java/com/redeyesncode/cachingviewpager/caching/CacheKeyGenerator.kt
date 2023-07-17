package com.redeyesncode.cachingviewpager.caching

import java.security.MessageDigest

object CacheKeyGenerator {
    fun generateCacheKey(keyName: String): String {
        val bytes = keyName.toByteArray()
        val digest = MessageDigest.getInstance("MD5").digest(bytes)
        return digest.joinToString("") { "%02x".format(it) }
    }
}