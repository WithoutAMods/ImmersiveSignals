package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.controller;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SignalControllerBlock extends BaseEntityBlock {
	
	public SignalControllerBlock() {
		super(Properties.of(Material.METAL)
				.sound(SoundType.METAL)
				.strength(1.5F, 6.0F));
	}
	
	@Override
	public RenderShape getRenderShape(BlockState pState) {
		return RenderShape.MODEL;
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
		return new SignalControllerEntity(pos, state);
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@Nonnull Level pLevel, @Nonnull BlockState pState, @Nonnull BlockEntityType<T> pEntityType) {
		return pLevel.isClientSide ? null : createTickerHelper(pEntityType, SignalControllerRegistration.SIGNAL_CONTROLLER_ENTITY.get(),
				(level, pos, blockState, entity) -> entity.tick());
	}
	
	@Override
	@Nonnull
	public InteractionResult use(@Nonnull BlockState pState, @Nonnull Level pLevel, @Nonnull BlockPos pPos, @Nonnull Player pPlayer, @Nonnull InteractionHand pHand, BlockHitResult pHit) {
		if (!pLevel.isClientSide) {
			MenuProvider containerProvider = new SimpleMenuProvider(
					(containerId, inventory, player) -> new SignalControllerContainer(containerId, pLevel, pPos),
					new TranslatableComponent("screen.immersivesignals.signal_controller"));
			NetworkHooks.openGui((ServerPlayer) pPlayer, containerProvider, pPos);
		}
		return InteractionResult.SUCCESS;
	}
	
}
