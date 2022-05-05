package lab5

import lab5.shapes.*

fun main() {

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

    val rectangles = shapeCollector.getAllShapesByType<Rectangle>()
    println("\nThere are ${rectangles.size} rectangles:")
    rectangles.forEach {
        println("$it ")
    }

    try {
        println("\nTrying to add an impossible triangle of (1.0, 2.0, 3.0)")
        shapeCollector.add(Triangle(Color.RED, Color.WHITE, 1.0, 2.0, 3.0))
    } catch (e: IllegalArgumentException) {
        println("Error: ${e.message}")
    }
}