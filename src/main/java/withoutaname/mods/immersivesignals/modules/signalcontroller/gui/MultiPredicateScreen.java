package withoutaname.mods.immersivesignals.modules.signalcontroller.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import org.jetbrains.annotations.NotNull;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.MultiPredicate;

public class MultiPredicateScreen extends Screen {

	protected final ResourceLocation GUI_TEXTURE = new ResourceLocation("textures/gui/demo_background.png");

	private final int xSize = 248;
	private final int ySize = 166;
	private int guiLeft;
	private int guiTop;

	private final Screen lastScreen;
	private final MultiPredicate<?> multiPredicate;

	public MultiPredicateScreen(Screen lastScreen, MultiPredicate<?> multiPredicate) {
		super(StringTextComponent.EMPTY);
		this.lastScreen = lastScreen;
		this.multiPredicate = multiPredicate;
	}

	@Override
	protected void init() {
		super.init();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
	}

	@Override
	public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		this.drawGuiContainerBackgroundLayer(matrixStack);
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
	public void closeScreen() {
		assert this.minecraft != null;
		this.minecraft.displayGuiScreen(lastScreen);
	}

}
