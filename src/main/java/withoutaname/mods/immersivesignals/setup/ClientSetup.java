package withoutaname.mods.immersivesignals.setup;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import withoutaname.mods.immersivesignals.ImmersiveSignals;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.PredicateAdapterScreen;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.controller.SignalControllerScreen;

@Mod.EventBusSubscriber(modid = ImmersiveSignals.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	public static void init(final FMLClientSetupEvent event) {
		ScreenManager.register(SignalControllerRegistration.SIGNAL_CONTROLLER_CONTAINER.get(), SignalControllerScreen::new);
		
		ScreenManager.register(SignalControllerRegistration.REDSTONE_ADAPTER_CONTAINER.get(), PredicateAdapterScreen::new);
	}
	
}