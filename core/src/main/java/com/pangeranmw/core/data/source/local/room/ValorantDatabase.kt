package com.pangeranmw.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pangeranmw.core.data.source.local.entity.AgentEntity

@Database(entities = [AgentEntity::class], version = 1, exportSchema = false)
@TypeConverters(ListStringConverter::class,ListAbilitiesConverter::class,RoleEntityConverter::class)
abstract class ValorantDatabase : RoomDatabase() {
    abstract fun valorantDao(): ValorantDao
}