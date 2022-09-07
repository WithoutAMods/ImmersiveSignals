package eu.withoutaname.mod.immersivesignals.block

import net.minecraft.client.renderer.model.ItemOverrideList
import net.minecraftforge.client.model.data.IDynamicBakedModel

abstract class AbstractDynamicBakedModel : IDynamicBakedModel {
    override fun useAmbientOcclusion() = true
    override fun isGui3d() = false
    override fun usesBlockLight() = false
    override fun isCustomRenderer() = false
    override fun getOverrides(): ItemOverrideList = ItemOverrideList.EMPTY
}
