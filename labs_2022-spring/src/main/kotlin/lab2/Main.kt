package lab2

import lab2.shapes.*

fun main() {
    val shapeCollector = ShapeCollector()

    // Circle that has an area of 4*pi ~ 12.566
    shapeCollector.add(Circle(black, red, 2.0))

    // Egyptian triangle with area of 12*5/2 = 30
    shapeCollector.add(Triangle(red, white, 5.0, 12.0, 13.0))

    // Equilateral triangle with area of sqrt(3)*25/4 ~ 10.825
    shapeCollector.add(Triangle(black, gray, 5.0))

    shapeCollector.add(Square(black, blue, 4.0))
    shapeCollector.add(Rectangle(blue, green, 3.0, 1.0))
    shapeCollector.add(Square(green, black, 2.0))
    shapeCollector.add(Square(black, white, 1.0))

    println("There are ${shapeCollector.getShapesCount()} shapes:")
    shapeCollector.getShapes().forEach {
        println("$it ")
    }

    println("\nSum of all figures' areas = ")
    shapeCollector.getShapes().forEach {
        print("${it.calcArea()} + ")
    }
    println("= ${shapeCollector.getAreasSum()}")

    println("\nShape with min area is ${shapeCollector.findShapeWithMinArea()}")
    println("and its area has a value of ${shapeCollector.findShapeWithMinArea().calcArea()}")
    println("\nShape with max area is ${shapeCollector.findShapeWithMaxArea()}")
    println("and its area has a value of ${shapeCollector.findShapeWithMaxArea().calcArea()}")

    println("\nShapes with [black] border color:")
    shapeCollector.findShapesWithBorderColor(black).forEach {
        println("$it ")
    }

    println("\nShapes with [white] fill color:")
    shapeCollector.findShapesWithFillColor(white).forEach {
        println("$it ")
    }

    val rectangles = shapeCollector.getAllShapesByType<Rectangle>()
    println("\nThere are ${rectangles.size} rectangles:")
    rectangles.forEach {
        println("$it ")
    }

    try {
        println("\nTrying to add an impossible triangle")
        shapeCollector.add(Triangle(red, white, 1.0, 2.0, 3.0))
    } catch (e: IllegalStateException) {
        println("Error: ${e.message}")
    }
}