package withoutaname.mods.immersivesignals.setup;

import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import withoutaname.mods.immersivesignals.ImmersiveSignals;
import withoutaname.mods.immersivesignals.modules.signal.SignalRegistration;

public class ModSetup {
	
	public static final ItemGroup defaultItemGroup = new ItemGroup("immersivesignals") {
		
		@Override
		public ItemStack createIcon() {
			return new ItemStack(SignalRegistration.SIGNAL_ITEM.get());
		}
	};
	
	public void init() {
		ImmersiveSignals.proxy.init();
	}
	
	public static Properties getStandardItemProperties() {
		return new Item.Properties().group(defaultItemGroup);
	}
	
}
