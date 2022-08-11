package eu.withoutaname.mod.immersivesignals

import eu.withoutaname.mod.immersivesignals.datagen.DataGenerators
import eu.withoutaname.mod.immersivesignals.setup.ClientSetup
import eu.withoutaname.mod.immersivesignals.setup.ModSetup
import eu.withoutaname.mod.immersivesignals.setup.Registration
import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(ImmersiveSignals.ID)
object ImmersiveSignals {

    const val ID = "immersivesignals"

    val logger: Logger = LogManager.getLogger()

    init {
        Registration.init()

        MOD_BUS.addListener(DataGenerators::gatherData)
        MOD_BUS.addListener(ModSetup::init)
        MOD_BUS.addListener(ClientSetup::init)
    }
}