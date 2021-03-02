package withoutaname.mods.immersivesignals.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		generator.addProvider(new BlockStates(generator, event.getExistingFileHelper()));
		generator.addProvider(new Recipes(generator));
		generator.addProvider(new Language(generator, "en_us"));
		generator.addProvider(new Language(generator, "de_de"));
		generator.addProvider(new LootTables(generator));
	}

}
