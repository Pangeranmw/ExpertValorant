package com.pangeranmw.core.utils

import com.pangeranmw.core.data.source.local.entity.AbilityEntity
import com.pangeranmw.core.data.source.local.entity.AgentEntity
import com.pangeranmw.core.data.source.local.entity.RoleEntity
import com.pangeranmw.core.data.source.remote.response.AgentItem
import com.pangeranmw.core.domain.model.Ability
import com.pangeranmw.core.domain.model.Agent
import com.pangeranmw.core.domain.model.Role


object DataMapper {
    fun mapResponsesToEntities(input: List<AgentItem>): List<AgentEntity> {
        val tourismList = ArrayList<AgentEntity>()
        input.map {
            val tourism = AgentEntity(
                isFavorite = false,
                agentId = it.uuid,
                name = it.displayName,
                description = it.description,
                role = RoleEntity(
                    displayName = it.role.displayName,
                    description = it.role.description,
                    displayIcon = it.role.displayIcon
                ),
                codeName = it.developerName,
                fullPortrait = it.fullPortraitV2,
                background = it.background,
                backgroundGradientColors = it.backgroundGradientColors,
                roleFilter = it.role.displayName,
                abilities = it.abilities.map { ability->
                    AbilityEntity(
                        description = ability.description,
                        displayIcon = ability.displayIcon,
                        displayName = ability.displayName,
                        slot = ability.slot
                    )
                }
            )
            tourismList.add(tourism)
        }
        return tourismList
    }

    fun mapEntitiesToDomain(input: List<AgentEntity>): List<Agent> =
        input.map {
            Agent(
                isFavorite = it.isFavorite,
                agentId = it.agentId,
                name = it.name,
                description = it.description,
                role = Role(
                    displayName = it.role.displayName,
                    description = it.role.description,
                    displayIcon = it.role.displayIcon
                ),
                roleFilter = it.role.displayName,
                codeName = it.codeName,
                fullPortrait = it.fullPortrait,
                background = it.background,
                backgroundGradientColors = it.backgroundGradientColors,
                abilities = it.abilities.map { ability->
                    Ability(
                        description = ability.description,
                        displayIcon = ability.displayIcon,
                        displayName = ability.displayName,
                        slot = ability.slot
                    )
                }
            )
        }

    fun mapDomainToEntity(input: Agent) = AgentEntity(
        isFavorite = input.isFavorite,
        agentId = input.agentId,
        name = input.name,
        description = input.description,
        role = RoleEntity(
            displayName = input.role.displayName,
            description = input.role.description,
            displayIcon = input.role.displayIcon
        ),
        roleFilter = input.roleFilter,
        codeName = input.codeName,
        fullPortrait = input.fullPortrait,
        background = input.background,
        backgroundGradientColors = input.backgroundGradientColors,
        abilities = input.abilities.map { ability->
            AbilityEntity(
                description = ability.description,
                displayIcon = ability.displayIcon,
                displayName = ability.displayName,
                slot = ability.slot
            )
        }
    )
}