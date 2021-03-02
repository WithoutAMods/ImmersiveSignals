package withoutaname.mods.immersivesignals.modules.signalcontroller.tools;

import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.PredicateAdapterTile;

public abstract class BasePredicate<T extends BasePredicate<T>> {

	public abstract boolean test(PredicateAdapterTile<T> tile);

}
