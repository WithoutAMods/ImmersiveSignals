package eu.withoutaname.mod.immersivesignals.signal.block.client

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject
import eu.withoutaname.mod.immersivesignals.ImmersiveSignals
import net.minecraft.resources.IResourceManager
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.IModelLoader

object SignalModelLoader : IModelLoader<SignalModelGeometry> {

    val ID = ResourceLocation(ImmersiveSignals.ID, "signal_loader")

    @Suppress("EmptyFunctionBlock")
    override fun onResourceManagerReload(resourceManager: IResourceManager) {
    }

    override fun read(
        deserializationContext: JsonDeserializationContext,
        modelContents: JsonObject
    ) = SignalModelGeometry()
}
