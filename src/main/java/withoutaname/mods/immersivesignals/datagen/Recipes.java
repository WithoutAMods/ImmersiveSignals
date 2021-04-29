package withoutaname.mods.immersivesignals.datagen;

import java.util.function.Consumer;
import javax.annotation.Nonnull;

import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;

import withoutaname.mods.immersivesignals.modules.signal.SignalRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;

public class Recipes extends RecipeProvider {
	
	public Recipes(DataGenerator generatorIn) {
		super(generatorIn);
	}
	
	@Override
	protected void buildShapelessRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(SignalRegistration.SIGNAL_ITEM.get())
				.pattern("RLR")
				.pattern(" I ")
				.pattern(" I ")
				.define('R', Items.REDSTONE)
				.define('L', Items.REDSTONE_LAMP)
				.define('I', Items.IRON_INGOT)
				.unlockedBy("redstone_lamp", InventoryChangeTrigger.Instance.hasItems(Items.REDSTONE))
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
				.unlockedBy("signal", InventoryChangeTrigger.Instance.hasItems(SignalRegistration.SIGNAL_ITEM.get()))
				.save(consumer);
		ShapedRecipeBuilder.shaped(SignalControllerRegistration.REDSTONE_ADAPTER_ITEM.get())
				.pattern("IRI")
				.pattern("RSR")
				.pattern("IRI")
				.define('I', Items.IRON_INGOT)
				.define('R', Items.REDSTONE)
				.define('S', Items.STONE)
				.unlockedBy("signal", InventoryChangeTrigger.Instance.hasItems(SignalRegistration.SIGNAL_ITEM.get()))
				.save(consumer);
	}
	
}
