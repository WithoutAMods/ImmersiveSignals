package eu.withoutaname.mod.immersivesignals.nbt

import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.INBT
import net.minecraft.nbt.ListNBT

sealed class NBTStructureBuilder {
    abstract fun build(): INBT

    abstract fun encodeValue(value: INBT)
}

abstract class NBTNamedStructureBuilder : NBTStructureBuilder() {
    protected lateinit var name: String

    fun encodeName(name: String) {
        this.name = name
    }
}

class NBTCompoundBuilder : NBTNamedStructureBuilder() {
    private val nbt = CompoundNBT()

    override fun encodeValue(value: INBT) {
        nbt.put(name, value)
    }

    override fun build(): INBT = nbt
}

class NBTListBuilder : NBTStructureBuilder() {

    private val nbt = ListNBT()

    override fun encodeValue(value: INBT) {
        nbt.add(value)
    }

    override fun build(): INBT = nbt
}