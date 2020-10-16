package withoutaname.mods.immersivesignals.modules.signal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;

public class SignalZs3vBlock extends BaseSignalBlock {

	public static final String REGISTRY_NAME = "signalzs3v";

	public SignalZs3vBlock() {
		super();
		this.setDefaultState(this.stateContainer.getBaseState()
				.with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
				.with(SIGNAL_NUMBER, 0));
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.HORIZONTAL_FACING, SIGNAL_NUMBER);
	}

}
