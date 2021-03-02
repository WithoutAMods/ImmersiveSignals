package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.redstone;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import withoutaname.mods.withoutalib.blocks.BaseScreen;

public class RedstoneAdapterScreen extends BaseScreen<RedstoneAdapterContainer> {

	public RedstoneAdapterScreen(RedstoneAdapterContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, new ResourceLocation("textures/gui/demo_background.png"), playerInventory, title, 248, 166);
	}

	@Override
	protected void init() {
		super.init();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {}

}
