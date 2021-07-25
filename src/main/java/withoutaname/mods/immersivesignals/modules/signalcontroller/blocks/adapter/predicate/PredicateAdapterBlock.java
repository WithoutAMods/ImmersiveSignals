package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.BaseAdapterBlock;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.BasePredicate;

public abstract class PredicateAdapterBlock<T extends BasePredicate<T>> extends BaseAdapterBlock {
	
	@SuppressWarnings("deprecation")
	@Override
	@Nonnull
	public InteractionResult use(@Nonnull BlockState state, Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult trace) {
		if (!level.isClientSide) {
			SimpleMenuProvider menuProvider = new SimpleMenuProvider(new MenuConstructor() {
				@Nullable
				@Override
				public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
					return createContainer(pContainerId, level, pos, player);
				}
			}, new TranslatableComponent("screen.immersivesignals.predicate_adapter"));
			NetworkHooks.openGui((ServerPlayer) player, menuProvider, pos);
		}
		return InteractionResult.SUCCESS;
	}
	
	protected abstract PredicateAdapterContainer<T> createContainer(int id, Level level, @Nonnull BlockPos pos, Player player);
	
}
