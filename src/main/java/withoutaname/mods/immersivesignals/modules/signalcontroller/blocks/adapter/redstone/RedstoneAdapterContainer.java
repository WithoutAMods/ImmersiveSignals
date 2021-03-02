package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.redstone;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;

public class RedstoneAdapterContainer extends Container {

	public RedstoneAdapterContainer(int id, World world, BlockPos pos) {
		super(SignalControllerRegistration.REDSTONE_ADAPTER_CONTAINER.get(), id);
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;
	}

}
