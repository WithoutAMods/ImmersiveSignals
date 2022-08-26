package eu.withoutaname.mod.immersivesignals.signal.util

import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.INBT

data class SignalBlockConfiguration(val mastHeight: Float = 1f) {
    fun toNBT() = CompoundNBT().apply {
        putFloat("mastHeight", mastHeight)
    }

    companion object {
        fun fromNBT(nbt: INBT?) =
            if (nbt !is CompoundNBT) SignalBlockConfiguration()
            else SignalBlockConfiguration(nbt.getFloat("mastHeight"))
    }
}
