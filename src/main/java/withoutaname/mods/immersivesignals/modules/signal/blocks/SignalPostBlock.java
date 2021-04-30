package withoutaname.mods.immersivesignals.modules.signal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer.Builder;

public class SignalPostBlock extends BaseSignalBlock {
	
	public static final String REGISTRY_NAME = "signal_post";
	
	public SignalPostBlock() {
		super();
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(ROTATION, 0)
				.setValue(SIGNAL_MASTSIGN, SignalMastsignMode.MODE_NONE));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(ROTATION, SIGNAL_MASTSIGN);
	}
	
}
