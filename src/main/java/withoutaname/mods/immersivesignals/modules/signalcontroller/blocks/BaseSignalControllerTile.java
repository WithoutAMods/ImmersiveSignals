package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;

public class BaseSignalControllerTile extends TileEntity {

	public BaseSignalControllerTile(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public static void registerTiles(RegistryEvent.Register<TileEntityType<?>> event) {
		event.getRegistry().register(TileEntityType.Builder.create(() -> new RedstoneSignalControllerTile(null), SignalControllerRegistration.REDSTONE_SIGNAL_CONTROLLER.get())
				.build(null).setRegistryName("redstonesignalcontroller"));
	}
}
