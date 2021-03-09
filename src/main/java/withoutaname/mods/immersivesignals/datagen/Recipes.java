package withoutaname.mods.immersivesignals.datagen;

import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import withoutaname.mods.immersivesignals.modules.signal.SignalRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider{

	public Recipes(DataGenerator generatorIn) {
		super(generatorIn);
	}
	
	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		ShapedRecipeBuilder.shapedRecipe(SignalRegistration.SIGNAL_ITEM.get())
				.patternLine("RLR")
				.patternLine(" I ")
				.patternLine(" I ")
				.key('R', Items.REDSTONE)
				.key('L', Items.REDSTONE_LAMP)
				.key('I', Items.IRON_INGOT)
				.addCriterion("redstone_lamp", InventoryChangeTrigger.Instance.forItems(Items.REDSTONE))
				.build(consumer);

		ShapedRecipeBuilder.shapedRecipe(SignalControllerRegistration.SIGNAL_CONTROLLER_ITEM.get())
				.patternLine("RCR")
				.patternLine("STS")
				.patternLine("ISI")
				.key('R', Items.REDSTONE)
				.key('C', Items.BLACK_CONCRETE)
				.key('S', Items.STONE)
				.key('T', Items.REDSTONE_TORCH)
				.key('I', Items.IRON_INGOT)
				.addCriterion("signal", InventoryChangeTrigger.Instance.forItems(SignalRegistration.SIGNAL_ITEM.get()))
				.build(consumer);
		ShapedRecipeBuilder.shapedRecipe(SignalControllerRegistration.REDSTONE_ADAPTER_ITEM.get())
				.patternLine("IRI")
				.patternLine("RSR")
				.patternLine("IRI")
				.key('I', Items.IRON_INGOT)
				.key('R', Items.REDSTONE)
				.key('S', Items.STONE)
				.addCriterion("signal", InventoryChangeTrigger.Instance.forItems(SignalRegistration.SIGNAL_ITEM.get()))
				.build(consumer);
	}

}
