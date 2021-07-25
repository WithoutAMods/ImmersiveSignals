package withoutaname.mods.immersivesignals.modules.signal.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class SignalFoundationBlock extends BaseSignalBlock {
	
	public static final String REGISTRY_NAME = "signal_foundation";
	
	public SignalFoundationBlock() {
		super();
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(ROTATION, 0));
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(ROTATION);
	}
	
}
