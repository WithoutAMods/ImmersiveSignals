package eu.withoutaname.mod.immersivesignals.util

import com.google.common.collect.ImmutableList
import net.minecraft.client.renderer.model.BakedQuad
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.vertex.VertexFormatElement
import net.minecraft.util.Direction
import net.minecraft.util.math.vector.Vector3f
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder
import kotlin.math.max
import kotlin.math.min

private val halfVector3f = v(.5f, .5f, .5f)

/**
 * @return Vector3f(x, y, z)
 */
fun v(x: Float, y: Float, z: Float) = Vector3f(x, y, z)

@Suppress("LongParameterList")
fun putVertex(
    builder: BakedQuadBuilder,
    normal: Vector3f,
    vec: Vector3f,
    u: Double,
    v: Double,
    sprite: TextureAtlasSprite,
    r: Float = 1f,
    g: Float = 1f,
    b: Float = 1f
) {
    val elements: ImmutableList<VertexFormatElement> = builder.vertexFormat.elements.asList()
    for (j in 0 until elements.size) {
        val e: VertexFormatElement = elements[j]
        when (e.usage) {
            VertexFormatElement.Usage.POSITION -> builder.put(j, vec.x(), vec.y(), vec.z(), 1.0f)
            VertexFormatElement.Usage.COLOR -> builder.put(j, r, g, b, 1.0f)
            VertexFormatElement.Usage.UV -> when (e.index) {
                0 -> {
                    val iu = sprite.getU(u)
                    val iv = sprite.getV(v)
                    builder.put(j, iu, iv)
                }

                2 -> builder.put(j, 0f, 0f)
                else -> builder.put(j)
            }

            VertexFormatElement.Usage.NORMAL -> builder.put(j, normal.x(), normal.y(), normal.z())
            else -> builder.put(j)
        }
    }
}

@Suppress("LongParameterList")
fun createQuad(
    topLeft: Vector3f,
    bottomLeft: Vector3f,
    bottomRight: Vector3f,
    topRight: Vector3f,
    rotation: Int,
    sprite: TextureAtlasSprite,
    uFrom: Double = 0.0,
    uTo: Double = sprite.width.toDouble(),
    vFrom: Double = 0.0,
    vTo: Double = sprite.height.toDouble(),
    r: Float = 1f,
    g: Float = 1f,
    b: Float = 1f
): BakedQuad {
    val quaternion = Vector3f.YP.rotation(rotation / 16f * 2 * Math.PI.toFloat())
    val block: (Vector3f).() -> Unit = {
        sub(halfVector3f)
        transform(quaternion)
        add(halfVector3f)
    }
    return createQuad(
        topLeft.copy().apply(block),
        bottomLeft.copy().apply(block),
        bottomRight.copy().apply(block),
        topRight.copy().apply(block),
        sprite,
        uFrom,
        uTo,
        vFrom,
        vTo,
        r,
        g,
        b
    )
}

@Suppress("LongParameterList")
fun createQuad(
    topLeft: Vector3f,
    bottomLeft: Vector3f,
    bottomRight: Vector3f,
    topRight: Vector3f,
    sprite: TextureAtlasSprite,
    uFrom: Double = 0.0,
    uTo: Double = sprite.width.toDouble(),
    vFrom: Double = 0.0,
    vTo: Double = sprite.height.toDouble(),
    r: Float = 1f,
    g: Float = 1f,
    b: Float = 1f
): BakedQuad {
    val normal = bottomRight.copy().apply {
        sub(bottomLeft)
        cross(topLeft.copy().apply { sub(bottomLeft) })
        normalize()
    }
    val builder = BakedQuadBuilder(sprite)
    builder.setQuadOrientation(Direction.getNearest(normal.x(), normal.y(), normal.z()))
    putVertex(builder, normal, topLeft, uFrom, vFrom, sprite, r, g, b)
    putVertex(builder, normal, bottomLeft, uFrom, vTo, sprite, r, g, b)
    putVertex(builder, normal, bottomRight, uTo, vTo, sprite, r, g, b)
    putVertex(builder, normal, topRight, uTo, vFrom, sprite, r, g, b)
    return builder.build()
}

@Suppress("LongParameterList")
fun createCube(
    side: Direction?,
    from: Vector3f,
    to: Vector3f,
    rotation: Int,
    texture: TextureAtlasSprite,
    r: Float = 1f,
    g: Float = 1f,
    b: Float = 1f,
    dynamicUV: Boolean = true
) = createCube(side, from, to, rotation, texture, texture, texture, texture, texture, texture, r, g, b, dynamicUV)

@Suppress("LongParameterList")
fun createCube(
    side: Direction?,
    from: Vector3f,
    to: Vector3f,
    rotation: Int,
    up: TextureAtlasSprite,
    down: TextureAtlasSprite,
    north: TextureAtlasSprite,
    south: TextureAtlasSprite,
    east: TextureAtlasSprite,
    west: TextureAtlasSprite,
    r: Float = 1f,
    g: Float = 1f,
    b: Float = 1f,
    dynamicUV: Boolean = true
) = mutableListOf<BakedQuad>().apply {
    val fx = min(from.x(), to.x())
    val fy = min(from.y(), to.y())
    val fz = min(from.z(), to.z())
    val tx = max(from.x(), to.x())
    val ty = max(from.y(), to.y())
    val tz = max(from.z(), to.z())

    val fff = v(fx, fy, fz)
    val fft = v(fx, fy, tz)
    val ftf = v(fx, ty, fz)
    val ftt = v(fx, ty, tz)
    val tff = v(tx, fy, fz)
    val tft = v(tx, fy, tz)
    val ttf = v(tx, ty, fz)
    val ttt = v(tx, ty, tz)

    val ffx = getFrom(dynamicUV, fx)
    val ftx = getFrom(dynamicUV, 1f - tx)
    val tfx = getTo(dynamicUV, 1f - fx)
    val ttx = getTo(dynamicUV, tx)
    val fty = getFrom(dynamicUV, 1f - ty)
    val tfy = getTo(dynamicUV, 1f - fy)
    val ffz = getFrom(dynamicUV, fz)
    val ftz = getFrom(dynamicUV, 1f - tz)
    val tfz = getTo(dynamicUV, 1f - fz)
    val ttz = getTo(dynamicUV, tz)

    if (side == if (ty < 1) null else Direction.UP)
        add(createQuad(ftf, ftt, ttt, ttf, rotation, up, ffx, ttx, ffz, ttz, r, g, b))
    if (side == if (fy > 0) null else Direction.DOWN)
        add(createQuad(tff, tft, fft, fff, rotation, down, ftx, tfx, ffz, ttz, r, g, b))
    if (side == if (fz > 0) null else Direction.NORTH)
        add(createQuad(ttf, tff, fff, ftf, rotation, north, ftx, tfx, fty, tfy, r, g, b))
    if (side == if (tz < 1) null else Direction.SOUTH)
        add(createQuad(ftt, fft, tft, ttt, rotation, south, ffx, ttx, fty, tfy, r, g, b))
    if (side == if (tx < 1) null else Direction.EAST)
        add(createQuad(ttt, tft, tff, ttf, rotation, east, ftz, tfz, fty, tfy, r, g, b))
    if (side == if (fx > 0) null else Direction.WEST)
        add(createQuad(ftf, fff, fft, ftt, rotation, west, ffz, ttz, fty, tfy, r, g, b))
}

private fun getFrom(dynamicUV: Boolean, d: Float): Double {
    return if (dynamicUV && 0 <= d && d <= 1) 16.0 * d else 0.0
}

private fun getTo(dynamicUV: Boolean, d: Float): Double {
    return if (dynamicUV && 0 <= d && d <= 1) 16.0 * d else 16.0
}
