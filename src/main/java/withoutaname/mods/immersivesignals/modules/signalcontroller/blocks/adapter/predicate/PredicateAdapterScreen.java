package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import withoutaname.mods.immersivesignals.ImmersiveSignals;
import withoutaname.mods.immersivesignals.datagen.Language;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.PredicateWidget;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.SignalDisplay;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.SignalPatternScreen;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.BasePredicate;
import withoutaname.mods.withoutalib.blocks.BaseScreen;

import javax.annotation.Nonnull;

public class PredicateAdapterScreen extends BaseScreen<PredicateAdapterContainer<?>> {
	
	private static BasePredicate<?> nextPredicate;
	protected PredicateWidget predicateWidget;
	protected Button modifyPatternButton;
	private Button nextButton;
	private Button preButton;
	private Button deleteButton;
	
	public PredicateAdapterScreen(PredicateAdapterContainer<?> container, Inventory playerInventory, Component title) {
		super(container, new ResourceLocation(ImmersiveSignals.MODID, "textures/gui/signal_template_small.png"), playerInventory, title, 224, 126);
	}
	
	public static void setNextPredicate(BasePredicate<?> predicate) {
		nextPredicate = predicate;
	}
	
	@Override
	protected void init() {
		super.init();
		int i = this.leftPos;
		int j = this.topPos;
		addRenderableWidget(new SignalDisplay(i + 12, j + 39, 3, true, true,
				menu::getCurrentPattern));
		
		assert minecraft != null;
		assert minecraft.gameMode != null;
		
		preButton = addRenderableWidget(new Button(i + 48, j + 12, 20, 20, new TextComponent("<"),
				p_onPress_1_ -> minecraft.gameMode.handleInventoryButtonClick(menu.containerId, 0)));
		
		nextButton = addRenderableWidget(new Button(i + 192, j + 12, 20, 20, new TextComponent(">"),
				p_onPress_1_ -> minecraft.gameMode.handleInventoryButtonClick(menu.containerId, 1)));
		
		if (predicateWidget != null) {
			addRenderableWidget(predicateWidget);
			updatePredicate();
		} else {
			menu.setOnPredicateIDChanged(() -> {
				predicateWidget = addRenderableWidget(BasePredicate.getInstance(menu.getPredicateID()).createWidget(this::addRenderableWidget, i + 48, j + 41));
				menu.setOnPredicateIDChanged(() -> {});
				updatePredicate();
			});
		}
		
		modifyPatternButton = addRenderableWidget(new Button(i + 48, j + 41 + 24, 164, 20, new TranslatableComponent(Language.SCREEN + ".signal_pattern"),
				p_onPress_1_ -> {
					final SignalPatternScreen signalSelectionScreen = new SignalPatternScreen(this, menu::getCurrentPattern);
					this.minecraft.setScreen(signalSelectionScreen);
					menu.setOnCurrentPatternChanged(signalSelectionScreen::update);
				}));
		
		deleteButton = addRenderableWidget(new Button(i + 48, j + 94, 20, 20, new TextComponent("X"),
				p_onPress_1_ -> minecraft.gameMode.handleInventoryButtonClick(menu.containerId, 2)));
		
		addRenderableWidget(new Button(i + 192, j + 94, 20, 20, new TextComponent("+"),
				p_onPress_1_ -> minecraft.gameMode.handleInventoryButtonClick(menu.containerId, 3)));
		
		menu.setOnPredicatePatternsSizeChanged(this::updatePredicatePatternIDSelection);
		menu.setOnCurrentPredicatePatternIDChanged(() -> {
			if (menu.getCurrentPredicatePatternID() == -1) {
				predicateWidget.active = false;
				modifyPatternButton.active = false;
				deleteButton.active = false;
				if (!(minecraft.screen == this)) {
					minecraft.setScreen(this);
				}
			} else {
				predicateWidget.active = true;
				modifyPatternButton.active = true;
				deleteButton.active = true;
			}
			updatePredicatePatternIDSelection();
		});
		updatePredicatePatternIDSelection();
		
	}
	
	private void updatePredicate() {
		if (nextPredicate != null) {
			predicateWidget.setPredicate(nextPredicate);
			nextPredicate = null;
		}
	}
	
	private void updatePredicatePatternIDSelection() {
		preButton.active = menu.getCurrentPredicatePatternID() > 0;
		nextButton.active = menu.getCurrentPredicatePatternID() < menu.getPredicatePatternsSize() - 1;
	}
	
	public void setPredicate(BasePredicate<?> predicate) {
		predicateWidget.setPredicate(predicate);
	}
	
	@Override
	protected void renderBg(@Nonnull PoseStack matrixStack, float partialTicks, int x, int y) {
		super.renderBg(matrixStack, partialTicks, x, y);
		assert minecraft != null;
		AbstractWidget.drawCenteredString(matrixStack, minecraft.font, (menu.getCurrentPredicatePatternID() + 1) + " / " + menu.getPredicatePatternsSize(), this.leftPos + 130, this.topPos + 18, 16777215);
	}
	
	@Override
	protected void renderLabels(@Nonnull PoseStack matrixStack, int x, int y) {}
	
	@Override
	public void removed() {
		nextPredicate = null;
		super.removed();
	}
	
}
