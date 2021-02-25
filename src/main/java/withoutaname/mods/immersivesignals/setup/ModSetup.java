package withoutaname.mods.immersivesignals.setup;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import withoutaname.mods.immersivesignals.modules.signal.SignalRegistration;

public class ModSetup {

	public static final ItemGroup defaultItemGroup = new ItemGroup("immersivesignals") {

		@Override
		public ItemStack createIcon() {
			return new ItemStack(SignalRegistration.SIGNAL_ITEM.get());
		}

	};

	public static void init(FMLCommonSetupEvent event) {
	}

	public static final Item.Properties defaultItemProperties = new Item.Properties().group(defaultItemGroup);

}