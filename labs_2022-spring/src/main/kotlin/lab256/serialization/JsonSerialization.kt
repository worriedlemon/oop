package lab256.serialization

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.*
import lab256.shapes.*

object JsonSerialization {
    private val json = Json {
        prettyPrint = true

        serializersModule = SerializersModule {
            polymorphic(ColoredShape2D::class) {
                subclass(Rectangle::class)
                subclass(Circle::class)
                subclass(Triangle::class)
                subclass(Square::class)
            }
        }
    }

    fun encodeToString(shapes: List<ColoredShape2D>): String {
        return json.encodeToString(shapes)
    }

    fun decodeFromString(string: String): List<ColoredShape2D> {
        return json.decodeFromString(string)
    }
}