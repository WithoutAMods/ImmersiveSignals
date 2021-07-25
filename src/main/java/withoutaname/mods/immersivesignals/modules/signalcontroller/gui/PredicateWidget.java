package withoutaname.mods.immersivesignals.modules.signalcontroller.gui;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.BasePredicate;

import javax.annotation.Nonnull;

public class PredicateWidget extends AbstractWidget {
	
	public PredicateWidget(int x, int y, Component title) {
		super(x, y, 164, 20, title);
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void setPredicate(BasePredicate<?> predicate) {}
	
	@Override
	public void updateNarration(@Nonnull NarrationElementOutput pNarrationElementOutput) {}
}
