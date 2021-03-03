package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.BasePredicate;
import withoutaname.mods.withoutalib.blocks.BaseScreen;

public class PredicateAdapterScreen<T extends BasePredicate<T>> extends BaseScreen<PredicateAdapterContainer<T>> {

	public PredicateAdapterScreen(PredicateAdapterContainer<T> container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, new ResourceLocation("textures/gui/demo_background.png"), playerInventory, title, 248, 166);
	}

	@Override
	protected void init() {
		super.init();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(@NotNull MatrixStack matrixStack, int x, int y) {}

}
