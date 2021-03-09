package withoutaname.mods.immersivesignals.modules.signalcontroller.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import org.jetbrains.annotations.NotNull;
import withoutaname.mods.immersivesignals.ImmersiveSignals;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.MultiPredicateModifiedPacket;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.SignalControllerNetworking;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.BasePredicate;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.MultiPredicate;

public class MultiPredicateScreen extends Screen {

	protected final ResourceLocation GUI_TEXTURE = new ResourceLocation(ImmersiveSignals.MODID, "textures/gui/predicate_screen.png");

	private final int xSize = 212;
	private final int ySize = 196;
	private int guiLeft;
	private int guiTop;

	private final int PREDICATES_PER_SIDE = 5;

	private final Screen lastScreen;
	private final int predicateType;
	private final MultiPredicate<?> multiPredicate;
	private final PredicateWidget[] predicateWidgets = new PredicateWidget[PREDICATES_PER_SIDE];
	private final Button[] removeButtons = new Button[PREDICATES_PER_SIDE];
	private Button preButton;
	private Button nextButton;

	private int currentPage = 0;

	public MultiPredicateScreen(Screen lastScreen, int predicateType, MultiPredicate<?> multiPredicate) {
		super(StringTextComponent.EMPTY);
		this.lastScreen = lastScreen;
		this.predicateType = predicateType;
		this.multiPredicate = multiPredicate;
	}

	@Override
	protected void init() {
		super.init();
		int i = this.guiLeft = (this.width - this.xSize) / 2;
		int j = this.guiTop = (this.height - this.ySize) / 2;

		preButton = addButton(new Button(i + 12, j + 12, 20, 20, new StringTextComponent("<"),
				button -> {
					if (currentPage > 0) {
						currentPage--;
						updateWidgets();
					}
				}));

		nextButton = addButton(new Button(i + 156, j + 12, 20, 20, new StringTextComponent(">"),
				button -> {
					if ((currentPage + 1) * PREDICATES_PER_SIDE < multiPredicate.getPredicates().size()) {
						currentPage++;
						updateWidgets();
					}
				}));

		addButton(new Button(i + 180, j + 12, 20, 20, new StringTextComponent("+"),
				button -> {
					final BasePredicate<?> predicate = BasePredicate.fromInt(predicateType);
					if (predicate != null) {
						final int id = multiPredicate.getPredicates().size();
						multiPredicate.addPredicate(predicate);
						currentPage = id / PREDICATES_PER_SIDE;
						updateWidgets();
					}
				}));

		for (int k = 0; k < PREDICATES_PER_SIDE; k++) {
			predicateWidgets[k] = BasePredicate.fromInt(predicateType).createWidget(this::addButton, i + 12, j + 40 + k * 24);
			addButton(predicateWidgets[k]);
			removeButtons[k] = createRemoveButton(k);
		}
		updateWidgets();

		addButton(new Button(i + 12, j + 40 + PREDICATES_PER_SIDE * 24 + 4, 92, 20,
				DialogTexts.GUI_CANCEL, (p_214186_1_) -> closeScreen()));
		addButton(new Button(i + 12 + 96, j + 40 + PREDICATES_PER_SIDE * 24 + 4, 92, 20,
				DialogTexts.GUI_DONE, (p_214187_1_) -> saveAndClose()));
	}

	private Button createRemoveButton(int id) {
		return addButton(new Button(this.guiLeft + 180, this.guiTop + 40 + id * 24, 20, 20, new StringTextComponent("X"),
				button -> {
					multiPredicate.getPredicates().remove(currentPage * PREDICATES_PER_SIDE + id);
					updateWidgets();
				}));
	}
	
	private void updateWidgets() {
		if (currentPage * PREDICATES_PER_SIDE >= multiPredicate.getPredicates().size()) {
			currentPage = (multiPredicate.getPredicates().size() - 1) / 5;
		}
		preButton.active = currentPage > 0;
		nextButton.active = (currentPage + 1) * PREDICATES_PER_SIDE < multiPredicate.getPredicates().size();
		for (int i = 0; i < PREDICATES_PER_SIDE; i++) {
			final boolean flag = i + currentPage * PREDICATES_PER_SIDE < multiPredicate.getPredicates().size();
			if (flag) {
				predicateWidgets[i].setPredicate(multiPredicate.getPredicates().get(i + currentPage * PREDICATES_PER_SIDE));
			}
			predicateWidgets[i].setVisible(flag);
			removeButtons[i].visible = flag;
		}
	}

	@Override
	public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		this.drawGuiContainerBackgroundLayer(matrixStack);
		Widget.drawCenteredString(matrixStack, minecraft.fontRenderer,
				(multiPredicate.getPredicates().size() == 0 ? 0 : currentPage * PREDICATES_PER_SIDE + 1)
						+ " - " + (currentPage < multiPredicate.getPredicates().size() / PREDICATES_PER_SIDE ? (currentPage + 1) * PREDICATES_PER_SIDE : currentPage * PREDICATES_PER_SIDE + multiPredicate.getPredicates().size() % PREDICATES_PER_SIDE)
						+ " / " + multiPredicate.getPredicates().size(), this.guiLeft + 94, this.guiTop + 18, 16777215);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}

	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		assert this.minecraft != null;
		this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if (button == 0) {
			for (PredicateWidget widget : predicateWidgets) {
				widget.mouseReleased(mouseX, mouseY, button);
			}
		}
		return super.mouseReleased(mouseX, mouseY, button);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (keyCode == 257) {
			saveAndClose();
			return true;
		}
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public void closeScreen() {
		assert this.minecraft != null;
		this.minecraft.displayGuiScreen(lastScreen);
	}

	private void saveAndClose() {
		SignalControllerNetworking.sendToServer(new MultiPredicateModifiedPacket(multiPredicate));
		closeScreen();
	}

}
