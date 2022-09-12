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
            config.parts.forEach {
                addAll(it.getQuads(config.rotation, side))
            }
        }

    override fun getParticleIcon(): TextureAtlasSprite {
        return blankSprite
    }
}
