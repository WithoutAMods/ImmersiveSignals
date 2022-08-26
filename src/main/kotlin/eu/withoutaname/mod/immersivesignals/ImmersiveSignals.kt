package eu.withoutaname.mod.immersivesignals

import eu.withoutaname.mod.immersivesignals.datagen.DataGenerators
import eu.withoutaname.mod.immersivesignals.setup.ClientSetup
import eu.withoutaname.mod.immersivesignals.setup.Registration
import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(ImmersiveSignals.ID)
object ImmersiveSignals {

    const val ID = "immersivesignals"

    val logger: Logger = LogManager.getLogger()

    init {
        Registration.init()
        ClientSetup.init()
        DataGenerators.init()
    }
}
