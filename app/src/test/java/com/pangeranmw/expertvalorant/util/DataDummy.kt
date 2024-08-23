package com.pangeranmw.expertvalorant.util

import com.pangeranmw.core.domain.model.Ability
import com.pangeranmw.core.domain.model.Agent
import com.pangeranmw.core.domain.model.Role


object DataDummy {
    fun generateAgent(): List<Agent> {
        val agents = ArrayList<Agent>()
        val roleArray = listOf("Initiator", "Initiator", "Initiator", "Sentinel", "Duelist", "Sentinel", "Initiator", "Initiator", "Sentinel", "Initiator", "Sentinel", "Controller", "Controller", "Duelist", "Controller", "Controller", "Duelist", "Controller", "Duelist", "Duelist", "Sentinel", "Duelist", "Controller", "Duelist")
        val nameArray = listOf("Gekko", "Fade", "Breach", "Deadlock", "Raze", "Chamber", "KAY/O", "Skye", "Cypher", "Sova", "Killjoy", "Harbor", "Viper", "Phoenix", "Astra", "Brimstone", "Iso", "Clove", "Neon", "Yoru", "Sage", "Reyna", "Omen", "Jett")
        for (i in nameArray.indices){
            val agent = Agent(
                agentId = "Id $i",
                name = nameArray[i],
                description = "Description $i",
                roleFilter = roleArray[i],
                role = Role("roleName", "roleDesc", "roleIcon"),
                codeName = "Code Name $i",
                fullPortrait = "fullPortrait $i",
                background = "background $i",
                backgroundGradientColors = listOf("backgroundGradient $i"),
                abilities = listOf(Ability(slot = "slot", displayName = "displayName", description = "description", displayIcon = "displayIcon")),
                isFavorite = Math.random() % 2 == 0.0,
            )
            agents.add(agent)
        }
        return agents
    }
}