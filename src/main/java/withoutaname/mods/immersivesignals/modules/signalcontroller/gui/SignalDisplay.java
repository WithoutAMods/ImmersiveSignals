package withoutaname.mods.immersivesignals.modules.signalcontroller.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import withoutaname.mods.immersivesignals.ImmersiveSignals;
import withoutaname.mods.immersivesignals.modules.signal.blocks.BaseSignalBlock;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.controller.SignalControllerEntity;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.SignalPattern;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class SignalDisplay extends AbstractWidget {
	
	private final int mainX;
	private final int mainY;
	private final int size;
	private final boolean withZs3;
	private final boolean withZs3v;
	private final Supplier<SignalPattern> patternSupplier;
	
	public SignalDisplay(int mainX, int mainY, int size, boolean withZs3, boolean withZs3v, Supplier<SignalPattern> patternSupplier) {
		super(mainX, withZs3 ? mainY - 9 * size : mainY, 8 * size, ((withZs3 ? 9 : 0) + 16 + (withZs3v ? 9 : 0)) * size, TextComponent.EMPTY);
		this.mainX = mainX;
		this.mainY = mainY;
		this.size = size;
		this.withZs3 = withZs3;
		this.withZs3v = withZs3v;
		this.patternSupplier = patternSupplier;
	}
	
	@Override
	public void render(@Nonnull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		final long time = System.currentTimeMillis() / (SignalControllerEntity.BLINK_TIME * 50);
		final SignalPattern pattern = patternSupplier.get();
		
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		
		if (pattern != null) {
			if (withZs3) {
				RenderSystem.setShaderTexture(0, new ResourceLocation(ImmersiveSignals.MODID, "textures/custom/signal_zs3.png"));
				blit(matrixStack, mainX + size, mainY - 9 * size, 6 * size, 8 * size, pattern.getZs3() % 4 * 32, (float) (pattern.getZs3() / 4 * 32), 24, 32, 128, 128);
			}
			
			RenderSystem.setShaderTexture(0, new ResourceLocation(ImmersiveSignals.MODID, "textures/custom/signal_main.png"));
			blit(matrixStack, mainX, mainY, 8 * size, 16 * size, 128, 0, 128, 256, 256, 256);
			if (pattern.getMainPattern() == BaseSignalBlock.SignalMainPattern.HP0) {
				blit(matrixStack, mainX + 3 * size, mainY + 3 * size, 2 * size, 2 * size, 48, 48, 32, 32, 256, 256);
			} else if (pattern.getMainPattern() == BaseSignalBlock.SignalMainPattern.KS1 && (pattern.getZs3v() == 0 || time % 2 == 0)) {
				blit(matrixStack, mainX + size, mainY + 6 * size, 3 * size, 2 * size, 16, 96, 48, 32, 256, 256);
			} else if (pattern.getMainPattern() == BaseSignalBlock.SignalMainPattern.KS2) {
				blit(matrixStack, mainX + 4 * size, mainY + 6 * size, 3 * size, 2 * size, 64, 96, 48, 32, 256, 256);
			}
			if (pattern.isShortenedBrakingDistance() || pattern.isMarkerLight()) {
				blit(matrixStack, mainX + size, mainY + size, 2 * size, 2 * size, 16, 16, 32, 32, 256, 256);
			}
			if (pattern.isSh1()) {
				blit(matrixStack, mainX + 3 * size, mainY + 10 * size, 2 * size, 2 * size, 48, 160, 32, 32, 256, 256);
			}
			if (pattern.isApproachSignalRepeater() || pattern.isSh1() || (pattern.isZs1() && time % 2 == 0)) {
				blit(matrixStack, mainX, mainY + 12 * size, 2 * size, 2 * size, 0, 192, 32, 32, 256, 256);
			}
			if (pattern.isZs7()) {
				blit(matrixStack, mainX + size, mainY + 10 * size, 2 * size, 2 * size, 16, 160, 32, 32, 256, 256);
				blit(matrixStack, mainX + 5 * size, mainY + 10 * size, 2 * size, 2 * size, 80, 160, 32, 32, 256, 256);
				blit(matrixStack, mainX + 3 * size, mainY + 12 * size, 2 * size, 2 * size, 48, 192, 32, 32, 256, 256);
			}
			
			if (withZs3v) {
				RenderSystem.setShaderTexture(0, new ResourceLocation(ImmersiveSignals.MODID, "textures/custom/signal_zs3v.png"));
				blit(matrixStack, mainX + size, mainY + 17 * size, 6 * size, 8 * size, pattern.getZs3v() % 4 * 32, (float) (pattern.getZs3v() / 4 * 32), 24, 32, 128, 128);
			}
		}
	}
	
	@Override
	public void updateNarration(@Nonnull NarrationElementOutput pNarrationElementOutput) {}
}
