package eu.withoutaname.mod.immersivesignals.signal.block

import eu.withoutaname.mod.immersivesignals.setup.Registration
import eu.withoutaname.mod.immersivesignals.signal.util.SignalConfiguration
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import net.minecraftforge.common.util.Constants.BlockFlags

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

    override fun asItem() = Registration.signalItem

    fun createSignal(world: World, pos: BlockPos, config: SignalConfiguration): Boolean {
        check(world.isClientSide.not()) { "Signal must be created on the server" }
        for (i in config.blockConfigurations.indices) {
            if (world.getBlockState(pos.above(i)).material.isReplaceable.not()) return false
        }
        for ((index, value) in config.blockConfigurations.withIndex()) {
            val state = Registration.signalBlock.defaultBlockState()
            val blockPos = pos.above(index)
            world.setBlock(blockPos, state, 0)
            val entity = world.getBlockEntity(blockPos)
            if (entity is SignalEntity) {
                entity.signalConfiguration = value
                world.sendBlockUpdated(blockPos, state, state, BlockFlags.DEFAULT)
            }
        }
        return true
    }
}
