package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.redstone;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.PredicateAdapterContainer;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.RedstonePredicate;

public class RedstoneAdapterContainer extends PredicateAdapterContainer<RedstonePredicate> {
	
	public RedstoneAdapterContainer(int id, Level world, BlockPos pos, Player player) {
		super(SignalControllerRegistration.REDSTONE_ADAPTER_CONTAINER.get(), id, world, pos, player);
	}
	
}
