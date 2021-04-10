package withoutaname.mods.immersivesignals.modules.signal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;

import withoutaname.mods.immersivesignals.modules.signal.blocks.BaseSignalBlock.SignalMainPattern;

public class SignalMainBlock extends BaseSignalBlock {

	public static final String REGISTRY_NAME = "signal_main";

	public SignalMainBlock() {
		super();
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
				.setValue(SIGNAL_MAIN_PATTERN, SignalMainPattern.NONE)
				.setValue(SIGNAL_WHITE0, false)
				.setValue(SIGNAL_WHITE1, false)
				.setValue(SIGNAL_WHITE2, false)
				.setValue(SIGNAL_ZS7, false));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.HORIZONTAL_FACING, SIGNAL_MAIN_PATTERN, SIGNAL_WHITE0, SIGNAL_WHITE1, SIGNAL_WHITE2, SIGNAL_ZS7);
	}

}
