package withoutaname.mods.immersivesignals.modules.signal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class SignalZs3Block extends BaseSignalBlock {
	
	public static final String REGISTRY_NAME = "signal_zs3";
	
	public SignalZs3Block() {
		super();
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(ROTATION, 0)
				.setValue(SIGNAL_NUMBER, 0));
		this.shape = VoxelShapes.box(.25, 0, .25, .75, .75, .75);
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(ROTATION, SIGNAL_NUMBER);
	}
	
	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return state.getValue(SIGNAL_NUMBER) == 0 ? 0 : 10;
	}
	
}
