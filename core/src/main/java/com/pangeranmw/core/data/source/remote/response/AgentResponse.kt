package com.pangeranmw.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AgentResponse(

	@field:SerializedName("data")
	val data: List<AgentItem> = emptyList(),

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class AbilitiesItem(

	@field:SerializedName("displayIcon")
	val displayIcon: String? = null,

	@field:SerializedName("displayName")
	val displayName: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("slot")
	val slot: String
) : Parcelable

@Parcelize
data class AgentItem(

	@field:SerializedName("killfeedPortrait")
	val killfeedPortrait: String? = null,

	@field:SerializedName("role")
	val role: RoleItem,

	@field:SerializedName("isFullPortraitRightFacing")
	val isFullPortraitRightFacing: Boolean? = null,

	@field:SerializedName("displayName")
	val displayName: String,

	@field:SerializedName("isBaseContent")
	val isBaseContent: Boolean? = null,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("backgroundGradientColors")
	val backgroundGradientColors: List<String>,

	@field:SerializedName("isAvailableForTest")
	val isAvailableForTest: Boolean? = null,

	@field:SerializedName("uuid")
	val uuid: String,

	@field:SerializedName("characterTags")
	val characterTags: List<String>? = null,

	@field:SerializedName("displayIconSmall")
	val displayIconSmall: String? = null,

	@field:SerializedName("fullPortrait")
	val fullPortrait: String,

	@field:SerializedName("fullPortraitV2")
	val fullPortraitV2: String,

	@field:SerializedName("abilities")
	val abilities: List<AbilitiesItem> = emptyList(),

	@field:SerializedName("displayIcon")
	val displayIcon: String? = null,

	@field:SerializedName("recruitmentData")
	val recruitmentData: RecruitmentData? = null,

	@field:SerializedName("bustPortrait")
	val bustPortrait: String? = null,

	@field:SerializedName("background")
	val background: String,

	@field:SerializedName("assetPath")
	val assetPath: String? = null,

	@field:SerializedName("voiceLine")
	val voiceLine: String? = null,

	@field:SerializedName("isPlayableCharacter")
	val isPlayableCharacter: Boolean? = null,

	@field:SerializedName("developerName")
	val developerName: String
) : Parcelable

@Parcelize
data class RoleItem(

	@field:SerializedName("displayIcon")
	val displayIcon: String,

	@field:SerializedName("displayName")
	val displayName: String,

	@field:SerializedName("assetPath")
	val assetPath: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("uuid")
	val uuid: String? = null
) : Parcelable

@Parcelize
data class RecruitmentData(

	@field:SerializedName("levelVpCostOverride")
	val levelVpCostOverride: Int? = null,

	@field:SerializedName("endDate")
	val endDate: String? = null,

	@field:SerializedName("milestoneThreshold")
	val milestoneThreshold: Int? = null,

	@field:SerializedName("milestoneId")
	val milestoneId: String? = null,

	@field:SerializedName("useLevelVpCostOverride")
	val useLevelVpCostOverride: Boolean? = null,

	@field:SerializedName("counterId")
	val counterId: String? = null,

	@field:SerializedName("startDate")
	val startDate: String? = null
) : Parcelable
