package eu.withoutaname.mod.immersivesignals.signal.util

import eu.withoutaname.mod.immersivesignals.signal.util.part.Mast
import java.lang.Float.min
import kotlin.math.ceil

data class SignalConfiguration(val rotation: Int, val height: Float) {
    companion object {
        const val ALLOWED_OFFSTAND = .25f
    }

    val blockConfigurations by lazy {
        mutableListOf<SignalBlockConfiguration>().apply {
            val last = ceil(height - ALLOWED_OFFSTAND).toInt() - 1
            for (i in 0..last) {
                add(
                    SignalBlockConfiguration(
                        rotation,
                        listOf(
                            Mast(
                                0f,
                                if (i == last) min(1f + ALLOWED_OFFSTAND, height - i) else 1f,
                                i == 0,
                                i == last
                            )
                        )
                    )
                )
            }
        }.toList()
    }
}
