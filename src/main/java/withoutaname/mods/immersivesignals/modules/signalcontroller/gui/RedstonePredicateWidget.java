package withoutaname.mods.immersivesignals.modules.signalcontroller.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.fmlclient.gui.widget.Slider;
import withoutaname.mods.immersivesignals.datagen.Language;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.BasePredicate;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.RedstonePredicate;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RedstonePredicateWidget extends PredicateWidget {
	
	private final Consumer<AbstractWidget> buttonConsumer;
	private final List<AbstractWidget> widgets = new ArrayList<>();
	private final Button northButton;
	private final Button eastButton;
	private final Button southButton;
	private final Button westButton;
	private final Slider powerSlider;
	private RedstonePredicate redstonePredicate;
	
	public RedstonePredicateWidget(@Nonnull RedstonePredicate redstonePredicate, Consumer<AbstractWidget> buttonConsumer, int x, int y) {
		super(x, y, TextComponent.EMPTY);
		this.redstonePredicate = redstonePredicate;
		this.buttonConsumer = buttonConsumer;
		
		String s = Language.SCREEN + ".redstone_predicate.";
		northButton = addButton(new Button(x, y, 20, 20, new TranslatableComponent(s + "north"), p_onPress_1_ -> setSide(Direction.NORTH)));
		eastButton = addButton(new Button(x + 20, y, 20, 20, new TranslatableComponent(s + "east"), p_onPress_1_ -> setSide(Direction.EAST)));
		southButton = addButton(new Button(x + 40, y, 20, 20, new TranslatableComponent(s + "south"), p_onPress_1_ -> setSide(Direction.SOUTH)));
		westButton = addButton(new Button(x + 60, y, 20, 20, new TranslatableComponent(s + "west"), p_onPress_1_ -> setSide(Direction.WEST)));
		updateButtons();
		
		powerSlider = (Slider) addButton(new Slider(x + 84, y, 80, 20, new TranslatableComponent(s + "power").append(": "), TextComponent.EMPTY,
				0, 15, redstonePredicate.getPower(), false, true,
				button -> {}, slider -> getRedstonePredicate().setPower(slider.getValueInt())));
	}
	
	private Button addButton(Button button) {
		buttonConsumer.accept(button);
		widgets.add(button);
		return button;
	}
	
	private void setSide(Direction side) {
		redstonePredicate.setSide(side);
		updateButtons();
	}
	
	@Override
	public void setVisible(boolean visible) {
		northButton.visible = visible;
		eastButton.visible = visible;
		southButton.visible = visible;
		westButton.visible = visible;
		powerSlider.visible = visible;
	}
	
	@Override
	public void setPredicate(BasePredicate<?> predicate) {
		redstonePredicate = (RedstonePredicate) predicate;
		updateButtons();
		powerSlider.setValue(redstonePredicate.getPower());
		powerSlider.updateSlider();
	}
	
	public RedstonePredicate getRedstonePredicate() {
		return redstonePredicate;
	}
	
	private void updateButtons() {
		northButton.active = redstonePredicate.getSide() != Direction.NORTH;
		eastButton.active = redstonePredicate.getSide() != Direction.EAST;
		southButton.active = redstonePredicate.getSide() != Direction.SOUTH;
		westButton.active = redstonePredicate.getSide() != Direction.WEST;
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		powerSlider.dragging = false;
		return super.mouseReleased(mouseX, mouseY, button);
	}
	
	@Override
	public void render(@Nonnull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {}
	
}
