package eu.withoutaname.mod.immersivesignals.signal.block.client

import com.mojang.datafixers.util.Pair
import net.minecraft.client.renderer.model.IModelTransform
import net.minecraft.client.renderer.model.IUnbakedModel
import net.minecraft.client.renderer.model.ItemOverrideList
import net.minecraft.client.renderer.model.ModelBakery
import net.minecraft.client.renderer.model.RenderMaterial
import net.minecraft.client.renderer.texture.AtlasTexture
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.IModelConfiguration
import net.minecraftforge.client.model.geometry.IModelGeometry
import java.util.function.Function

class SignalModelGeometry : IModelGeometry<SignalModelGeometry> {
    override fun bake(
        owner: IModelConfiguration?,
        bakery: ModelBakery?,
        spriteGetter: Function<RenderMaterial, TextureAtlasSprite>?,
        modelTransform: IModelTransform?,
        overrides: ItemOverrideList?,
        modelLocation: ResourceLocation?
    ) = SignalBakedModel()

    override fun getTextures(
        owner: IModelConfiguration?,
        modelGetter: Function<ResourceLocation, IUnbakedModel>?,
        missingTextureErrors: MutableSet<Pair<String, String>>?
    ) = mutableListOf<RenderMaterial>().apply {
        add(RenderMaterial(AtlasTexture.LOCATION_BLOCKS, SignalBakedModel.blankTexture))
    }
}
