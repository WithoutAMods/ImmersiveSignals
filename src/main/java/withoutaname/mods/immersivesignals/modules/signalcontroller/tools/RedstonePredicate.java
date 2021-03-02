package withoutaname.mods.immersivesignals.modules.signalcontroller.tools;

import net.minecraft.util.Direction;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.PredicateAdapterTile;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.redstone.RedstoneAdapterTile;

public class RedstonePredicate extends BasePredicate<RedstonePredicate> {

	private Direction side;
	private int power;

	@Override
	public boolean test(PredicateAdapterTile<RedstonePredicate> tile) {
		if (tile instanceof RedstoneAdapterTile) {
			return power == ((RedstoneAdapterTile) tile).getPowerOnSide(side);
		}
		return false;
	}

}
