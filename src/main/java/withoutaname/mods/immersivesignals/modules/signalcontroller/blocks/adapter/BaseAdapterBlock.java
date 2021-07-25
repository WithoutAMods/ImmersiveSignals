package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nonnull;

public abstract class BaseAdapterBlock extends BaseEntityBlock {
	
	public BaseAdapterBlock() {
		super(Properties.of(Material.METAL)
				.sound(SoundType.METAL)
				.strength(1.5F, 6.0F));
	}
	
	@Override
	public RenderShape getRenderShape(BlockState pState) {
		return RenderShape.MODEL;
	}
	
	public void update(@Nonnull Level worldIn, BlockPos pos) {
		final BlockEntity te = worldIn.getBlockEntity(pos);
		if (te instanceof BaseAdapterEntity) {
			((BaseAdapterEntity) te).update();
		}
	}
	
}
