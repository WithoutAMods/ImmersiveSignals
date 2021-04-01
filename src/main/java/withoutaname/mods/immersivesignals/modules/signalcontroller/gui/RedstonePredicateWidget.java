package withoutaname.mods.immersivesignals.modules.signalcontroller.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.Direction;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.widget.Slider;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.BasePredicate;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.RedstonePredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RedstonePredicateWidget extends PredicateWidget {

	private RedstonePredicate redstonePredicate;
	private final Consumer<Widget> buttonConsumer;

	private final List<Widget> widgets = new ArrayList<>();
	private final Button northButton;
	private final Button eastButton;
	private final Button southButton;
	private final Button westButton;
	private final Slider powerSlider;

	public RedstonePredicateWidget(RedstonePredicate redstonePredicate, Consumer<Widget> buttonConsumer, int x, int y) {
		super(x, y, StringTextComponent.EMPTY);
		this.redstonePredicate = redstonePredicate;
		this.buttonConsumer = buttonConsumer;

		northButton = addButton(new Button(x, y, 20, 20, new StringTextComponent("N"), p_onPress_1_ -> setSide(Direction.NORTH)));
		eastButton = addButton(new Button(x + 20, y, 20, 20, new StringTextComponent("E"), p_onPress_1_ -> setSide(Direction.EAST)));
		southButton = addButton(new Button(x + 40, y, 20, 20, new StringTextComponent("S"), p_onPress_1_ -> setSide(Direction.SOUTH)));
		westButton = addButton(new Button(x + 60, y, 20, 20, new StringTextComponent("W"), p_onPress_1_ -> setSide(Direction.WEST)));
		updateButtons();

		powerSlider = (Slider) addButton(new Slider(x + 84, y, 80, 20, new StringTextComponent("Power: "), StringTextComponent.EMPTY,
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
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
	}
}
