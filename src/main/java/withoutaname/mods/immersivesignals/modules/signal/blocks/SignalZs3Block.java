package withoutaname.mods.immersivesignals.modules.signal.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.Shapes;

public class SignalZs3Block extends BaseSignalBlock {
	
	public static final String REGISTRY_NAME = "signal_zs3";
	
	public SignalZs3Block() {
		super();
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(ROTATION, 0)
				.setValue(SIGNAL_NUMBER, 0));
		this.shape = Shapes.box(.25, 0, .25, .75, .75, .75);
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
