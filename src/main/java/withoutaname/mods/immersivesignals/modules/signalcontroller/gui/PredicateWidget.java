package withoutaname.mods.immersivesignals.modules.signalcontroller.gui;

import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.BasePredicate;

public class PredicateWidget extends Widget {

	public PredicateWidget(int x, int y, ITextComponent title) {
		super(x, y, 164, 20, title);
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setPredicate(BasePredicate<?> predicate) {}

}
