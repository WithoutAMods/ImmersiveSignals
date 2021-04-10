package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.controller.SignalControllerTile;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.SignalPattern;

import javax.annotation.Nullable;

public abstract class BaseAdapterTile extends TileEntity {

	public BaseAdapterTile(TileEntityType<? extends BaseAdapterTile> tileEntityType) {
		super(tileEntityType);
	}

	@Nullable
	public BlockPos getControllerBlockPos() {
		assert level != null;
		BlockPos blockPos = null;
		for (int i = 0; level.getBlockState(worldPosition.above(i)).getBlock() instanceof BaseAdapterBlock; i++) {
			if (level.getBlockState(worldPosition.above(i + 1)).getBlock() == SignalControllerRegistration.SIGNAL_CONTROLLER_BLOCK.get()) {
				blockPos = worldPosition.above(i +1);
				break;
			}
		}
		return blockPos;
	}

	public void setPattern(SignalPattern pattern) {
		BlockPos blockPos = getControllerBlockPos();
		if (blockPos != null) {
			final TileEntity te = level.getBlockEntity(blockPos);
			if (te instanceof SignalControllerTile) {
				((SignalControllerTile) te).setDefaultPattern(pattern);
			}
		}
	}

	public abstract void update();

}
