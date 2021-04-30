package withoutaname.mods.immersivesignals.modules.signal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer.Builder;

public class SignalFoundationBlock extends BaseSignalBlock {
	
	public static final String REGISTRY_NAME = "signal_foundation";
	
	public SignalFoundationBlock() {
		super();
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(ROTATION, 0));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(ROTATION);
	}
	
}
