package withoutaname.mods.immersivesignals.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import withoutaname.mods.immersivesignals.ImmersiveSignals;
import withoutaname.mods.immersivesignals.modules.signal.SignalRegistration;
import withoutaname.mods.immersivesignals.modules.signal.blocks.BaseSignalBlock;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;

import javax.annotation.Nonnull;

public class BlockStates extends BlockStateProvider {
	
	public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, ImmersiveSignals.MODID, exFileHelper);
	}
	
	@Override
	protected void registerStatesAndModels() {
		registerSignalModels();
		registerSignalControllerModels();
	}
	
	private void registerSignalModels() {
		getVariantBuilder(SignalRegistration.SIGNAL_FOUNDATION.get())
				.forAllStates(blockState -> getConfiguredModel(blockState, "block/signal_foundation"));
		getVariantBuilder(SignalRegistration.SIGNAL_POST.get()).forAllStates(blockState -> {
			String name;
			switch (blockState.getValue(BaseSignalBlock.SIGNAL_MASTSIGN)) {
				default:
				case MODE_NONE:
					name = "block/signal_post";
					break;
				case MODE_WRW:
					name = "block/signal_post_mastsign_wrw";
					break;
				case MODE_BOTH:
					name = "block/signal_post_mastsign_both";
					break;
				case MODE_Y:
					name = "block/signal_post_mastsign_y";
					break;
			}
			return getConfiguredModel(blockState, name);
		});
		getVariantBuilder(SignalRegistration.SIGNAL_ZS3V.get())
				.forAllStates(blockState -> getConfiguredModel(blockState, "block/signal_zs3v_"
						+ blockState.getValue(BaseSignalBlock.SIGNAL_NUMBER)));
		getVariantBuilder(SignalRegistration.SIGNAL_ZS3.get())
				.forAllStates(blockState -> getConfiguredModel(blockState, "block/signal_zs3_"
						+ blockState.getValue(BaseSignalBlock.SIGNAL_NUMBER)));
		MultiPartBlockStateBuilder mainBuilder = getMultipartBuilder(SignalRegistration.SIGNAL_MAIN.get());
		for (int i : BaseSignalBlock.ROTATION.getPossibleValues()) {
			getPartBuilder(mainBuilder, i, "block/signal_main").end();
			for (BaseSignalBlock.SignalMainPattern pattern : BaseSignalBlock.SignalMainPattern.values()) {
				if (pattern != BaseSignalBlock.SignalMainPattern.NONE) {
					getPartBuilder(mainBuilder, i, "block/signal_main_" + pattern.toString())
							.condition(BaseSignalBlock.SIGNAL_MAIN_PATTERN, pattern).end();
				}
			}
			getPartBuilder(mainBuilder, i, "block/signal_main_white0")
					.condition(BaseSignalBlock.SIGNAL_WHITE0, true).end();
			getPartBuilder(mainBuilder, i, "block/signal_main_white1")
					.condition(BaseSignalBlock.SIGNAL_WHITE1, true).end();
			getPartBuilder(mainBuilder, i, "block/signal_main_white2")
					.condition(BaseSignalBlock.SIGNAL_WHITE2, true).end();
			getPartBuilder(mainBuilder, i, "block/signal_main_zs7")
					.condition(BaseSignalBlock.SIGNAL_ZS7, true).end();
		}
	}
	
	@Nonnull
	private MultiPartBlockStateBuilder.PartBuilder getPartBuilder(@Nonnull MultiPartBlockStateBuilder builder, int i, String name) {
		return builder.part()
				.modelFile(models().getExistingFile(modLoc(name + (i % 4 == 0 ? "" : "_" + i % 4))))
				.rotationY(i / 4 * 90)
				.addModel()
				.condition(BaseSignalBlock.ROTATION, i);
	}
	
	@Nonnull
	private ConfiguredModel[] getConfiguredModel(BlockState blockState, String name) {
		ModelFile model = models().getExistingFile(modLoc(name + getModelVersion(blockState)));
		return new ConfiguredModel[]{new ConfiguredModel(model, 0, blockState.getValue(BaseSignalBlock.ROTATION) / 4 * 90, false)};
	}
	
	@Nonnull
	private String getModelVersion(@Nonnull BlockState blockState) {
		int i = blockState.getValue(BaseSignalBlock.ROTATION) % 4;
		return i == 0 ? "" : ("_" + i);
	}
	
	private void registerSignalControllerModels() {
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
