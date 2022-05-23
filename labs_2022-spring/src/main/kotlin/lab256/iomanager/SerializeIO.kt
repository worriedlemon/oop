package lab256.iomanager

import lab256.serialization.JsonSerialization
import lab256.shapes.ColoredShape2D
import java.io.*

object SerializeIO {
    fun <T : ColoredShape2D> readJsonFile(path: String): List<T> {
        return JsonSerialization.decodeFromString(
                    String(FileInputStream(path).readAllBytes())
                ) as List<T>
    }

    fun <T : ColoredShape2D> writeJsonFile(path: String, shapes: List<T>): String {
        val shapesEncoded = JsonSerialization.encodeToString(shapes)
        FileOutputStream(path).write(shapesEncoded.toByteArray())
        return shapesEncoded
    }
}