package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.controller.SignalControllerEntity;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.SignalPattern;

public abstract class BaseAdapterEntity extends BlockEntity {
	
	public BaseAdapterEntity(BlockEntityType<? extends BaseAdapterEntity> tileEntityType, @Nonnull BlockPos pos, @Nonnull BlockState state) {
		super(tileEntityType, pos, state);
	}
	
	@Nullable
	public BlockPos getControllerBlockPos() {
		assert level != null;
		BlockPos blockPos = null;
		for (int i = 0; level.getBlockState(worldPosition.above(i)).getBlock() instanceof BaseAdapterBlock; i++) {
			if (level.getBlockState(worldPosition.above(i + 1)).getBlock() == SignalControllerRegistration.SIGNAL_CONTROLLER_BLOCK.get()) {
				blockPos = worldPosition.above(i + 1);
				break;
			}
		}
		return blockPos;
	}
	
	public void setPattern(SignalPattern pattern) {
		BlockPos blockPos = getControllerBlockPos();
		if (blockPos != null) {
			assert level != null;
			final BlockEntity te = level.getBlockEntity(blockPos);
			if (te instanceof SignalControllerEntity) {
				((SignalControllerEntity) te).setDefaultPattern(pattern);
			}
		}
	}
	
	public abstract void update();
	
}
