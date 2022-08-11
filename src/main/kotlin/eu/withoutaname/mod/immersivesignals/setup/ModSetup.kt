package eu.withoutaname.mod.immersivesignals.setup

import eu.withoutaname.mod.immersivesignals.ImmersiveSignals
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent

object ModSetup {

    val DEFAULT_CREATIVE_TAB: ItemGroup = object : ItemGroup(ImmersiveSignals.ID) {
        override fun makeIcon(): ItemStack {
            return ItemStack(Items.BARRIER)
        }
    }
    val DEFAULT_ITEM_PROPERTIES: Item.Properties = Item.Properties().tab(DEFAULT_CREATIVE_TAB)

    fun init(event: FMLCommonSetupEvent) {
    }
}