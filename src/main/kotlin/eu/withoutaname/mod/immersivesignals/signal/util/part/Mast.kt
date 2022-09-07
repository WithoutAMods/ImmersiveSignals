package eu.withoutaname.mod.immersivesignals.signal.util.part

import eu.withoutaname.mod.immersivesignals.signal.block.client.SignalBakedModel
import eu.withoutaname.mod.immersivesignals.util.createQuad
import eu.withoutaname.mod.immersivesignals.util.v
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.minecraft.client.renderer.model.BakedQuad
import net.minecraft.util.Direction

@Serializable
@SerialName("mast")
data class Mast(val yStart: Float = 0f, val yEnd: Float = 1f, val bottom: Boolean = true, val top: Boolean = true) :
    SignalPart() {

    override fun getQuads(rotation: Int, side: Direction?) = mutableListOf<BakedQuad>().apply {
        if (bottom && side == if (yStart > 0) null else Direction.DOWN) add(
            createQuad(
                v(.6f, yStart, .4f),
                v(.6f, yStart, .6f),
                v(.4f, yStart, .6f),
                v(.4f, yStart, .4f),
                rotation,
                SignalBakedModel.blankSprite,
                r = yStart,
                g = 1f,
                b = yStart
            )
        )
        if (side == null)
            for (i in 0..15 step 4) add(
                createQuad(
                    v(.4f, yEnd, .6f),
                    v(.4f, yStart, .6f),
                    v(.6f, yStart, .6f),
                    v(.6f, yEnd, .6f),
                    (i + rotation) % 16,
                    SignalBakedModel.blankSprite,
                    r = yStart,
                    g = 1f,
                    b = yStart
                )
            )
        if (top && side == if (yEnd < 1) null else Direction.UP) add(
            createQuad(
                v(.4f, yEnd, .4f),
                v(.4f, yEnd, .6f),
                v(.6f, yEnd, .6f),
                v(.6f, yEnd, .4f),
                rotation,
                SignalBakedModel.blankSprite,
                r = yStart,
                g = 1f,
                b = yStart
            )
        )
    }
}
