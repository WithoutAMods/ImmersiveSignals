package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RedstoneSignalControllerBlock extends BaseSignalControllerBlock {

	public static final String REGISTRY_NAME = "redstone_signal_controller";

	public RedstoneSignalControllerBlock() {
		super();
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new RedstoneSignalControllerTile((World) world, state);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		if(!world.isRemote()) {
			TileEntity te = world.getTileEntity(currentPos);
			if(te instanceof RedstoneSignalControllerTile) {
				((RedstoneSignalControllerTile) te).updateSignalPattern();
			}
		}
		return super.updatePostPlacement(stateIn, facing, facingState, world, currentPos, facingPos);
	}

	@Override
	public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, @Nullable Direction side) {
		return true;
	}

}
