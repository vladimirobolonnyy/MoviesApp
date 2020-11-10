package ru.padawans.moviesapp.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken


class Converters {
    @TypeConverter
    fun fromString(value: String?): List<Int?>? {
        val listType = object : TypeToken<List<Int?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromIntList(list: List<Int?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

}