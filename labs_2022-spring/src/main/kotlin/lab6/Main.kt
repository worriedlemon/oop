package lab6

import lab6.iomanager.ShapeCollectorIOManager
import lab6.shapes.*

fun main() {
    val shapeCollector = ShapeCollector()
    shapeCollector.add(Rectangle(Color.BLACK, Color.WHITE, 2.0, 3.0))
    shapeCollector.add(Circle(Color.BLUE, Color.GRAY, 1.5))
    shapeCollector.add(Triangle(Color.RED, Color.BLACK, 2.0, 2.0, 1.0))
    shapeCollector.add(Square(Color.YELLOW, Color.MAGENTA, 3.0))

    println("Input the full desired path to a JSON file:")
    val path = ShapeCollectorIOManager.readPath()
    val jsonResult = ShapeCollectorIOManager.writeJsonFile(path, shapeCollector)

    println("Result is:\n$jsonResult\n")

    println("Adding some shapes (triangle and rectangle)...\n")
    shapeCollector.add(Triangle(Color.CYAN, Color.WHITE, 3.0))
    shapeCollector.add(Rectangle(Color.GREEN, Color.YELLOW, 4.0, 1.0))

    println("Information read from file \'$path\':")
    val readCollector = ShapeCollectorIOManager.readJsonFile(path)
    println(readCollector)

    println("\nAre these collectors same? ${shapeCollector == readCollector}")
}