package ru.padawans.moviesapp.data.cache

import android.util.Log
import kotlinx.coroutines.*

object Cache: ICache {
    val cache = mutableMapOf<String,Any>()

    override fun set(key: String, value: Any, lifetimeSeconds: Long) {
        cache.put(key,value)
        cacheEndOfLife(key,lifetimeSeconds*1000)
    }

    override fun get(key: String): Any? {
        return cache.get(key)
    }

    override fun clear(key: String) {
       cache.remove(key)
    }

    private fun cacheEndOfLife(key: String,lifetimeSeconds: Long)  {
        GlobalScope.launch(Dispatchers.IO) {
            delay(lifetimeSeconds)
            clear(key)
        }
    }
}