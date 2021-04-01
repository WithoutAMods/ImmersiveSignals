package withoutaname.mods.immersivesignals.modules.signalcontroller.data;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import withoutaname.mods.immersivesignals.ImmersiveSignals;

public class SectionsEventHandler {

	public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<World> event) {
		SectionsProvider provider = new SectionsProvider();
		event.addCapability(new ResourceLocation(ImmersiveSignals.MODID, "sections"), provider);
		event.addListener(provider::invalidate);
	}
}
