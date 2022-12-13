package lab2

import lab2.readers.*
import java.io.File
import kotlin.system.measureTimeMillis

fun parseInfoFrom(pathToFile: String) {
    val file = File(pathToFile)

    if (!file.exists()) {
        println("Inputted path is incorrect: file doesn't exists")
    } else if (file.isDirectory) {
        println("Error: Inputted path represents a directory")
    } else {
        val fileReader: BigFileReader = when (file.extension) {
            "csv" -> CSVReader()
            "xml" -> XMLReader()
            else -> {
                println("File extension is not valid: use CSV or XML")
                return
            }
        }

        println("File \"${file.absolutePath}\" processing has started...")

        val parseDuration = measureTimeMillis {
            val parseResults = fileReader.readInfoFrom(file)

            println("Done.\n\nRecurrences:")
            parseResults.outputRecurrences()

            println("\nHouses count by floors:")
            parseResults.outputGroupedByFloors()
        } / 1000.0

        println("\nTime passed: $parseDuration seconds")
    }
}