package withoutaname.mods.immersivesignals.modules.signalcontroller.gui;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import withoutaname.mods.immersivesignals.ImmersiveSignals;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.PredicatePacket;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.SignalControllerNetworking;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.BasePredicate;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.MultiPredicate;

public class MultiPredicateScreen extends Screen {
	
	protected final ResourceLocation GUI_TEXTURE = new ResourceLocation(ImmersiveSignals.MODID, "textures/gui/predicate_screen.png");
	
	private final int PREDICATES_PER_SIDE = 5;
	
	private final Screen lastScreen;
	private final MultiPredicate<?> multiPredicate;
	private final PredicateWidget[] predicateWidgets = new PredicateWidget[PREDICATES_PER_SIDE];
	private final Button[] removeButtons = new Button[PREDICATES_PER_SIDE];
	
	private final int xSize = 212;
	private final int ySize = 196;
	private int guiLeft;
	private int guiTop;
	
	private Button preButton;
	private Button nextButton;
	
	private int currentPage = 0;
	
	public MultiPredicateScreen(Screen lastScreen, MultiPredicate<?> multiPredicate) {
		super(TextComponent.EMPTY);
		this.lastScreen = lastScreen;
		this.multiPredicate = multiPredicate;
	}
	
	public static void open(MultiPredicate<?> multiPredicate) {
		Minecraft.getInstance().setScreen(new MultiPredicateScreen(Minecraft.getInstance().screen, multiPredicate));
	}
	
	@Override
	protected void init() {
		super.init();
		int i = this.guiLeft = (this.width - this.xSize) / 2;
		int j = this.guiTop = (this.height - this.ySize) / 2;
		
		preButton = addRenderableWidget(new Button(i + 12, j + 12, 20, 20, new TextComponent("<"),
				button -> {
					if (currentPage > 0) {
						currentPage--;
						updateWidgets();
					}
				}));
		
		nextButton = addRenderableWidget(new Button(i + 156, j + 12, 20, 20, new TextComponent(">"),
				button -> {
					if ((currentPage + 1) * PREDICATES_PER_SIDE < multiPredicate.getPredicates().size()) {
						currentPage++;
						updateWidgets();
					}
				}));
		
		addRenderableWidget(new Button(i + 180, j + 12, 20, 20, new TextComponent("+"),
				button -> {
					final BasePredicate<?> predicate = BasePredicate.getInstance(multiPredicate.getSubInstance().getId());
					if (predicate != null) {
						final int id = multiPredicate.getPredicates().size();
						multiPredicate.addPredicate(predicate);
						currentPage = id / PREDICATES_PER_SIDE;
						updateWidgets();
					}
				}));
		
		BasePredicate<?> instance = BasePredicate.getInstance(multiPredicate.getSubInstance().getId());
		if (instance != null) {
			for (int k = 0; k < PREDICATES_PER_SIDE; k++) {
				predicateWidgets[k] = instance.createWidget(this::addRenderableWidget, i + 12, j + 40 + k * 24);
				addRenderableWidget(predicateWidgets[k]);
				removeButtons[k] = createRemoveButton(k);
			}
		}
		updateWidgets();
		
		addRenderableWidget(new Button(i + 12, j + 40 + PREDICATES_PER_SIDE * 24 + 4, 92, 20,
				CommonComponents.GUI_CANCEL, (p_214186_1_) -> onClose()));
		addRenderableWidget(new Button(i + 12 + 96, j + 40 + PREDICATES_PER_SIDE * 24 + 4, 92, 20,
				CommonComponents.GUI_DONE, (p_214187_1_) -> saveAndClose()));
	}
	
	@Nonnull
	private Button createRemoveButton(int id) {
		return addRenderableWidget(new Button(this.guiLeft + 180, this.guiTop + 40 + id * 24, 20, 20, new TextComponent("X"),
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
	public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(poseStack);
		this.drawGuiContainerBackgroundLayer(poseStack);
		assert minecraft != null;
		AbstractWidget.drawCenteredString(poseStack, minecraft.font,
				(multiPredicate.getPredicates().size() == 0 ? 0 : currentPage * PREDICATES_PER_SIDE + 1)
						+ " - " + (currentPage < multiPredicate.getPredicates().size() / PREDICATES_PER_SIDE ? (currentPage + 1) * PREDICATES_PER_SIDE : currentPage * PREDICATES_PER_SIDE + multiPredicate.getPredicates().size() % PREDICATES_PER_SIDE)
						+ " / " + multiPredicate.getPredicates().size(), this.guiLeft + 94, this.guiTop + 18, 16777215);
		super.render(poseStack, mouseX, mouseY, partialTicks);
	}
	
	protected void drawGuiContainerBackgroundLayer(PoseStack poseStack) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, GUI_TEXTURE);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.blit(poseStack, i, j, 0, 0, this.xSize, this.ySize);
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
	public void onClose() {
		assert this.minecraft != null;
		this.minecraft.setScreen(lastScreen);
	}
	
	private void saveAndClose() {
		SignalControllerNetworking.sendToServer(new PredicatePacket(multiPredicate));
		onClose();
	}
	
}
