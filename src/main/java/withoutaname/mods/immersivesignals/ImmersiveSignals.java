package withoutaname.mods.immersivesignals;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import withoutaname.mods.immersivesignals.modules.signal.SignalRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;
import withoutaname.mods.immersivesignals.setup.ClientProxy;
import withoutaname.mods.immersivesignals.setup.IProxy;
import withoutaname.mods.immersivesignals.setup.ModSetup;
import withoutaname.mods.immersivesignals.setup.ServerProxy;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ImmersiveSignals.MODID)
public class ImmersiveSignals {

	public static final String MODID = "immersivesignals";

	public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

	public static ModSetup setup = new ModSetup();
	
	//private static final Logger LOGGER = LogManager.getLogger();

	public ImmersiveSignals() {
		SignalRegistration.init();
		SignalControllerRegistration.init();

		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

		// Register ourselves for server and other game events we are interested in
		//MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		setup.init();
	}

	// You can use EventBusSubscriber to automatically subscribe events on the
	// contained class (this is subscribing to the MOD
	// Event bus for receiving Registry Events)
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {

		@SubscribeEvent
		public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
			//BaseSignalBlock.registerBlocks(event);
			//BaseSignalControllerBlock.registerBlocks(event);
		}

		@SubscribeEvent
		public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event){
			//BaseSignalControllerTile.registerTiles(event);
		}

		@SubscribeEvent
		public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
			//event.getRegistry().register(new SignalItem());
			//BaseSignalControllerBlock.registerItems(event);
		}
		
	}
	
}
