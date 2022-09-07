package eu.withoutaname.mod.immersivesignals.setup

import eu.withoutaname.mod.immersivesignals.ImmersiveSignals
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack

object ModSetup {

    val DEFAULT_CREATIVE_TAB: ItemGroup = object : ItemGroup(ImmersiveSignals.ID) {
        override fun makeIcon(): ItemStack {
            return ItemStack(Registration.signalItem)
        }
    }
    val DEFAULT_ITEM_PROPERTIES: Item.Properties = Item.Properties().tab(DEFAULT_CREATIVE_TAB)
}
