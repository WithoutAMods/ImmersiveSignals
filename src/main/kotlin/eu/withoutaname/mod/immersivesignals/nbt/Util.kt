package eu.withoutaname.mod.immersivesignals.nbt

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.serializer
import net.minecraft.nbt.CompoundNBT
import net.minecraft.nbt.INBT

inline fun <reified T> encodeNBT(value: T) = encodeNBT(serializer(), value)

fun <T> encodeNBT(serializer: SerializationStrategy<T>, value: T): INBT {
    val encoder = NBTEncoder()
    encoder.encodeSerializableValue(serializer, value)
    return encoder.nbt
}

inline fun <reified T> decodeNBT(nbt: INBT): T = decodeNBT(nbt, serializer())

fun <T> decodeNBT(nbt: INBT, deserializer: DeserializationStrategy<T>): T {
    val decoder = NBTDecoder(nbt)
    return decoder.decodeSerializableValue(deserializer)
}

inline fun <reified T> CompoundNBT.putSerializable(key: String, value: T) = put(key, encodeNBT(value))
inline fun <reified T> CompoundNBT.getSerializable(key: String): T? = get(key)?.let { decodeNBT(it) }