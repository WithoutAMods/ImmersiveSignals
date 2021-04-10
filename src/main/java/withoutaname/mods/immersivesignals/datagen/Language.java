package withoutaname.mods.immersivesignals.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import withoutaname.mods.immersivesignals.ImmersiveSignals;
import withoutaname.mods.immersivesignals.modules.signal.SignalRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;

public class Language extends LanguageProvider {

	public static final String SIGNAL_DE_DE = "Signal";
	public static final String SIGNAL_EN_US = "Signal";
	private final String locale;

	public Language(DataGenerator gen, String locale) {
		super(gen, ImmersiveSignals.MODID, locale);
		this.locale = locale;
	}

	@Override
	protected void addTranslations() {
		add(SignalRegistration.SIGNAL_FOUNDATION.get().getDescriptionId(), SIGNAL_DE_DE, SIGNAL_EN_US);
		add(SignalRegistration.SIGNAL_MAIN.get().getDescriptionId(), SIGNAL_DE_DE, SIGNAL_EN_US);
		add(SignalRegistration.SIGNAL_POST.get().getDescriptionId(), SIGNAL_DE_DE, SIGNAL_EN_US);
		add(SignalRegistration.SIGNAL_ZS3.get().getDescriptionId(), SIGNAL_DE_DE, SIGNAL_EN_US);
		add(SignalRegistration.SIGNAL_ZS3V.get().getDescriptionId(), SIGNAL_DE_DE, SIGNAL_EN_US);
		add(SignalRegistration.SIGNAL_ITEM.get(), SIGNAL_DE_DE, SIGNAL_EN_US);

		add(SignalControllerRegistration.SIGNAL_CONTROLLER_ITEM.get(), "Signalsteuerung", "Signal Controller");
		add(SignalControllerRegistration.REDSTONE_ADAPTER_ITEM.get(), "Redstoneadapter", "Redstone Adapter");

		add("itemGroup.immersivesignals", "Immersive Signals", "Immersive Signals");
	}

	private void add(String key, String de_de, String en_us) {
		switch(locale) {
			case "de_de":
				add(key, de_de);
				break;
			case "en_us":
				add(key, en_us);
				break;
		}
	}

	private void add(Item key, String de_de, String en_us) {
		add(key.getDescriptionId(), de_de, en_us);
	}

}
