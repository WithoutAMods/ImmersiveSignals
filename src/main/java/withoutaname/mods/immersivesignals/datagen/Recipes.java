package withoutaname.mods.immersivesignals.datagen;

import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import withoutaname.mods.immersivesignals.modules.signal.SignalRegistration;

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
				.key('L', Blocks.REDSTONE_LAMP)
				.key('I', Items.IRON_INGOT)
				.addCriterion("redstone_lamp", InventoryChangeTrigger.Instance.forItems(Blocks.REDSTONE_LAMP))
				.build(consumer);
	}

}
