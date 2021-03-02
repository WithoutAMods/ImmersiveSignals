package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.controller.SignalControllerTile;
import withoutaname.mods.immersivesignals.tools.SignalPattern;

public class BaseAdapterTile extends TileEntity {

	public BaseAdapterTile(TileEntityType<? extends BaseAdapterTile> tileEntityType) {
		super(tileEntityType);
	}

	@Nullable
	public BlockPos getControllerBlockPos() {
		assert world != null;
		BlockPos blockPos = null;
		for (int i = 0; world.getBlockState(pos.up(i)).getBlock() instanceof BaseAdapterBlock; i++) {
			if (world.getBlockState(pos.up(i + 1)).getBlock() == SignalControllerRegistration.SIGNAL_CONTROLLER_BLOCK.get()) {
				blockPos = pos.up(i +1);
				break;
			}
		}
		return blockPos;
	}

	public void setPattern(SignalPattern pattern) {
		BlockPos blockPos = getControllerBlockPos();
		if (blockPos != null) {
			final TileEntity te = world.getTileEntity(blockPos);
			if (te instanceof SignalControllerTile) {
				((SignalControllerTile) te).setDefaultPattern(pattern);
			}
		}
	}

}
