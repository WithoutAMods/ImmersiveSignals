package eu.withoutaname.mod.immersivesignals.nbt

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.PolymorphicKind.OPEN
import kotlinx.serialization.descriptors.PolymorphicKind.SEALED
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind.CONTEXTUAL
import kotlinx.serialization.descriptors.SerialKind.ENUM
import kotlinx.serialization.descriptors.StructureKind.CLASS
import kotlinx.serialization.descriptors.StructureKind.LIST
import kotlinx.serialization.descriptors.StructureKind.MAP
import kotlinx.serialization.descriptors.StructureKind.OBJECT
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import net.minecraft.nbt.ByteNBT
import net.minecraft.nbt.DoubleNBT
import net.minecraft.nbt.FloatNBT
import net.minecraft.nbt.INBT
import net.minecraft.nbt.IntNBT
import net.minecraft.nbt.LongNBT
import net.minecraft.nbt.ShortNBT
import net.minecraft.nbt.StringNBT

@OptIn(ExperimentalSerializationApi::class)
class NBTEncoder : AbstractEncoder() {
    override val serializersModule: SerializersModule = EmptySerializersModule()

    lateinit var nbt: INBT
    private val structureBuilders = mutableListOf<NBTStructureBuilder>()
    private lateinit var name: String

    override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
        when (descriptor.kind) {
            is PrimitiveKind, ENUM -> {}
            CLASS, MAP, SEALED, OBJECT -> structureBuilders.add(NBTCompoundBuilder())
            LIST -> structureBuilders.add(NBTListBuilder())
            OPEN, CONTEXTUAL -> throw UnsupportedOperationException()
        }
        return this
    }

    override fun endStructure(descriptor: SerialDescriptor) {
        when (descriptor.kind) {
            is PrimitiveKind, ENUM -> {}
            else -> put(structureBuilders.removeLast().build())
        }
    }

    override fun encodeElement(descriptor: SerialDescriptor, index: Int): Boolean {
        val last = structureBuilders.last()
        if (last is NBTNamedStructureBuilder) last.encodeName(descriptor.getElementName(index))
        return true
    }

    private fun put(value: INBT) {
        if (structureBuilders.isEmpty()) {
            nbt = value
        } else {
            structureBuilders.last().encodeValue(value)
        }
    }

    override fun encodeBoolean(value: Boolean) = put(ByteNBT.valueOf(value))

    override fun encodeByte(value: Byte) = put(ByteNBT.valueOf(value))

    override fun encodeChar(value: Char) = put(StringNBT.valueOf(value.toString()))

    override fun encodeDouble(value: Double) = put(DoubleNBT.valueOf(value))

    override fun encodeFloat(value: Float) = put(FloatNBT.valueOf(value))

    override fun encodeInt(value: Int) = put(IntNBT.valueOf(value))

    override fun encodeLong(value: Long) = put(LongNBT.valueOf(value))

    override fun encodeShort(value: Short) = put(ShortNBT.valueOf(value))

    override fun encodeString(value: String) = put(StringNBT.valueOf(value))

    override fun encodeEnum(enumDescriptor: SerialDescriptor, index: Int) =
        put(StringNBT.valueOf(enumDescriptor.getElementName(index)))
}