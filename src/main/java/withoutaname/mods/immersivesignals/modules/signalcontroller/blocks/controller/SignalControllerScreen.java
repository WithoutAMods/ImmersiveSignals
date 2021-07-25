package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.controller;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import withoutaname.mods.immersivesignals.ImmersiveSignals;
import withoutaname.mods.immersivesignals.datagen.Language;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.SignalDisplay;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.SignalPatternScreen;
import withoutaname.mods.withoutalib.blocks.BaseScreen;

public class SignalControllerScreen extends BaseScreen<SignalControllerContainer> {
	
	private Button overrideButton;
	
	public SignalControllerScreen(SignalControllerContainer container, Inventory playerInventory, Component title) {
		super(container, new ResourceLocation(ImmersiveSignals.MODID, "textures/gui/signal_template_small.png"), playerInventory, title, 224, 126);
	}
	
	@Override
	protected void init() {
		super.init();
		int i = this.leftPos;
		int j = this.topPos;
		
		addRenderableWidget(new SignalDisplay(i + 12, j + 39, 3, true, true,
				() -> menu.isOverride() ? menu.getOverridePattern() : menu.getDefaultPattern()));
		
		assert minecraft != null;
		assert minecraft.gameMode != null;
		overrideButton = addRenderableWidget(new Button(i + 48, j + 41, 164, 20, TextComponent.EMPTY,
				p_onPress_1_ -> minecraft.gameMode.handleInventoryButtonClick(menu.containerId, 0)));
		
		menu.setOnOverrideChanged(this::onOverrideChanged);
		onOverrideChanged();
		
		addRenderableWidget(new Button(i + 48, j + 41 + 24, 164, 20,
				new TranslatableComponent(Language.SCREEN + ".signal_pattern"),
				p_onPress_1_ -> {
					final SignalPatternScreen signalSelectionScreen = new SignalPatternScreen(this, menu::getOverridePattern);
					this.minecraft.setScreen(signalSelectionScreen);
					menu.setOnOverridePatternChanged(signalSelectionScreen::update);
				}));
	}
	
	private void onOverrideChanged() {
		overrideButton.setMessage(new TranslatableComponent(Language.SCREEN + ".override_pattern")
				.append(": ")
				.append(new TranslatableComponent("gui." + (menu.isOverride() ? "yes" : "no"))));
	}
	
	@Override
	protected void renderLabels(@Nonnull PoseStack pPoseStack, int x, int y) {}
	
}
