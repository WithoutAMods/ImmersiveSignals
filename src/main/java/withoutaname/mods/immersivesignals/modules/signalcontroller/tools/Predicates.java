package withoutaname.mods.immersivesignals.modules.signalcontroller.tools;

import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.PredicateAdapterTile;

import java.util.ArrayList;
import java.util.List;

public class Predicates<T extends BasePredicate<T>> {

	private final List<T> predicates = new ArrayList<>();

	public boolean test(PredicateAdapterTile<T> tile) {
		for (T predicate : predicates) {
			if (predicate.test(tile)) {
				return true;
			}
		}
		return false;
	}

	public void addPredicate(T predicate) {
		predicates.add(predicate);
	}

	public List<T> getPredicates() {
		return predicates;
	}
}
