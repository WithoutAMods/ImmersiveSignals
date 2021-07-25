package withoutaname.mods.immersivesignals.modules.signal.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class SignalPostBlock extends BaseSignalBlock {
	
	public static final String REGISTRY_NAME = "signal_post";
	
	public SignalPostBlock() {
		super();
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(ROTATION, 0)
				.setValue(SIGNAL_MASTSIGN, SignalMastsignMode.MODE_NONE));
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(ROTATION, SIGNAL_MASTSIGN);
	}
	
}
