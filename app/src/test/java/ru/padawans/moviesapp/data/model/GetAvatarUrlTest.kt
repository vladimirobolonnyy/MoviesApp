package ru.padawans.moviesapp.data.model

import org.junit.Assert.*
import org.junit.Test
import ru.padawans.moviesapp.BuildConfig
import ru.padawans.network.utils.Resolutions
import ru.padawans.network.model.getAvatarUrl

class GetAvatarUrlTest{

    @Test
    fun isFullUrlWork(){
        val path = getAvatarUrl("https..", Resolutions.REVIEW)
        assertEquals("https..",path)
        
        val path1 = getAvatarUrl("lol/https..", Resolutions.REVIEW)
        assertEquals("https..",path1)
    }

    @Test
    fun isPartUrlWork(){
        val path = getAvatarUrl("/1234.png", Resolutions.REVIEW)
        assertEquals(BuildConfig.BASE_IMG_URL+ Resolutions.REVIEW+"/1234.png",path)
    }

    @Test
    fun isResolutionsWork(){
        val path = getAvatarUrl("/1234.png", Resolutions.REVIEW)
        assertEquals(BuildConfig.BASE_IMG_URL+ Resolutions.REVIEW+"/1234.png",path)

        val path1 = getAvatarUrl("/1234.png", Resolutions.CREDITS)
        assertEquals(BuildConfig.BASE_IMG_URL+ Resolutions.CREDITS+"/1234.png",path1)

        val path2 = getAvatarUrl("/1234.png", Resolutions.REVIEW)
        assertNotEquals(BuildConfig.BASE_IMG_URL+ Resolutions.CREDITS+"/1234.png",path2)
    }


}