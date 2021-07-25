package withoutaname.mods.immersivesignals.setup;

import javax.annotation.Nonnull;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import withoutaname.mods.immersivesignals.modules.signal.SignalRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.SignalControllerNetworking;

public class ModSetup {
	
	public static final CreativeModeTab DEFAULT_CREATIVE_TAB = new CreativeModeTab("Immersive Signals") {
		
		@Nonnull
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(SignalRegistration.SIGNAL_ITEM.get());
		}
		
	};
	
	public static void init(FMLCommonSetupEvent event) {
		SignalControllerNetworking.registerMessages();
	}
	
	public static final Item.Properties DEFAULT_ITEM_PROPERTIES = new Item.Properties().tab(DEFAULT_CREATIVE_TAB);
	
}