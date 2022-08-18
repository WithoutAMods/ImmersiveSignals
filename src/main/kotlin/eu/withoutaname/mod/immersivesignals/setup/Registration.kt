package eu.withoutaname.mod.immersivesignals.setup

import eu.withoutaname.mod.immersivesignals.ImmersiveSignals
import eu.withoutaname.mod.immersivesignals.signal.block.SignalBlock
import eu.withoutaname.mod.immersivesignals.signal.block.SignalEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object Registration {

    private val blocks = KDeferredRegister(ForgeRegistries.BLOCKS, ImmersiveSignals.ID)
    private val tileEntities = KDeferredRegister(ForgeRegistries.TILE_ENTITIES, ImmersiveSignals.ID)

    fun init() {}

    val signalBlock by blocks.registerObject("signal") { SignalBlock }
    val signalEntityType by tileEntities.registerObject("signal") {
        TileEntityType.Builder.of(
            { SignalEntity() },
            signalBlock
        ).build(null)
    }
}
