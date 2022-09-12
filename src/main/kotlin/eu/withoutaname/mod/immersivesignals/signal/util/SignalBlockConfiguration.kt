package eu.withoutaname.mod.immersivesignals.signal.util

import eu.withoutaname.mod.immersivesignals.signal.util.part.Mast
import eu.withoutaname.mod.immersivesignals.signal.util.part.SignalPart
import kotlinx.serialization.Serializable

@Serializable
data class SignalBlockConfiguration(val rotation: Int = 0, val parts: List<SignalPart> = listOf(Mast()))
