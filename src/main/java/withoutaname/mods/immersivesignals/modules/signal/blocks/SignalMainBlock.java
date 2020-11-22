package withoutaname.mods.immersivesignals.modules.signal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;

public class SignalMainBlock extends BaseSignalBlock {

	public static final String REGISTRY_NAME = "signal_main";

	public SignalMainBlock() {
		super();
		this.setDefaultState(this.stateContainer.getBaseState()
				.with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
				.with(SIGNAL_MAIN_PATTERN, SignalMainPattern.MODE_NONE)
				.with(SIGNAL_WHITE0, false)
				.with(SIGNAL_WHITE1, false)
				.with(SIGNAL_WHITE2, false)
				.with(SIGNAL_ZS7, false));
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.HORIZONTAL_FACING, SIGNAL_MAIN_PATTERN, SIGNAL_WHITE0, SIGNAL_WHITE1, SIGNAL_WHITE2, SIGNAL_ZS7);
	}

}
