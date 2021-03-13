package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.jetbrains.annotations.NotNull;
import withoutaname.mods.immersivesignals.ImmersiveSignals;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.PredicateWidget;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.SignalDisplay;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.SignalPatternScreen;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.BasePredicate;
import withoutaname.mods.withoutalib.blocks.BaseScreen;

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
		int i = this.guiLeft;
		int j = this.guiTop;

		addButton(new SignalDisplay(i + 12, j + 39, 3, true, true,
				container::getCurrentPattern));

		assert minecraft != null;
		assert minecraft.playerController != null;

		preButton = addButton(new Button(i + 48, j + 12, 20, 20, new StringTextComponent("<"),
				p_onPress_1_ -> minecraft.playerController.sendEnchantPacket(container.windowId, 0)));

		nextButton = addButton(new Button(i + 192, j + 12, 20, 20, new StringTextComponent(">"),
				p_onPress_1_ -> minecraft.playerController.sendEnchantPacket(container.windowId, 1)));

		if (predicateWidget != null) {
			addButton(predicateWidget);
			updatePredicate();
		} else {
			container.setOnPredicateIDChanged(() -> {
				predicateWidget = addButton(BasePredicate.getInstance(container.getPredicateID()).createWidget(this::addButton, i + 48, j + 41));
				container.setOnPredicateIDChanged(() -> {});
				updatePredicate();
			});
		}

		modifyPatternButton = addButton(new Button(i + 48, j + 41 + 24, 164, 20, new StringTextComponent("Modify Pattern"),
				p_onPress_1_ -> {
					final SignalPatternScreen signalSelectionScreen = new SignalPatternScreen(this, container::getCurrentPattern);
					this.minecraft.displayGuiScreen(signalSelectionScreen);
					container.setOnCurrentPatternChanged(signalSelectionScreen::update);
				}));

		deleteButton = addButton(new Button(i + 48, j + 94, 20, 20, new StringTextComponent("X"),
				p_onPress_1_ -> minecraft.playerController.sendEnchantPacket(container.windowId, 2)));

		addButton(new Button(i + 192, j + 94, 20, 20, new StringTextComponent("+"),
				p_onPress_1_ -> minecraft.playerController.sendEnchantPacket(container.windowId, 3)));

		container.setOnPredicatePatternsSizeChanged(this::updatePredicatePatternIDSelection);
		container.setOnCurrentPredicatePatternIDChanged(() -> {
			if (container.getCurrentPredicatePatternID() == -1) {
				predicateWidget.active = false;
				modifyPatternButton.active = false;
				deleteButton.active = false;
				if (!(minecraft.currentScreen == this)) {
					minecraft.displayGuiScreen(this);
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
		preButton.active = container.getCurrentPredicatePatternID() > 0;
		nextButton.active = container.getCurrentPredicatePatternID() < container.getPredicatePatternsSize() - 1;
	}

	public void setPredicate(BasePredicate<?> predicate) {
		predicateWidget.setPredicate(predicate);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		super.drawGuiContainerBackgroundLayer(matrixStack, partialTicks, x, y);
		Widget.drawCenteredString(matrixStack, minecraft.fontRenderer, (container.getCurrentPredicatePatternID() + 1) + " / " + container.getPredicatePatternsSize(), this.guiLeft + 130, this.guiTop + 18, 16777215);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(@NotNull MatrixStack matrixStack, int x, int y) {}

	@Override
	public void onClose() {
		nextPredicate = null;
		super.onClose();
	}
}
