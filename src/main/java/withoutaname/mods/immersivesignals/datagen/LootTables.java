package withoutaname.mods.immersivesignals.datagen;

import net.minecraft.data.DataGenerator;
import withoutaname.mods.immersivesignals.modules.signal.SignalRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;
import withoutaname.mods.withoutalib.datagen.BaseLootTableProvider;

public class LootTables extends BaseLootTableProvider {

	public LootTables(DataGenerator dataGeneratorIn) {
		super(dataGeneratorIn);
	}

	@Override
	protected void addTables() {
		createStandardTable(SignalRegistration.SIGNAL_FOUNDATION.get(), SignalRegistration.SIGNAL_ITEM.get());
		createStandardTable(SignalRegistration.SIGNAL_MAIN.get(), SignalRegistration.SIGNAL_ITEM.get());
		createStandardTable(SignalRegistration.SIGNAL_POST.get(), SignalRegistration.SIGNAL_ITEM.get());
		createStandardTable(SignalRegistration.SIGNAL_ZS3.get(), SignalRegistration.SIGNAL_ITEM.get());
		createStandardTable(SignalRegistration.SIGNAL_ZS3V.get(), SignalRegistration.SIGNAL_ITEM.get());

		createStandardTable(SignalControllerRegistration.SIGNAL_CONTROLLER_ITEM.get());
		createStandardTable(SignalControllerRegistration.REDSTONE_ADAPTER_ITEM.get());
	}
	
}
