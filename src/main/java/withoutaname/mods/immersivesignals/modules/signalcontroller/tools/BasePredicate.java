package withoutaname.mods.immersivesignals.modules.signalcontroller.tools;

import net.minecraft.client.gui.widget.Widget;
import net.minecraft.nbt.INBT;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.PredicateAdapterTile;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.PredicateWidget;

import java.util.function.Consumer;

public abstract class BasePredicate<T extends BasePredicate<T>> {

	public abstract boolean test(PredicateAdapterTile<T> tile);

	public static BasePredicate<?> fromInt(int i) {
		switch (i & 0xf) {
			case 0:
				return RedstonePredicate.fromInt(i);
		}
		return null;
	}

	public abstract T fromNBT(INBT inbt);

	public abstract int toInt();

	public abstract INBT toNBT();

	public abstract PredicateWidget createWidget(Consumer<Widget> buttonConsumer, int x, int y);

}
