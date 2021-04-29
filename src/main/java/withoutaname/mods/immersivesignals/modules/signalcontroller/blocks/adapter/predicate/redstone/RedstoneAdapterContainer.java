package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.redstone;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.PredicateAdapterContainer;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.RedstonePredicate;

public class RedstoneAdapterContainer extends PredicateAdapterContainer<RedstonePredicate> {
	
	public RedstoneAdapterContainer(int id, World world, BlockPos pos, PlayerEntity player) {
		super(SignalControllerRegistration.REDSTONE_ADAPTER_CONTAINER.get(), id, world, pos, player);
	}
	
}
