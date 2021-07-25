package withoutaname.mods.immersivesignals.datagen;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import withoutaname.mods.immersivesignals.modules.signal.SignalRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
	
	public Recipes(DataGenerator generatorIn) {
		super(generatorIn);
	}
	
	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(SignalRegistration.SIGNAL_ITEM.get())
				.pattern("RLR")
				.pattern(" I ")
				.pattern(" I ")
				.define('R', Items.REDSTONE)
				.define('L', Items.REDSTONE_LAMP)
				.define('I', Items.IRON_INGOT)
				.unlockedBy("redstone_lamp", InventoryChangeTrigger.TriggerInstance.hasItems(Items.REDSTONE))
				.save(consumer);
		
		ShapedRecipeBuilder.shaped(SignalControllerRegistration.SIGNAL_CONTROLLER_ITEM.get())
				.pattern("RCR")
				.pattern("STS")
				.pattern("ISI")
				.define('R', Items.REDSTONE)
				.define('C', Items.BLACK_CONCRETE)
				.define('S', Items.STONE)
				.define('T', Items.REDSTONE_TORCH)
				.define('I', Items.IRON_INGOT)
				.unlockedBy("signal", InventoryChangeTrigger.TriggerInstance.hasItems(SignalRegistration.SIGNAL_ITEM.get()))
				.save(consumer);
		ShapedRecipeBuilder.shaped(SignalControllerRegistration.REDSTONE_ADAPTER_ITEM.get())
				.pattern("IRI")
				.pattern("RSR")
				.pattern("IRI")
				.define('I', Items.IRON_INGOT)
				.define('R', Items.REDSTONE)
				.define('S', Items.STONE)
				.unlockedBy("signal", InventoryChangeTrigger.TriggerInstance.hasItems(SignalRegistration.SIGNAL_ITEM.get()))
				.save(consumer);
	}
	
}
