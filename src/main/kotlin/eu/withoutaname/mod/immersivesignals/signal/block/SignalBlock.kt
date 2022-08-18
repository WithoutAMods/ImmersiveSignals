package eu.withoutaname.mod.immersivesignals.signal.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.world.IBlockReader

object SignalBlock : Block(Properties.of(Material.METAL).sound(SoundType.METAL).strength(1.5f, 6.0f)) {
    override fun createTileEntity(state: BlockState?, world: IBlockReader?) = SignalEntity()
    override fun hasTileEntity(state: BlockState?) = true
}
