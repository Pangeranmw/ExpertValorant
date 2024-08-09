package com.pangeranmw.core.data.source.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.pangeranmw.core.data.source.local.entity.RoleEntity

class RoleEntityConverter {

    @TypeConverter
    fun fromString(value: String): RoleEntity {
        return Gson().fromJson(value, RoleEntity::class.java)
    }

    @TypeConverter
    fun fromRoleEntity(role: RoleEntity): String {
        return Gson().toJson(role)
    }
}