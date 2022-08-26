package eu.withoutaname.mod.immersivesignals.signal.util

import kotlin.math.ceil
import kotlin.math.min

data class SignalConfiguration(val height: Float) {
    companion object {
        const val ALLOWED_OFFSTAND = .25f
    }

    val blockConfigurations = mutableListOf<SignalBlockConfiguration>().apply {
        for (i in 0 until ceil(height - ALLOWED_OFFSTAND).toInt()) {
            add(
                SignalBlockConfiguration(
                    min(1f + ALLOWED_OFFSTAND, height - i)
                )
            )
        }
    }.toList()
}
