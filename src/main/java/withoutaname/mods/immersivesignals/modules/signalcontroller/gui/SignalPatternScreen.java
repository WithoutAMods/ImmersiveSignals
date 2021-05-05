package withoutaname.mods.immersivesignals.modules.signalcontroller.gui;

import java.util.function.Supplier;
import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.widget.Slider;

import withoutaname.mods.immersivesignals.ImmersiveSignals;
import withoutaname.mods.immersivesignals.datagen.Language;
import withoutaname.mods.immersivesignals.modules.signal.blocks.BaseSignalBlock;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.PatternModifyPacket;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.SignalControllerNetworking;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.SignalPattern;

public class SignalPatternScreen extends Screen {
	
	protected final ResourceLocation GUI_TEXTURE = new ResourceLocation(ImmersiveSignals.MODID, "textures/gui/signal_template.png");
	
	private final Screen lastScreen;
	private final Supplier<SignalPattern> patternSupplier;
	
	private final int xSize = 256;
	private final int ySize = 164;
	private int guiLeft;
	private int guiTop;
	
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
	private static final String SIGNAL_PATTERN = Language.SCREEN + ".signal_pattern.";
	
	public SignalPatternScreen(Screen lastScreen, Supplier<SignalPattern> patternSupplier) {
		super(StringTextComponent.EMPTY);
		this.lastScreen = lastScreen;
		this.patternSupplier = patternSupplier;
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
		
		String s = Language.SCREEN + ".signal_pattern";
		mainNoneButton = addButton(new Button(i, j, split4Width, 20, new TranslationTextComponent(s + ".main.none"),
				p_onPress_1_ -> sendPacket(0, 0)));
		mainHp0Button = addButton(new Button(i + split4Width + 4, j, split4Width, 20, new TranslationTextComponent(s + ".main.hp0"),
				p_onPress_1_ -> sendPacket(0, 1)));
		mainKs1Button = addButton(new Button(i + 2 * (split4Width + 4), j, split4Width, 20, new TranslationTextComponent(s + ".main.ks1"),
				p_onPress_1_ -> sendPacket(0, 2)));
		mainKs2Button = addButton(new Button(i + 3 * (split4Width + 4), j, split4Width, 20, new TranslationTextComponent(s + ".main.ks2"),
				p_onPress_1_ -> sendPacket(0, 3)));
		zs3Button = addButton(new Slider(i, j + 24, split2Width, 20, title, title,
				0, 15, patternSupplier.get().getZs3(), false, false, p_onPress_1_ -> {},
				slider -> sendPacket(1, slider.getValueInt())));
		zs3vButton = addButton(new Slider(i + split2Width + 4, j + 24, split2Width, 20, title, title,
				0, 15, patternSupplier.get().getZs3v(), false, false, p_onPress_1_ -> {},
				slider -> sendPacket(2, slider.getValueInt())));
		shortenedBrakingDistanceButton = addButton(new Button(i, j + 48, width, 20, title,
				p_onPress_1_ -> sendPacket(3)));
		approachSignalRepeaterButton = addButton(new Button(i, j + 72, width, 20, title,
				p_onPress_1_ -> sendPacket(4)));
		zs7Button = addButton(new Button(i, j + 96, split3Width, 20, title,
				p_onPress_1_ -> sendPacket(5)));
		sh1Button = addButton(new Button(i + split3Width + 5, j + 96, split3Width, 20, title,
				p_onPress_1_ -> sendPacket(6)));
		zs1Button = addButton(new Button(i + 2 * (split3Width + 5), j + 96, split3Width, 20, title,
				p_onPress_1_ -> sendPacket(7)));
		markerLightButton = addButton(new Button(i, j + 120, width, 20, title,
				p_onPress_1_ -> sendPacket(8)));
		
		this.update();
	}
	
	private void sendPacket(int buttonID) {
		sendPacket(buttonID, 0);
	}
	
	private void sendPacket(int buttonID, int value) {
		SignalControllerNetworking.sendToServer(new PatternModifyPacket(buttonID, value));
	}
	
	public void update() {
		final SignalPattern pattern = patternSupplier.get();
		mainNoneButton.active = pattern.getMainPattern() != BaseSignalBlock.SignalMainPattern.NONE;
		mainHp0Button.active = pattern.getMainPattern() != BaseSignalBlock.SignalMainPattern.HP0;
		mainKs1Button.active = pattern.getMainPattern() != BaseSignalBlock.SignalMainPattern.KS1;
		mainKs2Button.active = pattern.getMainPattern() != BaseSignalBlock.SignalMainPattern.KS2;
		update(zs3Button, "zs3", pattern.isZs3Possible(), pattern.getZs3());
		update(zs3vButton, "zs3v", pattern.isZs3vPossible(), pattern.getZs3v());
		update(shortenedBrakingDistanceButton, "shorterDistance", pattern.isShortenedBrakingDistancePossible(), pattern.isShortenedBrakingDistance());
		update(approachSignalRepeaterButton, "signalRepeater", pattern.isApproachSignalRepeaterPossible(), pattern.isApproachSignalRepeater());
		update(zs7Button, "zs7", pattern.isZs7Possible(), pattern.isZs7());
		update(sh1Button, "sh1", pattern.isSh1Possible(), pattern.isSh1());
		update(zs1Button, "zs1", pattern.isZs1Possible(), pattern.isZs1());
		update(markerLightButton, "marker", pattern.isMarkerLightPossible(), pattern.isMarkerLight());
	}
	
	private void update(@Nonnull Slider slider, @Nonnull String key, boolean possible, int value) {
		slider.active = possible;
		slider.setValue(value);
		slider.setMessage(new TranslationTextComponent(SIGNAL_PATTERN + key)
				.append(": ")
				.append(value == 0 ?
						new TranslationTextComponent(SIGNAL_PATTERN + "off") :
						new StringTextComponent(String.valueOf(value))));
	}
	
	private void update(@Nonnull Button button, @Nonnull String key, boolean possible, boolean on) {
		button.active = possible;
		button.setMessage(new TranslationTextComponent(SIGNAL_PATTERN + key)
				.append(": ")
				.append(new TranslationTextComponent(SIGNAL_PATTERN + (on ? "on" : "off"))));
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
	public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		this.drawGuiBackgroundLayer(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	protected void drawGuiBackgroundLayer(MatrixStack matrixStack) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		assert this.minecraft != null;
		this.minecraft.getTextureManager().bind(GUI_TEXTURE);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	public void onClose() {
		assert this.minecraft != null;
		this.minecraft.setScreen(lastScreen);
	}
	
}
