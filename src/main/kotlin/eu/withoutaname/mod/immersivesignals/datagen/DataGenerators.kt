package eu.withoutaname.mod.immersivesignals.datagen

import eu.withoutaname.mod.immersivesignals.MOD_BUS
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent

object DataGenerators {

    fun init() {
        MOD_BUS.addListener(::gatherData)
    }

    private fun gatherData(event: GatherDataEvent) {
        val generator = event.generator
        if (event.includeClient()) {
            generator.addProvider(BlockStates(generator, event.existingFileHelper))
            generator.addProvider(Language(generator))
        }
    }
}
