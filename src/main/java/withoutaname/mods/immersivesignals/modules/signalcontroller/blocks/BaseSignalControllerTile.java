package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.World;

public class BaseSignalControllerTile extends TileEntity {

	public BaseSignalControllerTile(TileEntityType<? extends BaseSignalControllerTile> tileEntityType, World world, BlockState state) {
		super(tileEntityType);
		setWorld(world);
	}
}
