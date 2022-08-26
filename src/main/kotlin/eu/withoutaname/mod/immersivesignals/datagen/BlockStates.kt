package eu.withoutaname.mod.immersivesignals.datagen

import eu.withoutaname.mod.immersivesignals.ImmersiveSignals
import eu.withoutaname.mod.immersivesignals.setup.Registration
import eu.withoutaname.mod.immersivesignals.signal.block.client.SignalModelLoader
import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.BlockModelBuilder
import net.minecraftforge.client.model.generators.BlockStateProvider
import net.minecraftforge.client.model.generators.CustomLoaderBuilder
import net.minecraftforge.common.data.ExistingFileHelper

class BlockStates(generator: DataGenerator, exFileHelper: ExistingFileHelper) :
    BlockStateProvider(generator, ImmersiveSignals.ID, exFileHelper) {

    override fun registerStatesAndModels() {
        simpleBlock(
            Registration.signalBlock,
            models().getBuilder("signal").customLoader { builder, existingFileHelper ->
                object : CustomLoaderBuilder<BlockModelBuilder>(SignalModelLoader.ID, builder, existingFileHelper) {}
            }.end()
        )
    }
}
