package withoutaname.mods.immersivesignals.modules.signalcontroller.tools;

import net.minecraft.client.gui.widget.Widget;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.PredicateWidget;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.ScreenPredicateWidget;

import java.util.function.Consumer;

public abstract class ScreenPredicate<T extends ScreenPredicate<T>> extends BasePredicate<T> {

	@OnlyIn(Dist.CLIENT)
	@Override
	public PredicateWidget createWidget(Consumer<Widget> buttonConsumer, int x, int y) {
		return new ScreenPredicateWidget(this, x, y);
	}

	@OnlyIn(Dist.CLIENT)
	public abstract void openScreen();
}
