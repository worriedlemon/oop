package lab6.serialization

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.*
import lab6.shapes.*

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

    fun encodeToString(shapeCollector: ShapeCollector): String {
        return json.encodeToString(shapeCollector)
    }

    fun decodeFromString(string: String): ShapeCollector {
        return json.decodeFromString(string)
    }
}