package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public abstract class BaseAdapterBlock extends Block {
	
	public BaseAdapterBlock() {
		super(Properties.of(Material.METAL)
				.sound(SoundType.METAL)
				.strength(1.5F, 6.0F));
	}
	
	@Nullable
	@Override
	public abstract TileEntity createTileEntity(BlockState state, IBlockReader world);
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	public void update(@Nonnull World worldIn, BlockPos pos) {
		final TileEntity te = worldIn.getBlockEntity(pos);
		if (te instanceof BaseAdapterTile) {
			((BaseAdapterTile) te).update();
		}
	}
	
}
