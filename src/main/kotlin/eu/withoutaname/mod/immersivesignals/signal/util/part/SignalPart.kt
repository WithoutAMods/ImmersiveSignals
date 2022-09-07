package eu.withoutaname.mod.immersivesignals.signal.util.part

import kotlinx.serialization.Serializable
import net.minecraft.client.renderer.model.BakedQuad
import net.minecraft.util.Direction

@Serializable
sealed class SignalPart {
    abstract fun getQuads(rotation: Int, side: Direction?): Collection<BakedQuad>
}