package com.example.checkers.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromString(value: String): Map<String, Int> {
        val listType = object : TypeToken<Map<String, Int>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: Map<String, Int>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}