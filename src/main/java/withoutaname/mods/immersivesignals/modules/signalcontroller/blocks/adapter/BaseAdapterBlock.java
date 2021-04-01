package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class BaseAdapterBlock extends Block {

	public BaseAdapterBlock() {
		super(Properties.create(Material.IRON)
				.sound(SoundType.METAL)
				.hardnessAndResistance(1.5F, 6.0F));
	}

	@Nullable
	@Override
	public abstract TileEntity createTileEntity(BlockState state, IBlockReader world);

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	public void update(World worldIn, BlockPos pos) {
		final TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof BaseAdapterTile) {
			((BaseAdapterTile) te).update();
		}
	}

}
