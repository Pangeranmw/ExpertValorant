package com.pangeranmw.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Agent(
    var agentId: String,
    var name: String,
    var description: String,
    var role: Role,
    var roleFilter: String,
    var codeName: String,
    var fullPortrait: String,
    var background: String,
    var backgroundGradientColors: List<String>,
    var abilities: List<Ability>,
    var isFavorite: Boolean = false
): Parcelable

@Parcelize
data class Ability(
    val slot: String,
    val displayName: String,
    val description: String,
    val displayIcon: String? = null
): Parcelable

@Parcelize
data class Role(
    val displayName: String,
    val description: String,
    val displayIcon: String,
):Parcelable
