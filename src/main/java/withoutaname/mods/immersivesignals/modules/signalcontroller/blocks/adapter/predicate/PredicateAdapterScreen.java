package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import withoutaname.mods.immersivesignals.ImmersiveSignals;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.PredicateWidget;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.SignalDisplay;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.SignalPatternScreen;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.BasePredicate;
import withoutaname.mods.withoutalib.blocks.BaseScreen;

import javax.annotation.Nonnull;

public class PredicateAdapterScreen extends BaseScreen<PredicateAdapterContainer<?>> {

	private static BasePredicate<?> nextPredicate;

	private Button nextButton;
	private Button preButton;
	protected PredicateWidget predicateWidget;
	protected Button modifyPatternButton;
	private Button deleteButton;

	public PredicateAdapterScreen(PredicateAdapterContainer<?> container, PlayerInventory playerInventory, ITextComponent title) {
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

		addButton(new SignalDisplay(i + 12, j + 39, 3, true, true,
				menu::getCurrentPattern));

		assert minecraft != null;
		assert minecraft.gameMode != null;

		preButton = addButton(new Button(i + 48, j + 12, 20, 20, new StringTextComponent("<"),
				p_onPress_1_ -> minecraft.gameMode.handleInventoryButtonClick(menu.containerId, 0)));

		nextButton = addButton(new Button(i + 192, j + 12, 20, 20, new StringTextComponent(">"),
				p_onPress_1_ -> minecraft.gameMode.handleInventoryButtonClick(menu.containerId, 1)));

		if (predicateWidget != null) {
			addButton(predicateWidget);
			updatePredicate();
		} else {
			menu.setOnPredicateIDChanged(() -> {
				predicateWidget = addButton(BasePredicate.getInstance(menu.getPredicateID()).createWidget(this::addButton, i + 48, j + 41));
				menu.setOnPredicateIDChanged(() -> {});
				updatePredicate();
			});
		}

		modifyPatternButton = addButton(new Button(i + 48, j + 41 + 24, 164, 20, new StringTextComponent("Modify Pattern"),
				p_onPress_1_ -> {
					final SignalPatternScreen signalSelectionScreen = new SignalPatternScreen(this, menu::getCurrentPattern);
					this.minecraft.setScreen(signalSelectionScreen);
					menu.setOnCurrentPatternChanged(signalSelectionScreen::update);
				}));

		deleteButton = addButton(new Button(i + 48, j + 94, 20, 20, new StringTextComponent("X"),
				p_onPress_1_ -> minecraft.gameMode.handleInventoryButtonClick(menu.containerId, 2)));

		addButton(new Button(i + 192, j + 94, 20, 20, new StringTextComponent("+"),
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
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
		super.renderBg(matrixStack, partialTicks, x, y);
		Widget.drawCenteredString(matrixStack, minecraft.font, (menu.getCurrentPredicatePatternID() + 1) + " / " + menu.getPredicatePatternsSize(), this.leftPos + 130, this.topPos + 18, 16777215);
	}

	@Override
	protected void renderLabels(@Nonnull MatrixStack matrixStack, int x, int y) {}

	@Override
	public void removed() {
		nextPredicate = null;
		super.removed();
	}
}
