package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.redstone;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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
	public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
		return new PredicateAdapterTile<>(SignalControllerRegistration.REDSTONE_ADAPTER_ENTITY.get(), new MultiPredicate<>(new RedstonePredicate()), pos, state);
	}
	
	@Override
	public boolean isSignalSource(@Nonnull BlockState pState) {
		return true;
	}
	
	@Override
	protected PredicateAdapterContainer<RedstonePredicate> createContainer(int id, Level level, @Nonnull BlockPos pos, Player player) {
		return new RedstoneAdapterContainer(id, level, pos, player);
	}
	
	@Override
	public void neighborChanged(@Nonnull BlockState state, @Nonnull Level pLevel, @Nonnull BlockPos pos, @Nonnull Block blockIn, @Nonnull BlockPos fromPos, boolean isMoving) {
		update(pLevel, pos);
	}
	
}
