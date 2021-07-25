package withoutaname.mods.immersivesignals.modules.signal.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class SignalZs3vBlock extends BaseSignalBlock {
	
	public static final String REGISTRY_NAME = "signal_zs3v";
	
	public SignalZs3vBlock() {
		super();
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(ROTATION, 0)
				.setValue(SIGNAL_NUMBER, 0));
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(ROTATION, SIGNAL_NUMBER);
	}
	
	@Override
	public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
		return state.getValue(SIGNAL_NUMBER) == 0 ? 0 : 10;
	}
	
}
