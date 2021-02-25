package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import org.jetbrains.annotations.Nullable;

public class RedstoneAdapterBlock extends BaseAdapterBlock {

	public static final String REGISTRY_NAME = "redstone_signal_controller";

	public RedstoneAdapterBlock() {
		super();
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new RedstoneAdapterTile();
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		if(!world.isRemote()) {
			TileEntity te = world.getTileEntity(currentPos);
			if(te instanceof RedstoneAdapterTile) {
				((RedstoneAdapterTile) te).updateSignalPattern();
			}
		}
		return super.updatePostPlacement(stateIn, facing, facingState, world, currentPos, facingPos);
	}

	@Override
	public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, @Nullable Direction side) {
		return true;
	}

}
