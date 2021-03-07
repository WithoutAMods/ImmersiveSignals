package withoutaname.mods.immersivesignals.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import withoutaname.mods.immersivesignals.ImmersiveSignals;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;

public class BlockStates extends BlockStateProvider {

	public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, ImmersiveSignals.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		BlockModelBuilder signalControllerModel = models().cubeBottomTop("block/signal_controller",
				modLoc("block/signal_controller_side"),
				modLoc("block/signal_controller_bottom"),
				modLoc("block/signal_controller_top"));
		simpleBlock(SignalControllerRegistration.SIGNAL_CONTROLLER_BLOCK.get(), signalControllerModel);
		simpleBlockItem(SignalControllerRegistration.SIGNAL_CONTROLLER_BLOCK.get(), signalControllerModel);
		BlockModelBuilder redstoneAdapterModel = models().cubeBottomTop("block/redstone_adapter",
				modLoc("block/redstone_adapter_side"),
				modLoc("block/signal_controller_bottom"),
				modLoc("block/redstone_adapter_top"));
		simpleBlock(SignalControllerRegistration.REDSTONE_ADAPTER_BLOCK.get(), redstoneAdapterModel);
		simpleBlockItem(SignalControllerRegistration.REDSTONE_ADAPTER_BLOCK.get(), redstoneAdapterModel);
	}

}
