package com.pangeranmw.core.data.source.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pangeranmw.core.data.source.local.entity.AbilityEntity

class ListAbilitiesConverter {

    @TypeConverter
    fun fromString(value: String): List<AbilityEntity> {
        val listType = object : TypeToken<List<AbilityEntity>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<AbilityEntity>): String {
        return Gson().toJson(list)
    }
}