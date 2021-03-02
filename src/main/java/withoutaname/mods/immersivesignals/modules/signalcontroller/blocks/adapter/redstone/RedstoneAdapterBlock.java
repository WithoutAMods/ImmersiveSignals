package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.redstone;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.BaseAdapterBlock;

public class RedstoneAdapterBlock extends BaseAdapterBlock {

	public RedstoneAdapterBlock() {
		super();
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new RedstoneAdapterTile();
	}

	@Override
	public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, @Nullable Direction side) {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	@NotNull
	public ActionResultType onBlockActivated(@NotNull BlockState state, World world, @NotNull BlockPos pos, @NotNull PlayerEntity player, @NotNull Hand hand, @NotNull BlockRayTraceResult trace) {
		if (!world.isRemote) {
			INamedContainerProvider containerProvider = new INamedContainerProvider() {
				@Override
				public ITextComponent getDisplayName() {
					return new TranslationTextComponent("screen.immersivesignals.redstone_adapter");
				}
				@Override
				public Container createMenu(int i, @NotNull PlayerInventory playerInventory, @NotNull PlayerEntity playerEntity) {
					return new RedstoneAdapterContainer(i, world, pos);
				}
			};
			NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, pos);
		}
		return ActionResultType.SUCCESS;
	}

}
