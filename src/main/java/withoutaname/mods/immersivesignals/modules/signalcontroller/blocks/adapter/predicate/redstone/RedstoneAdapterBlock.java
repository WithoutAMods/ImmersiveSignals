package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.redstone;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.PredicateAdapterBlock;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.PredicateAdapterContainer;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.PredicateAdapterTile;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.MultiPredicate;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.RedstonePredicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RedstoneAdapterBlock extends PredicateAdapterBlock<RedstonePredicate> {

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new PredicateAdapterTile<>(SignalControllerRegistration.REDSTONE_ADAPTER_TILE.get(), new MultiPredicate<>(new RedstonePredicate()));
	}

	@Override
	public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, @Nullable Direction side) {
		return true;
	}

	@Override
	protected PredicateAdapterContainer<RedstonePredicate> createContainer(int id, World world, @Nonnull BlockPos pos, PlayerEntity player) {
		return new RedstoneAdapterContainer(id, world, pos, player);
	}

	@Override
	public void neighborChanged(@Nonnull BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Block blockIn, @Nonnull BlockPos fromPos, boolean isMoving) {
		update(worldIn, pos);
	}

}
