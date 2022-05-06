package lab6.iomanager

import lab6.serialization.ShapeCollectorJsonSerialization
import lab6.shapes.ShapeCollector
import java.io.*

object ShapeCollectorIOManager {
    fun readJsonFile(path: String): ShapeCollector? {
        return try {
            val shapeCollector = ShapeCollectorJsonSerialization
                .decodeFromString(
                    String(FileInputStream(path).readAllBytes())
                )
            //println("Done")
            shapeCollector
        } catch (e: IOException) {
            println(e.message)
            null
        }
    }

    fun writeJsonFile(path: String, shapeCollector: ShapeCollector): String {
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
}