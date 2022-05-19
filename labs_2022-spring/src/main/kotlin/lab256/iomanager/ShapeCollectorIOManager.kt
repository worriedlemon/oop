package lab256.iomanager

import lab256.serialization.ShapeCollectorJsonSerialization
import lab256.shapes.ColoredShape2D
import lab256.shapes.ShapeCollector
import java.io.*

object ShapeCollectorIOManager {
    private const val STANDART_PATH = "./src/main/resources/lab6/shapeCollector.json"

    fun <T : ColoredShape2D> readJsonFile(path: String): ShapeCollector<T>? {
        return try {
            val shapeCollector = ShapeCollectorJsonSerialization
                .decodeFromString(
                    String(FileInputStream(path).readAllBytes())
                )
            //println("Done")
            shapeCollector as ShapeCollector<T>
        } catch (e: IOException) {
            println(e.message)
            null
        }
    }

    fun <T : ColoredShape2D> writeJsonFile(path: String, shapeCollector: ShapeCollector<T>): String {
        val jsonResult = ShapeCollectorJsonSerialization.encodeToString(shapeCollector)
        try {
            FileOutputStream(path).buffered().use {
                it.write(jsonResult.toByteArray())
                //println("Done")
            }
        } catch (e: IOException) {
            println(e.message)
        }
        return jsonResult
    }

    fun readPath(): String = readln().ifBlank { STANDART_PATH }
}