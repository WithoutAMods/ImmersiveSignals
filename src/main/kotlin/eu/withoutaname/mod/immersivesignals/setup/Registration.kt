package eu.withoutaname.mod.immersivesignals.setup

import eu.withoutaname.mod.immersivesignals.ImmersiveSignals
import eu.withoutaname.mod.immersivesignals.MOD_BUS
import eu.withoutaname.mod.immersivesignals.signal.block.SignalBlock
import eu.withoutaname.mod.immersivesignals.signal.block.SignalEntity
import eu.withoutaname.mod.immersivesignals.signal.item.SignalItem
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.IForgeRegistryEntry
import kotlin.reflect.KProperty

object Registration {

    private val blocks = DeferredRegister.create(ForgeRegistries.BLOCKS, ImmersiveSignals.ID)
    private val tileEntities = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ImmersiveSignals.ID)
    private val items = DeferredRegister.create(ForgeRegistries.ITEMS, ImmersiveSignals.ID)

    fun init() {
        blocks.register(MOD_BUS)
        tileEntities.register(MOD_BUS)
        items.register(MOD_BUS)
    }

    val signalBlock: SignalBlock by blocks.register("signal") { SignalBlock }
    val signalEntityType: TileEntityType<SignalEntity> by this.tileEntities.register("signal") {
        TileEntityType.Builder.of(
            { SignalEntity() },
            signalBlock
        ).build(null)
    }
    val signalItem: SignalItem by items.register("signal") { SignalItem }
}

private operator fun <T : IForgeRegistryEntry<in T>> RegistryObject<T>.getValue(
    ref: Any?,
    property: KProperty<*>
): T = get()
