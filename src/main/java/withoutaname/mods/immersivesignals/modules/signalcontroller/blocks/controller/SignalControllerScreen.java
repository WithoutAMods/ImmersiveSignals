package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.controller;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import withoutaname.mods.immersivesignals.ImmersiveSignals;
import withoutaname.mods.immersivesignals.datagen.Language;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.SignalDisplay;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.SignalPatternScreen;
import withoutaname.mods.withoutalib.blocks.BaseScreen;

public class SignalControllerScreen extends BaseScreen<SignalControllerContainer> {
	
	private Button overrideButton;
	
	public SignalControllerScreen(SignalControllerContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, new ResourceLocation(ImmersiveSignals.MODID, "textures/gui/signal_template_small.png"), playerInventory, title, 224, 126);
	}
	
	@Override
	protected void init() {
		super.init();
		int i = this.leftPos;
		int j = this.topPos;
		
		addButton(new SignalDisplay(i + 12, j + 39, 3, true, true,
				() -> menu.isOverride() ? menu.getOverridePattern() : menu.getDefaultPattern()));
		
		assert minecraft != null;
		assert minecraft.gameMode != null;
		overrideButton = addButton(new Button(i + 48, j + 41, 164, 20, StringTextComponent.EMPTY,
				p_onPress_1_ -> minecraft.gameMode.handleInventoryButtonClick(menu.containerId, 0)));
		
		menu.setOnOverrideChanged(this::onOverrideChanged);
		onOverrideChanged();
		
		addButton(new Button(i + 48, j + 41 + 24, 164, 20,
				new TranslationTextComponent(Language.SCREEN + ".signal_pattern"),
				p_onPress_1_ -> {
					final SignalPatternScreen signalSelectionScreen = new SignalPatternScreen(this, menu::getOverridePattern);
					this.minecraft.setScreen(signalSelectionScreen);
					menu.setOnOverridePatternChanged(signalSelectionScreen::update);
				}));
	}
	
	private void onOverrideChanged() {
		overrideButton.setMessage(new TranslationTextComponent(Language.SCREEN + ".override_pattern")
				.append(": ")
				.append(new TranslationTextComponent("gui." + (menu.isOverride() ? "yes" : "no"))));
	}
	
	@Override
	protected void renderLabels(@Nonnull MatrixStack matrixStack, int x, int y) {}
	
}
