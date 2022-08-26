package eu.withoutaname.mod.immersivesignals.signal.block.client

import eu.withoutaname.mod.immersivesignals.ImmersiveSignals
import eu.withoutaname.mod.immersivesignals.block.AbstractDynamicBakedModel
import eu.withoutaname.mod.immersivesignals.signal.block.SignalEntity
import eu.withoutaname.mod.immersivesignals.signal.util.SignalBlockConfiguration
import net.minecraft.block.BlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.model.BakedQuad
import net.minecraft.client.renderer.texture.AtlasTexture
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.util.Direction
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.data.IModelData
import java.util.Random

class SignalBakedModel : AbstractDynamicBakedModel() {
    companion object {
        val blankTexture = ResourceLocation(ImmersiveSignals.ID, "custom/blank")
        val blankSprite by lazy {
            Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(blankTexture)
        }
    }

    override fun getQuads(
        state: BlockState?,
        side: Direction?,
        rand: Random,
        extraData: IModelData
    ) =
        mutableListOf<BakedQuad>().apply {
            val config = extraData.getData(SignalEntity.config) ?: SignalBlockConfiguration()
            addAll(mast(config.mastHeight, side))
        }

    private fun mast(height: Float, side: Direction?) = mutableListOf<BakedQuad>().apply {
        if (side == null)
            for (i in 0..15 step 4) add(
                createQuad(
                    v(.4f, height, .6f),
                    v(.4f, 0f, .6f),
                    v(.6f, 0f, .6f),
                    v(.6f, height, .6f),
                    i,
                    blankSprite,
                    r = 0f,
                    g = 1f,
                    b = 0f
                )
            )
        if (side == null && height < 1 || side == Direction.UP) add(
            createQuad(
                v(.4f, height, .4f),
                v(.4f, height, .6f),
                v(.6f, height, .6f),
                v(.6f, height, .4f),
                blankSprite,
                r = 0f,
                g = 1f,
                b = 0f
            )
        )
        if (side == Direction.DOWN) add(
            createQuad(
                v(.6f, 0f, .4f),
                v(.6f, 0f, .6f),
                v(.4f, 0f, .6f),
                v(.4f, 0f, .4f),
                blankSprite,
                r = 0f,
                g = 1f,
                b = 0f
            )
        )
    }

    override fun getParticleIcon(): TextureAtlasSprite {
        return blankSprite
    }

}
