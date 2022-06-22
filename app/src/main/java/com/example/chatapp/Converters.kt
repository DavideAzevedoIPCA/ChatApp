package com.example.chatapp

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import org.json.JSONArray
import java.lang.reflect.Type
import java.sql.Date


class Converters {
    @TypeConverter
    fun fromTimestamp(value: Date?): Long? {
        return value?.time?.toLong()
    }

    @TypeConverter
    fun dateToTimestamp(date: Long?): Date? {
        return date?.toLong()?.let { Date(it) }
    }

    @TypeConverter
    fun fromListString(value: List<String>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromString(value: String?): List<String?>? {
        val listType : Type = object : TypeToken<List<String?>?>() { }.type
        return Gson().fromJson<List<String>>(value, listType)
    }
}