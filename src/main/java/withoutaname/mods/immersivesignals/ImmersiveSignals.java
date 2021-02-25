package withoutaname.mods.immersivesignals;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import withoutaname.mods.immersivesignals.modules.signal.SignalRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;
import withoutaname.mods.immersivesignals.setup.ModSetup;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ImmersiveSignals.MODID)
public class ImmersiveSignals {

	public static final String MODID = "immersivesignals";

	private static final Logger LOGGER = LogManager.getLogger();

	public ImmersiveSignals() {
		SignalRegistration.init();
		SignalControllerRegistration.init();

		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ModSetup::init);
	}

}
