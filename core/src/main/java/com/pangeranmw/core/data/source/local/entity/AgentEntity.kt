package com.pangeranmw.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "agent")
@Parcelize
data class AgentEntity(
    @PrimaryKey
    @ColumnInfo(name = "agentId")
    var agentId: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "roleFilter")
    var roleFilter: String,

    @ColumnInfo(name = "role")
    var role: RoleEntity,

    @ColumnInfo(name = "codeName")
    var codeName: String,

    @ColumnInfo(name = "fullPortrait")
    var fullPortrait: String,

    @ColumnInfo("background")
    var background: String,

    @ColumnInfo("backgroundGradientColors")
    var backgroundGradientColors: List<String>,

    @ColumnInfo(name = "abilities")
    var abilities: List<AbilityEntity>,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
): Parcelable

@Parcelize
data class AbilityEntity(
    val slot: String,
    val displayName: String,
    val description: String,
    val displayIcon: String? = null
): Parcelable

@Parcelize
data class RoleEntity(
    val displayName: String,
    val description: String,
    val displayIcon: String,
):Parcelable