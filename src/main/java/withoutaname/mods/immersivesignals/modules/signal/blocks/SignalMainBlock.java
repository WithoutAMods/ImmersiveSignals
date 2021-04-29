package withoutaname.mods.immersivesignals.modules.signal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class SignalMainBlock extends BaseSignalBlock {
	
	public static final String REGISTRY_NAME = "signal_main";
	
	public SignalMainBlock() {
		super();
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(ROTATION, 0)
				.setValue(SIGNAL_MAIN_PATTERN, SignalMainPattern.NONE)
				.setValue(SIGNAL_WHITE0, false)
				.setValue(SIGNAL_WHITE1, false)
				.setValue(SIGNAL_WHITE2, false)
				.setValue(SIGNAL_ZS7, false));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(ROTATION, SIGNAL_MAIN_PATTERN, SIGNAL_WHITE0, SIGNAL_WHITE1, SIGNAL_WHITE2, SIGNAL_ZS7);
	}
	
	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return state.getValue(SIGNAL_MAIN_PATTERN) != SignalMainPattern.NONE || state.getValue(SIGNAL_WHITE0)
				|| state.getValue(SIGNAL_WHITE1) || state.getValue(SIGNAL_WHITE2) || state.getValue(SIGNAL_ZS7) ? 10 : 0;
	}
	
}
