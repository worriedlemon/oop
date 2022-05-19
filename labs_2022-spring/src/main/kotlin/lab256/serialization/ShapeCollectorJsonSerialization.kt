package lab256.serialization

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.*
import lab256.shapes.*

object ShapeCollectorJsonSerialization {
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

    fun encodeToString(shapeCollector: ShapeCollector<out ColoredShape2D>): String {
        return json.encodeToString(shapeCollector)
    }

    fun decodeFromString(string: String): ShapeCollector<out ColoredShape2D> {
        return json.decodeFromString(string)
    }
}