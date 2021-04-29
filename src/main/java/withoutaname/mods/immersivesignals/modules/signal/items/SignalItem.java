package withoutaname.mods.immersivesignals.modules.signal.items;

import javax.annotation.Nonnull;

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
				.stacksTo(64)
				.tab(ModSetup.defaultItemGroup));
	}
	
	@Nonnull
	@Override
	public ActionResultType useOn(ItemUseContext context) {
		World world = context.getLevel();
		if (!world.isClientSide) {
			BlockPos pos = world.getBlockState(context.getClickedPos()).getMaterial().isReplaceable() ? context.getClickedPos() : context.getClickedPos().relative(context.getClickedFace());
			Direction facing = context.getHorizontalDirection().getOpposite();
			int mainHeight = 5;
			boolean withZS3 = true;
			boolean withZSV = true;
			if (BaseSignalBlock.createSignal(world, pos, facing, mainHeight, withZS3, withZSV)) {
				context.getItemInHand().shrink(1);
				return ActionResultType.SUCCESS;
			}
		}
		return super.useOn(context);
	}
	
}
