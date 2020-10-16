package withoutaname.mods.immersivesignals.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import withoutaname.mods.immersivesignals.ImmersiveSignals;
import withoutaname.mods.immersivesignals.modules.signal.SignalRegistration;

public class Language extends LanguageProvider {
	
	private final String locale;

	public Language(DataGenerator gen, String locale) {
		super(gen, ImmersiveSignals.MODID, locale);
		this.locale = locale;
	}

	@Override
	protected void addTranslations() {
		add(SignalRegistration.SIGNAL_FOUNDATION.get().getTranslationKey() + ".name", "Signal", "Signal");
		add(SignalRegistration.SIGNAL_MAIN.get().getTranslationKey() + ".name", "Signal", "Signal");
		add(SignalRegistration.SIGNAL_POST.get().getTranslationKey() + ".name", "Signal", "Signal");
		add(SignalRegistration.SIGNAL_ZS3.get().getTranslationKey() + ".name", "Signal", "Signal");
		add(SignalRegistration.SIGNAL_ZS3V.get().getTranslationKey() + ".name", "Signal", "Signal");

		add(SignalRegistration.SIGNAL_ITEM.get(), "Signal", "Signal");

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
		add(key.getTranslationKey(), de_de, en_us);
	}

}
