package withoutaname.mods.immersivesignals.modules.signal.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import withoutaname.mods.immersivesignals.modules.signal.blocks.BaseSignalBlock;
import withoutaname.mods.immersivesignals.setup.ModSetup;

public class SignalItem extends Item {

	public static final String REGISTRY_NAME = "signal";

	public SignalItem() {
		super(new Item.Properties()
				.maxStackSize(64)
				.group(ModSetup.defaultItemGroup));
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		if(!world.isRemote) {
			BlockPos pos = world.getBlockState(context.getPos()).getMaterial().isReplaceable() ? context.getPos() : context.getPos().offset(context.getFace());
			Direction facing = context.getPlacementHorizontalFacing().getOpposite();
			int mainHeight = 5;
			boolean withZS3 = true;
			boolean withZSV = true;
			if (BaseSignalBlock.createSignal(world, pos, facing, mainHeight, withZS3, withZSV)) {
				context.getItem().shrink(1);
				return ActionResultType.SUCCESS;
			}
		}
		return super.onItemUse(context);
	}

}
