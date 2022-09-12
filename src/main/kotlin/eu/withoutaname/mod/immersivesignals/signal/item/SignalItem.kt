package eu.withoutaname.mod.immersivesignals.signal.item

import eu.withoutaname.mod.immersivesignals.setup.ModSetup
import eu.withoutaname.mod.immersivesignals.signal.block.SignalBlock
import eu.withoutaname.mod.immersivesignals.signal.util.SignalConfiguration
import net.minecraft.item.Item
import net.minecraft.item.ItemUseContext
import net.minecraft.util.ActionResultType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import kotlin.math.round

object SignalItem : Item(ModSetup.DEFAULT_ITEM_PROPERTIES.stacksTo(1)) {
    override fun useOn(pContext: ItemUseContext): ActionResultType {
        val level: World = pContext.level
        if (!level.isClientSide) {
            val pos: BlockPos = if (level.getBlockState(pContext.clickedPos).material
                .isReplaceable
            ) pContext.clickedPos else pContext.clickedPos.relative(pContext.clickedFace)
            val rotation: Int = (round((180 - pContext.rotation) * 16.0f / 360.0f).toInt() + 16) % 16
            if (SignalBlock.createSignal(level, pos, SignalConfiguration(rotation, 5.5f))) {
                pContext.itemInHand.shrink(1)
                return ActionResultType.SUCCESS
            }
        }
        return super.useOn(pContext)
    }
}
