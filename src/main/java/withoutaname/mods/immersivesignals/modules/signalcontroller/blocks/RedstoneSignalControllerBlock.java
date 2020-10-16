package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
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
		return new RedstoneSignalControllerTile((World) world);
	}

	@Override
	public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
		LOGGER.info("tick");
	}

	@Override
	public int tickRate(IWorldReader worldIn) {
		return 2;
	}

	@Override
	public void observedNeighborChange(BlockState observerState, World world, BlockPos observerPos, Block changedBlock, BlockPos changedBlockPos) {
		LOGGER.info("observedNeighborChange");
		if(!world.isRemote()) {
			TileEntity te = world.getTileEntity(observerPos);
			if(te instanceof RedstoneSignalControllerTile) {
				((RedstoneSignalControllerTile) te).updateSignalPattern();
			}
		}
	}

	@Override
	public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {
		LOGGER.info("onNeighborChange");
		if(!world.isRemote()) {
			TileEntity te = world.getTileEntity(pos);
			if(te instanceof RedstoneSignalControllerTile) {
				((RedstoneSignalControllerTile) te).updateSignalPattern();
			}
		}
	}

	@Override
	public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, @Nullable Direction side) {
		return true;
	}

	@Override
	public boolean shouldCheckWeakPower(BlockState state, IWorldReader world, BlockPos pos, Direction side) {
		return true;
	}
}
