package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.BaseAdapterBlock;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.BasePredicate;

import javax.annotation.Nonnull;

public abstract class PredicateAdapterBlock<T extends BasePredicate<T>> extends BaseAdapterBlock {

	@SuppressWarnings("deprecation")
	@Override
	@Nonnull
	public ActionResultType use(@Nonnull BlockState state, World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult trace) {
		if (!world.isClientSide) {
			INamedContainerProvider containerProvider = new INamedContainerProvider() {
				@Override
				public ITextComponent getDisplayName() {
					return new TranslationTextComponent("screen.immersivesignals.predicate_adapter");
				}
				@Override
				public Container createMenu(int i, @Nonnull PlayerInventory playerInventory, @Nonnull PlayerEntity playerEntity) {
					return createContainer(i, world, pos, player);
				}
			};
			NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, pos);
		}
		return ActionResultType.SUCCESS;
	}

	protected abstract PredicateAdapterContainer<T> createContainer(int id, World world, @Nonnull BlockPos pos, PlayerEntity player);

}
