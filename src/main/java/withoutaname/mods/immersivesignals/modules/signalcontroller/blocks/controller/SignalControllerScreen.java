package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.controller;

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
import withoutaname.mods.withoutalib.blocks.BaseScreen;

public class SignalControllerScreen extends BaseScreen<SignalControllerContainer> {

	private Button overrideButton;

	public SignalControllerScreen(SignalControllerContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, new ResourceLocation(ImmersiveSignals.MODID, "textures/gui/container/signal_controller.png"), playerInventory, title, 180, 126);
	}

	@Override
	protected void init() {
		super.init();
		int i = this.guiLeft;
		int j = this.guiTop;
		final StringTextComponent title = new StringTextComponent("");

		addButton(new SignalDisplay(i + 12, j + 39, 3, true, true,
				() -> container.isOverride() ? container.getOverridePattern() : container.getDefaultPattern()));

		assert minecraft != null;
		assert minecraft.playerController != null;
		overrideButton = addButton(new Button(i + 48, j + 41, 120, 20, title,
				p_onPress_1_ -> minecraft.playerController.sendEnchantPacket(container.windowId, 0)));

		container.setOnOverrideChanged(this::onOverrideChanged);
		onOverrideChanged();

		addButton(new Button(i + 48, j + 41 + 24, 120, 20,
				new StringTextComponent("Set Override Pattern"),
				p_onPress_1_ -> {
					final SignalPatternScreen signalSelectionScreen = new SignalPatternScreen(this, container::getOverridePattern);
					this.minecraft.displayGuiScreen(signalSelectionScreen);
					container.setOnOverridePatternChanged(signalSelectionScreen::update);
				}));
	}

	private void onOverrideChanged() {
		overrideButton.setMessage(new StringTextComponent("Override Pattern: " + (container.isOverride() ? "Yes" : "No")));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(@NotNull MatrixStack matrixStack, int x, int y) {}

}
