package eu.withoutaname.mod.immersivesignals.setup

import eu.withoutaname.mod.immersivesignals.signal.block.client.SignalModelLoader
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderTypeLookup
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoaderRegistry
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS

object ClientSetup {

    fun init() {
        MOD_BUS.addListener(::onSetup)
        MOD_BUS.addListener(::onModelRegistryEvent)
    }

    private fun onSetup(event: FMLClientSetupEvent) {
        event.enqueueWork { RenderTypeLookup.setRenderLayer(Registration.signalBlock, RenderType.solid()) }
    }

    @Suppress("UnusedPrivateMember")
    private fun onModelRegistryEvent(event: ModelRegistryEvent) {
        ModelLoaderRegistry.registerLoader(SignalModelLoader.ID, SignalModelLoader)
    }
}
