package eu.withoutaname.mod.immersivesignals.nbt

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import net.minecraft.nbt.CollectionNBT
import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.INBT
import net.minecraft.nbt.NumberNBT
import net.minecraft.nbt.StringNBT

@Suppress("TooManyFunctions")
@OptIn(ExperimentalSerializationApi::class)
class NBTDecoder(private val nbt: INBT) : AbstractDecoder() {
    override val serializersModule: SerializersModule = EmptySerializersModule()

    private val keys = if (nbt is CompoundNBT) nbt.allKeys.iterator() else null

    private var current = nbt

    private var index = 0

    override fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder = NBTDecoder(current)

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        return when (nbt) {
            is CompoundNBT -> {
                if (keys!!.hasNext()) {
                    val key = keys.next()
                    current = nbt[key]!!
                    descriptor.getElementIndex(key)
                } else {
                    CompositeDecoder.DECODE_DONE
                }
            }

            is CollectionNBT<*> -> {
                if (index == nbt.size) return CompositeDecoder.DECODE_DONE
                current = nbt[index]
                index++
            }

            else -> throw UnsupportedOperationException()
        }
    }

    override fun decodeBoolean(): Boolean = (current as NumberNBT).asInt != 0
    override fun decodeByte(): Byte = (current as NumberNBT).asByte
    override fun decodeChar(): Char = (current as StringNBT).asString.first()
    override fun decodeDouble(): Double = (current as NumberNBT).asDouble
    override fun decodeFloat(): Float = (current as NumberNBT).asFloat
    override fun decodeInt(): Int = (current as NumberNBT).asInt
    override fun decodeLong(): Long = (current as NumberNBT).asLong
    override fun decodeShort(): Short = (current as NumberNBT).asShort
    override fun decodeString(): String = (current as StringNBT).asString
    override fun decodeEnum(enumDescriptor: SerialDescriptor): Int =
        enumDescriptor.getElementIndex((current as StringNBT).asString)
}
