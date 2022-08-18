package eu.withoutaname.mod.immersivesignals.datagen

import net.minecraftforge.fml.event.lifecycle.GatherDataEvent

object DataGenerators {

    @JvmStatic
    fun gatherData(event: GatherDataEvent) {
        val generator = event.generator
        if (event.includeClient()) {
            generator.addProvider(BlockStates(generator, event.existingFileHelper))
            generator.addProvider(Language(generator))
        }
    }
}
