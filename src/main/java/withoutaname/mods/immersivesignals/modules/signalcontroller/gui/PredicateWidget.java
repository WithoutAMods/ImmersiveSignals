package withoutaname.mods.immersivesignals.modules.signalcontroller.gui;

import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.StringTextComponent;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.BasePredicate;

public abstract class PredicateWidget extends Widget {

	public PredicateWidget(int x, int y) {
		super(x, y, 164, 20, StringTextComponent.EMPTY);
	}

	public abstract void setVisible(boolean visible);

	public abstract void setPredicate(BasePredicate<?> predicate);

}
