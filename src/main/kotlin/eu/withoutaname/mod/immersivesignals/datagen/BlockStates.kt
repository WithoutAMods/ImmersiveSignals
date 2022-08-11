package eu.withoutaname.mod.immersivesignals.datagen

import eu.withoutaname.mod.immersivesignals.ImmersiveSignals
import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.BlockStateProvider
import net.minecraftforge.common.data.ExistingFileHelper

class BlockStates(generator: DataGenerator, exFileHelper: ExistingFileHelper) :
    BlockStateProvider(generator, ImmersiveSignals.ID, exFileHelper) {

    override fun registerStatesAndModels() {}
}