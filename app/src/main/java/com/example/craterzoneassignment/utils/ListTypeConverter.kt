package com.example.craterzoneassignment.utils

import androidx.room.TypeConverter
import com.example.craterzoneassignment.models.Photo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class ListTypeConverter {

    companion object {
        var gson = Gson()

        @TypeConverter
        @JvmStatic
        fun stringToSomeObjectList(data: String?): List<Photo> {
            if (data == null) {
                return Collections.emptyList()
            }
            val listType: Type = object : TypeToken<List<Photo?>?>() {}.type
            return gson.fromJson(data, listType)
        }

        @TypeConverter
        @JvmStatic
        fun someObjectListToString(someObjects: List<Photo?>?): String {
            return gson.toJson(someObjects)
        }
    }
}