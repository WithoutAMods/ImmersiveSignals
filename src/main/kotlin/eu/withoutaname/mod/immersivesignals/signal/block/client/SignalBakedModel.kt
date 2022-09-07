package eu.withoutaname.mod.immersivesignals.signal.block.client

import eu.withoutaname.mod.immersivesignals.ImmersiveSignals
import eu.withoutaname.mod.immersivesignals.block.AbstractDynamicBakedModel
import eu.withoutaname.mod.immersivesignals.signal.block.SignalEntity
import eu.withoutaname.mod.immersivesignals.signal.util.SignalBlockConfiguration
import eu.withoutaname.mod.immersivesignals.util.createQuad
import eu.withoutaname.mod.immersivesignals.util.v
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
            config.parts.forEach {
                addAll(it.getQuads(config.rotation, side))
            }
            if (side == null) add(
                createQuad(
                    v(0f, .1f, .75f),
                    v(0f, 0f, 1f),
                    v(1f, 0f, 1f),
                    v(1f, .1f, .75f),
                    config.rotation,
                    blankSprite
                )
            )
        }

    override fun getParticleIcon(): TextureAtlasSprite {
        return blankSprite
    }

}
