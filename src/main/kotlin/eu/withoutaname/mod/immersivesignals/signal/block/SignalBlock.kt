package eu.withoutaname.mod.immersivesignals.signal.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.world.IBlockReader

object SignalBlock : Block(Properties.of(Material.METAL).sound(SoundType.METAL).strength(1.5f, 6.0f)) {
    override fun createTileEntity(state: BlockState?, world: IBlockReader?) = SignalEntity()
    override fun hasTileEntity(state: BlockState?) = true

    override fun getShape(
        pState: BlockState,
        pLevel: IBlockReader,
        pPos: BlockPos,
        pContext: ISelectionContext
    ): VoxelShape = VoxelShapes.box(.4, 0.0, .4, .6, 1.0, .6)

    override fun getVisualShape(
        pState: BlockState,
        pReader: IBlockReader,
        pPos: BlockPos,
        pContext: ISelectionContext
    ): VoxelShape = VoxelShapes.box(.4, -.5, .4, .6, 1.5, .6)
}
