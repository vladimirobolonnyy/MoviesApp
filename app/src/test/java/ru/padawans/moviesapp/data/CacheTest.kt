package ru.padawans.moviesapp.data

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.padawans.moviesapp.data.cache.Cache
import kotlin.collections.emptyList as emptyList

class CacheTest {

    private val intList = mutableListOf<Int>()
    @Before
    fun setUp() {
        for (i in 1.. 10000){
            intList.add(i)
        }
    }


    @Test
    fun isCacheSetWorking(){
        Cache.set("key","ANY",20)
        assertEquals("ANY", Cache.get("key"))

        Cache.set("oneMoreKey","Tom",20)
        assertEquals("Tom", Cache.get("oneMoreKey"))

        Cache.set("anotherKey","Jerry",20)
        assertEquals("Jerry", Cache.get("anotherKey"))

    }

    @Test
    fun isCacheClearWorking(){
        Cache.set("Clean",
                "The cleanest cache in the wild west",20)
        Cache.clear("Clean")
        assertNull(Cache.get("Clean"))
    }

    @Test
    fun isCacheClearAfterEndOfLife(){
        Cache.set("key",1,1)
       runBlocking {
            delay(2000)
            assertNull(Cache.get("key"))
        }
    }

    @Test
    fun isCacheOverride(){
        Cache.set("key",1,1)
        Cache.set("key",2,1)
        Cache.set("key",3,1)
        assertEquals(3,Cache.get("key"))
    }

    @Test
    fun cacheSpeed(){
        Cache.set("key",intList,20)
        assertEquals(intList,Cache.get("key"))
    }
}