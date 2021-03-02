package withoutaname.mods.immersivesignals.modules.signalcontroller.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.widget.Slider;
import org.jetbrains.annotations.NotNull;
import withoutaname.mods.immersivesignals.ImmersiveSignals;
import withoutaname.mods.immersivesignals.modules.signal.blocks.BaseSignalBlock;
import withoutaname.mods.immersivesignals.tools.SignalPattern;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SignalPatternScreen extends Screen {

	protected final ResourceLocation GUI_TEXTURE = new ResourceLocation(ImmersiveSignals.MODID, "textures/gui/signal_template.png");

	private final int xSize = 256;
	private final int ySize = 164;
	private int guiLeft;
	private int guiTop;

	private final Screen lastScreen;
	private final Supplier<SignalPattern> patternSupplier;
	private final Consumer<Integer> buttonIdConsumer;

	private Button mainNoneButton;
	private Button mainHp0Button;
	private Button mainKs1Button;
	private Button mainKs2Button;
	private Slider zs3Button;
	private Slider zs3vButton;
	private Button shortenedBrakingDistanceButton;
	private Button approachSignalRepeaterButton;
	private Button zs7Button;
	private Button sh1Button;
	private Button zs1Button;
	private Button markerLightButton;

	public SignalPatternScreen(Screen lastScreen, Supplier<SignalPattern> patternSupplier, Consumer<Integer> buttonIdConsumer) {
		super(StringTextComponent.EMPTY);
		this.lastScreen = lastScreen;
		this.patternSupplier = patternSupplier;
		this.buttonIdConsumer = buttonIdConsumer;
	}

	@Override
	protected void init() {
		super.init();
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		addButton(new SignalDisplay(this.guiLeft + 8, this.guiTop + 50, 4, true, true, patternSupplier));
		
		int i = this.guiLeft + 48;
		int j = this.guiTop + 12;
		final int width = this.xSize - 48 - 12;
		final int split2Width = (width - 4) / 2;
		final int split3Width = (width - 2 * 4) / 3;
		final int split4Width = (width - 3 * 4) / 4;

		mainNoneButton = addButton(new Button(i, j, split4Width, 20, new StringTextComponent("None"),
				p_onPress_1_ -> buttonIdConsumer.accept(0)));
		mainHp0Button = addButton(new Button(i + split4Width + 4, j, split4Width, 20, new StringTextComponent("Hp0"),
				p_onPress_1_ -> buttonIdConsumer.accept((1 << 4))));
		mainKs1Button = addButton(new Button(i +  2 * (split4Width + 4), j, split4Width, 20, new StringTextComponent("Ks1"),
				p_onPress_1_ -> buttonIdConsumer.accept((2 << 4))));
		mainKs2Button = addButton(new Button(i + 3 * (split4Width + 4), j, split4Width, 20, new StringTextComponent("Ks2"),
				p_onPress_1_ -> buttonIdConsumer.accept((3 << 4))));
		zs3Button = addButton(new Slider(i, j + 24, split2Width, 20, title, title,
				0, 15, patternSupplier.get().getZs3(), false, false, p_onPress_1_ -> {},
				slider -> buttonIdConsumer.accept(1 + (slider.getValueInt() << 4))));
		zs3vButton = addButton(new Slider(i + split2Width + 4, j + 24, split2Width, 20, title, title,
				0, 15, patternSupplier.get().getZs3v(), false, false, p_onPress_1_ -> {},
				slider -> buttonIdConsumer.accept(2 + (slider.getValueInt() << 4))));
		shortenedBrakingDistanceButton = addButton(new Button(i, j + 48, width, 20, title,
				p_onPress_1_ -> buttonIdConsumer.accept(3)));
		approachSignalRepeaterButton = addButton(new Button(i, j + 72, width, 20, title,
				p_onPress_1_ -> buttonIdConsumer.accept(4)));
		zs7Button = addButton(new Button(i, j + 96, split3Width, 20, title,
				p_onPress_1_ -> buttonIdConsumer.accept(5)));
		sh1Button = addButton(new Button(i + split3Width + 5, j + 96, split3Width, 20, title,
				p_onPress_1_ -> buttonIdConsumer.accept(6)));
		zs1Button = addButton(new Button(i + 2 * (split3Width + 5), j + 96, split3Width, 20, title,
				p_onPress_1_ -> buttonIdConsumer.accept(7)));
		markerLightButton = addButton(new Button(i, j + 120, width, 20, title,
				p_onPress_1_ -> buttonIdConsumer.accept(8)));
		
		this.update();
	}
	
	public void update() {
		final SignalPattern pattern = patternSupplier.get();
		mainNoneButton.active = pattern.getMainPattern() != BaseSignalBlock.SignalMainPattern.NONE;
		mainHp0Button.active = pattern.getMainPattern() != BaseSignalBlock.SignalMainPattern.HP0;
		mainKs1Button.active = pattern.getMainPattern() != BaseSignalBlock.SignalMainPattern.KS1;
		mainKs2Button.active = pattern.getMainPattern() != BaseSignalBlock.SignalMainPattern.KS2;
		zs3Button.active = pattern.isZs3Possible();
		zs3Button.setValue(pattern.getZs3());
		zs3Button.setMessage(new StringTextComponent(
				"Zs3: " + (pattern.getZs3() == 0 ? "No" : pattern.getZs3())));
		zs3vButton.active = pattern.isZs3vPossible();
		zs3vButton.setValue(pattern.getZs3v());
		zs3vButton.setMessage(new StringTextComponent(
				"Zs3v: " + (pattern.getZs3v() == 0 ? "No" : pattern.getZs3v())));
		shortenedBrakingDistanceButton.active = pattern.isShortenedBrakingDistancePossible();
		shortenedBrakingDistanceButton.setMessage(new StringTextComponent(
				"Shorter Distance: " + (pattern.isShortenedBrakingDistance() ? "Yes" : "No")));
		approachSignalRepeaterButton.active = pattern.isApproachSignalRepeaterPossible();
		approachSignalRepeaterButton.setMessage(new StringTextComponent(
				"Signal Repeater: " + (pattern.isApproachSignalRepeater() ? "Yes" : "No")));
		zs7Button.active = pattern.isZs7Possible();
		zs7Button.setMessage(new StringTextComponent(
				"Zs7: " + (pattern.isZs7() ? "Yes" : "No")));
		sh1Button.active = pattern.isSh1Possible();
		sh1Button.setMessage(new StringTextComponent(
				"Sh1: " + (pattern.isSh1() ? "Yes" : "No")));
		zs1Button.active = pattern.isZs1Possible();
		zs1Button.setMessage(new StringTextComponent(
				"Zs1: " + (pattern.isZs1() ? "Yes" : "No")));
		markerLightButton.active = pattern.isMarkerLightPossible();
		markerLightButton.setMessage(new StringTextComponent(
				"Marker: " + (pattern.isMarkerLight() ? "Yes" : "No")));
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if (button == 0) {
			zs3Button.dragging = false;
			zs3vButton.dragging = false;
		}
		return super.mouseReleased(mouseX, mouseY, button);
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
