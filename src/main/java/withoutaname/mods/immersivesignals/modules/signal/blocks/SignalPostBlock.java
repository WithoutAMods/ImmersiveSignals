package withoutaname.mods.immersivesignals.modules.signal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;

public class SignalPostBlock extends BaseSignalBlock {

	public static final String REGISTRY_NAME = "signalpost";

	public SignalPostBlock() {
		super();
		this.setDefaultState(this.stateContainer.getBaseState()
				.with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
				.with(SIGNAL_MASTSIGN, SignalMastsignMode.MODE_NONE));
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.HORIZONTAL_FACING, SIGNAL_MASTSIGN);
	}

}
