package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.redstone;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.PredicateAdapterBlock;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.PredicateAdapterTile;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.RedstonePredicate;

public class RedstoneAdapterBlock extends PredicateAdapterBlock<RedstonePredicate> {

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new PredicateAdapterTile<>(SignalControllerRegistration.REDSTONE_ADAPTER_TILE.get(), new RedstonePredicate());
	}

	@Override
	public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, @Nullable Direction side) {
		return true;
	}

	@Override
	public void neighborChanged(@NotNull BlockState state, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull Block blockIn, @NotNull BlockPos fromPos, boolean isMoving) {
		update(worldIn, pos);
	}

}
