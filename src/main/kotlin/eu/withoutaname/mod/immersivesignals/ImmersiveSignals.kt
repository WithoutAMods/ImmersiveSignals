package eu.withoutaname.mod.immersivesignals

import eu.withoutaname.mod.immersivesignals.datagen.DataGenerators
import eu.withoutaname.mod.immersivesignals.setup.ClientSetup
import eu.withoutaname.mod.immersivesignals.setup.Registration
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

val MOD_BUS: IEventBus
    inline get() = FMLJavaModLoadingContext.get().modEventBus


@Mod(ImmersiveSignals.ID)
class ImmersiveSignals {

    companion object {
        const val ID = "immersivesignals"

        @JvmField
        val logger: Logger = LogManager.getLogger()
    }


    init {
        Registration.init()
        ClientSetup.init()
        DataGenerators.init()
    }
}
