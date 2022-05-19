package lab256

import lab256.iomanager.ShapeCollectorIOManager
import lab256.shapes.*

private fun lab5() {
    // General collector
    val shapeCollector = ShapeCollector<ColoredShape2D>()

    // Circle that has an area of 4*pi ~ 12.566
    shapeCollector.add(Circle(Color.BLACK, Color.RED, 2.0))

    // Egyptian triangle with area of 12*5/2 = 30
    shapeCollector.add(Triangle(Color.RED, Color.WHITE, 5.0, 12.0, 13.0))

    // Equilateral triangle with area of sqrt(3)*25/4 ~ 10.825
    shapeCollector.add(Triangle(Color.BLACK, Color.GRAY, 5.0))

    println("\nThere are ${shapeCollector.getShapesCount()} shapes:")
    shapeCollector.getShapes().forEach {
        println("$it ")
    }

    // Collector, which can contain only rectangles (and squares)
    val rectangleCollector = ShapeCollector<Rectangle>()
    rectangleCollector.add(Square(Color.BLACK, Color.BLUE, 4.0))
    rectangleCollector.add(Rectangle(Color.BLUE, Color.GREEN, 3.0, 1.0))
    rectangleCollector.add(Square(Color.GREEN, Color.BLACK, 2.0))
    rectangleCollector.add(Square(Color.BLACK, Color.WHITE, 1.0))

    println("\nThere are ${rectangleCollector.getShapesCount()} rectangles:")
    rectangleCollector.getShapes().forEach {
        println("$it ")
    }

    // Add all shapes from rectangle collector to general collector
    shapeCollector.addAll(rectangleCollector)

    println("\nLets merge this collections... Now there are ${shapeCollector.getShapesCount()} shapes:")
    shapeCollector.getShapes().forEach {
        println("$it ")
    }

    println("\nHow about sorting this collector by shapes area?")
    println("Let's make a comparator, which will compare areas from min to max.")

    /* If first shape has bigger area, then return 1
     * Else if first shape has smaller are - return -1
     * If both shapes have equal areas return 0
     */

    val comparingFromMinToMaxRules = Comparator<ColoredShape2D> { o1, o2 ->
        when {
            o1.calcArea() > o2.calcArea() -> 1
            o1.calcArea() < o2.calcArea() -> -1
            else -> 0
        }
    }

    /* If first shape has bigger area, then return -1
     * Else if first shape has smaller are - return 1
     * If both shapes have equal areas return 0
     */

    val comparingFromMaxToMinRules = comparingFromMinToMaxRules.reversed()

    println("Here is the sorted shape collection:")
    shapeCollector.getSorted(comparingFromMinToMaxRules)
        .getShapes()
        .forEach { println("$it ") }

    println("\nNow sorting in reverse order (max to min):")
    shapeCollector.getSorted(comparingFromMaxToMinRules)
        .getShapes()
        .forEach { println("$it ") }

    // The rest of Main.kt code was a part of lab2. Output didn't change

    println("\nSum of all figures' areas = ")
    shapeCollector.getShapes().forEach {
        print("${it.calcArea()} + ")
    }
    println("= ${shapeCollector.getAreasSum()}")

    println("\nShape with min area is ${shapeCollector.findShapeWithMinArea()}")
    println("and its area has a value of ${shapeCollector.findShapeWithMinArea()!!.calcArea()}")
    println("\nShape with max area is ${shapeCollector.findShapeWithMaxArea()}")
    println("and its area has a value of ${shapeCollector.findShapeWithMaxArea()!!.calcArea()}")

    println("\nShapes with [black] border color:")
    shapeCollector.findShapesWithBorderColor(Color.BLACK).forEach {
        println("$it ")
    }

    println("\nShapes with [white] fill color:")
    shapeCollector.findShapesWithFillColor(Color.WHITE).forEach {
        println("$it ")
    }
}

private fun lab6() {
    val shapeCollector = ShapeCollector<ColoredShape2D>()
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
    val readCollector = ShapeCollectorIOManager.readJsonFile<ColoredShape2D>(path)
    println(readCollector)

    println("\nAre these collectors same? ${shapeCollector == readCollector}")
}

private fun invokeLabByNumber(index: Int) {
    when (index) {
        2 -> lab5()
        5 -> lab5()
        6 -> lab6()
        else -> throw IllegalStateException()
    }
}

fun main() {
    val index: Int = try {
        readln().toInt()
    } catch (e: NumberFormatException) {
        println(e.message)
        println("Invoking lab 5")
        5
    }
    invokeLabByNumber(index)
}