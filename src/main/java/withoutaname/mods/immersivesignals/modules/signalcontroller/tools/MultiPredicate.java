package withoutaname.mods.immersivesignals.modules.signalcontroller.tools;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.PredicateAdapterTile;

import java.util.ArrayList;
import java.util.List;

public class MultiPredicate<T extends BasePredicate<T>> {

	private final List<T> predicates = new ArrayList<>();

	public boolean test(PredicateAdapterTile<T> tile) {
		for (T predicate : predicates) {
			if (predicate.test(tile)) {
				return true;
			}
		}
		return false;
	}

	public void addPredicate(BasePredicate<?> predicate) {
		try {
			predicates.add((T) predicate);
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
	}

	public List<T> getPredicates() {
		return predicates;
	}

	public static <T extends BasePredicate<T>> MultiPredicate<T> fromNBT(T instance, INBT inbt) {
		final MultiPredicate<T> multiPredicate = new MultiPredicate<>();
		if (inbt instanceof ListNBT) {
			for (INBT nbt : (ListNBT) inbt) {
				multiPredicate.addPredicate(instance.fromNBT(nbt));
			}
		}
		return multiPredicate;
	}

	public INBT toNBT() {
		final ListNBT nbt = new ListNBT();
		for (T predicate : predicates) {
			nbt.add(predicate.toNBT());
		}
		return nbt;
	}

}
