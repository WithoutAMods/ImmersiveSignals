package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.BasePredicate;

public class PredicateAdapterContainer<T extends BasePredicate<T>> extends Container {

	public PredicateAdapterContainer(int id, World world, BlockPos pos) {
		super(SignalControllerRegistration.REDSTONE_ADAPTER_CONTAINER.get(), id);
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;
	}

}
