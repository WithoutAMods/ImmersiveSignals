package withoutaname.mods.immersivesignals.datagen;

import javax.annotation.Nonnull;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

import withoutaname.mods.immersivesignals.ImmersiveSignals;
import withoutaname.mods.immersivesignals.modules.signal.SignalRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;

public class Language extends LanguageProvider {
	
	public static final String SIGNAL_DE_DE = "Signal";
	public static final String SIGNAL_EN_US = "Signal";
	public static final String SCREEN = "screen." + ImmersiveSignals.MODID;
	private final String locale;
	
	public Language(DataGenerator gen, String locale) {
		super(gen, ImmersiveSignals.MODID, locale);
		this.locale = locale;
	}
	
	@Override
	protected void addTranslations() {
		addSignalTranslations();
		
		addSignalControllerTranslations();
		
		add("itemGroup.immersivesignals", "Immersive Signals", "Immersive Signals");
	}
	
	private void addSignalTranslations() {
		add(SignalRegistration.SIGNAL_FOUNDATION.get().getDescriptionId(), SIGNAL_DE_DE, SIGNAL_EN_US);
		add(SignalRegistration.SIGNAL_MAIN.get().getDescriptionId(), SIGNAL_DE_DE, SIGNAL_EN_US);
		add(SignalRegistration.SIGNAL_POST.get().getDescriptionId(), SIGNAL_DE_DE, SIGNAL_EN_US);
		add(SignalRegistration.SIGNAL_ZS3.get().getDescriptionId(), SIGNAL_DE_DE, SIGNAL_EN_US);
		add(SignalRegistration.SIGNAL_ZS3V.get().getDescriptionId(), SIGNAL_DE_DE, SIGNAL_EN_US);
		add(SignalRegistration.SIGNAL_ITEM.get(), SIGNAL_DE_DE, SIGNAL_EN_US);
	}
	
	private void addSignalControllerTranslations() {
		add(SignalControllerRegistration.SIGNAL_CONTROLLER_ITEM.get(), "Signalsteuerung", "Signal Controller");
		add(SignalControllerRegistration.REDSTONE_ADAPTER_ITEM.get(), "Redstoneadapter", "Redstone Adapter");
		
		add(SCREEN + ".override_pattern", "Signalbild überschreiben", "Override Pattern");
		
		addRedstonePredicateTranslations();
		
		add(SCREEN + ".screen_predicate", "Bedingung ändern", "Modify Condition");
		
		addSignalPatternTranslations();
	}
	
	private void addRedstonePredicateTranslations() {
		String s = SCREEN + ".redstone_predicate";
		add(s + ".north", "N", "N");
		add(s + ".east", "O", "E");
		add(s + ".south", "S", "S");
		add(s + ".west", "W", "W");
		
		add(s + ".power", "Stärke", "Power");
	}
	
	private void addSignalPatternTranslations() {
		String s = SCREEN + ".signal_pattern";
		
		add(s, "Signalbild ändern", "Modify Pattern");
		
		add(s + ".on", "An", "On");
		add(s + ".off", "Aus", "Off");
		
		add(s + ".main.none", "Aus", "None");
		add(s + ".main.hp0", "Hp0", "Hp0");
		add(s + ".main.ks1", "Ks1", "Ks1");
		add(s + ".main.ks2", "Ks2", "Ks2");
		
		add(s + ".zs3", "Zs3", "Zs3");
		add(s + ".zs3v", "Zs3v", "Zs3v");
		add(s + ".shorterDistance", "Verkürzter Bremsweg", "Shorter Distance");
		add(s + ".signalRepeater", "Vorsignalwiederholer", "Signal Repeater");
		add(s + ".zs7", "Zs7", "Zs7");
		add(s + ".sh1", "Sh1", "Sh1");
		add(s + ".zs1", "Zs1", "Zs1");
		add(s + ".marker", "Kennlicht", "Marker");
	}
	
	private void add(String key, String de_de, String en_us) {
		switch (locale) {
			case "de_de":
				add(key, de_de);
				break;
			case "en_us":
				add(key, en_us);
				break;
		}
	}
	
	private void add(@Nonnull Item key, String de_de, String en_us) {
		add(key.getDescriptionId(), de_de, en_us);
	}
	
}
