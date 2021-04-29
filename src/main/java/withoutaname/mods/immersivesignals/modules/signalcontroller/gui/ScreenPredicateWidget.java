package withoutaname.mods.immersivesignals.modules.signalcontroller.gui;

import net.minecraft.util.text.StringTextComponent;

import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.BasePredicate;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.ScreenPredicate;

public class ScreenPredicateWidget extends PredicateWidget {
	
	private ScreenPredicate<?> predicate;
	
	public ScreenPredicateWidget(ScreenPredicate<?> predicate, int x, int y) {
		super(x, y, new StringTextComponent("Modify Predicate"));
		this.predicate = predicate;
	}
	
	@Override
	public void setPredicate(BasePredicate<?> predicate) {
		this.predicate = (ScreenPredicate<?>) predicate;
	}
	
	@Override
	public void onClick(double mouseX, double mouseY) {
		predicate.openScreen();
	}
	
}
