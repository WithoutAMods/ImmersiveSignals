package eu.withoutaname.mod.immersivesignals.util

import com.google.common.collect.ImmutableList
import net.minecraft.client.renderer.model.BakedQuad
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.vertex.VertexFormatElement
import net.minecraft.util.Direction
import net.minecraft.util.math.vector.Vector3f
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder


private val halfVector3f = v(.5f, .5f, .5f)

/**
 * @return Vector3f(x, y, z)
 */
fun v(x: Float, y: Float, z: Float) = Vector3f(x, y, z)

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

@Suppress("LongMethod")
fun createCube(
    from: Vector3f,
    to: Vector3f,
    up: TextureAtlasSprite,
    down: TextureAtlasSprite,
    north: TextureAtlasSprite,
    south: TextureAtlasSprite,
    east: TextureAtlasSprite,
    west: TextureAtlasSprite,
    dynamicUV: Boolean = true
): List<BakedQuad?>? {
    val quads = mutableListOf<BakedQuad>()
    val fx = from.x()
    val fy = from.y()
    val fz = from.z()
    val tx = to.x()
    val ty = to.y()
    val tz = to.z()
    quads.add(
        createQuad(
            v(fx, ty, fz),
            v(fx, ty, tz),
            v(tx, ty, tz),
            v(tx, ty, fz),
            up,
            getFrom(dynamicUV, fx),
            getTo(dynamicUV, tx),
            getFrom(dynamicUV, fz),
            getTo(dynamicUV, tz)
        )
    )
    quads.add(
        createQuad(
            v(tx, fy, fz),
            v(tx, fy, tz),
            v(fx, fy, tz),
            v(fx, fy, fz),
            down,
            getFrom(dynamicUV, 1f - tx),
            getTo(dynamicUV, 1f - fx),
            getFrom(dynamicUV, fz),
            getTo(dynamicUV, tz)
        )
    )
    quads.add(
        createQuad(
            v(tx, ty, fz),
            v(tx, fy, fz),
            v(fx, fy, fz),
            v(fx, ty, fz),
            north,
            getFrom(dynamicUV, 1f - tx),
            getTo(dynamicUV, 1f - fx),
            getFrom(dynamicUV, 1f - ty),
            getTo(dynamicUV, 1f - fy)
        )
    )
    quads.add(
        createQuad(
            v(fx, ty, tz),
            v(fx, fy, tz),
            v(tx, fy, tz),
            v(tx, ty, tz),
            south,
            getFrom(dynamicUV, fx),
            getTo(dynamicUV, tx),
            getFrom(dynamicUV, 1f - ty),
            getTo(dynamicUV, 1f - fy)
        )
    )
    quads.add(
        createQuad(
            v(tx, ty, tz),
            v(tx, fy, tz),
            v(tx, fy, fz),
            v(tx, ty, fz),
            east,
            getFrom(dynamicUV, 1f - tz),
            getTo(dynamicUV, 1f - fz),
            getFrom(dynamicUV, 1f - ty),
            getTo(dynamicUV, 1f - fy)
        )
    )
    quads.add(
        createQuad(
            v(fx, ty, fz),
            v(fx, fy, fz),
            v(fx, fy, tz),
            v(fx, ty, tz),
            west,
            getFrom(dynamicUV, fz),
            getTo(dynamicUV, tz),
            getFrom(dynamicUV, 1f - ty),
            getTo(dynamicUV, 1f - fy)
        )
    )
    return quads
}

private fun getFrom(dynamicUV: Boolean, d: Float): Double {
    return if (dynamicUV && 0 <= d && d <= 1) 16.0 * d else 0.0
}

private fun getTo(dynamicUV: Boolean, d: Float): Double {
    return if (dynamicUV && 0 <= d && d <= 1) 16.0 * d else 16.0
}
