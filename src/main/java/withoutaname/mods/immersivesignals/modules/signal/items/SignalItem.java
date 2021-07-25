package withoutaname.mods.immersivesignals.modules.signal.items;

import javax.annotation.Nonnull;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import withoutaname.mods.immersivesignals.modules.signal.blocks.BaseSignalBlock;
import withoutaname.mods.immersivesignals.setup.ModSetup;

public class SignalItem extends Item {
	
	public static final String REGISTRY_NAME = "signal";
	
	public SignalItem() {
		super(new Item.Properties()
				.stacksTo(64)
				.tab(ModSetup.DEFAULT_CREATIVE_TAB));
	}
	
	@Nonnull
	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		if (!level.isClientSide) {
			BlockPos pos = level.getBlockState(context.getClickedPos()).getMaterial().isReplaceable() ? context.getClickedPos() : context.getClickedPos().relative(context.getClickedFace());
			int rotation = Mth.floor((context.getRotation() * 16.0F / 360.0F) + 0.5F) & 15;
			if (BaseSignalBlock.createSignal(level, pos, rotation, 5, true, true)) {
				context.getItemInHand().shrink(1);
				return InteractionResult.SUCCESS;
			}
		}
		return super.useOn(context);
	}
	
}
