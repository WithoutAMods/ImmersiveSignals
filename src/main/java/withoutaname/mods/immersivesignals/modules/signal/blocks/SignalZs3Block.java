package withoutaname.mods.immersivesignals.modules.signal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShapes;

public class SignalZs3Block extends BaseSignalBlock {

	public static final String REGISTRY_NAME = "signal_zs3";

	public SignalZs3Block() {
		super();
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
				.setValue(SIGNAL_NUMBER, 0));
		this.shape = VoxelShapes.box(.25, 0, .25, .75, .75, .75);
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.HORIZONTAL_FACING, SIGNAL_NUMBER);
	}

}
