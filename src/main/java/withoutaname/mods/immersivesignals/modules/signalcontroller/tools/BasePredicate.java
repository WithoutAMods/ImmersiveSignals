package withoutaname.mods.immersivesignals.modules.signalcontroller.tools;

import net.minecraft.nbt.INBT;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.PredicateAdapterTile;

public abstract class BasePredicate<T extends BasePredicate<T>> {

	public abstract boolean test(PredicateAdapterTile<T> tile);

	public abstract T fromNBT(INBT inbt);

	public abstract INBT toNBT();

}
