package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.jetbrains.annotations.NotNull;
import withoutaname.mods.immersivesignals.ImmersiveSignals;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.SignalDisplay;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.SignalPatternScreen;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.BasePredicate;
import withoutaname.mods.withoutalib.blocks.BaseScreen;

public class PredicateAdapterScreen<T extends BasePredicate<T>> extends BaseScreen<PredicateAdapterContainer<T>> {

	public PredicateAdapterScreen(PredicateAdapterContainer<T> container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, new ResourceLocation(ImmersiveSignals.MODID, "textures/gui/signal_template_small.png"), playerInventory, title, 180, 126);
	}

	@Override
	protected void init() {
		super.init();
		int i = this.guiLeft;
		int j = this.guiTop;
		final StringTextComponent title = new StringTextComponent("");

		addButton(new SignalDisplay(i + 12, j + 39, 3, true, true,
				container::getCurrentPattern));

		assert minecraft != null;
		assert minecraft.playerController != null;

		addButton(new Button(i + 48, j + 41, 120, 20, new StringTextComponent("Modify Predicate"),
				p_onPress_1_ -> minecraft.playerController.sendEnchantPacket(container.windowId, 0)));

		addButton(new Button(i + 48, j + 41 + 24, 120, 20, new StringTextComponent("Modify Pattern"),
				p_onPress_1_ -> {
					final SignalPatternScreen signalSelectionScreen = new SignalPatternScreen(this, container::getCurrentPattern);
					this.minecraft.displayGuiScreen(signalSelectionScreen);
					container.setOnCurrentPatternChanged(signalSelectionScreen::update);
				}));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(@NotNull MatrixStack matrixStack, int x, int y) {}

}
