package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter;

import net.minecraft.tileentity.TileEntityType;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.BasePredicate;

public class PredicateAdapterTile<T extends BasePredicate<T>> extends BaseAdapterTile {

	public PredicateAdapterTile(TileEntityType<? extends PredicateAdapterTile> tileEntityType) {
		super(tileEntityType);
	}

}
