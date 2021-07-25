package withoutaname.mods.immersivesignals.datagen;

import javax.annotation.Nonnull;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	
	@SubscribeEvent
	public static void gatherData(@Nonnull GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		generator.addProvider(new BlockStates(generator, event.getExistingFileHelper()));
		generator.addProvider(new Recipes(generator));
		generator.addProvider(new Language(generator, "en_us"));
		generator.addProvider(new Language(generator, "de_de"));
		generator.addProvider(new LootTables(generator));
	}
	
}
