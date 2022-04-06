import org.junit.jupiter.api.Test
import kotlin.test.*
import lab2.shapes.*

class Lab2UnitTests {

    @Test
    fun `Add test passes`() {
        val shapeCollector = ShapeCollector()
        val square = Square(black, white, 2.0)
        val rectangle = Rectangle(blue, red, 1.0, 2.0)
        shapeCollector.add(square)
        shapeCollector.add(rectangle)

        val expectedList: MutableList<ColoredShape2D> = emptyList<ColoredShape2D>().toMutableList()
        expectedList.add(square)
        expectedList.add(rectangle)
        assertEquals(ShapeCollector(expectedList), shapeCollector)
    }

    @Test
    fun `Add test fails with negative sides`() {
        assertFails {
            val shapeCollector = ShapeCollector()
            shapeCollector.add(Rectangle(black, white, -1.0, 2.0))
        }
    }

    @Test
    fun `Add test fails with triangle rule`() {
        assertFails {
            val shapeCollector = ShapeCollector()
            shapeCollector.add(Triangle(black, white, 1.0, 4.0, 2.0))
        }
    }

    @Test
    fun `Get shapes test`() {
        val shapeCollector = ShapeCollector()
        val square = Square(black, white, 2.0)
        val rectangle = Rectangle(blue, red, 1.0, 2.0)

        shapeCollector.add(square)
        shapeCollector.add(rectangle)

        val expectedList: MutableList<ColoredShape2D> = mutableListOf(square, rectangle)
        assertEquals(expectedList, shapeCollector.getShapes())
    }

    @Test
    fun `Get shapes count test`() {
        val shapeCollector = ShapeCollector()

        shapeCollector.add(Square(black, white, 2.0))
        shapeCollector.add(Rectangle(blue, red, 1.0, 2.0))
        shapeCollector.add(Circle(black, white, 4.0))

        assertEquals(3, shapeCollector.getShapesCount())
    }

    @Test
    fun `Get areas sum test`() {
        val shapeCollector = ShapeCollector()

        shapeCollector.add(Square(black, white, 1.0))
        shapeCollector.add(Rectangle(blue, red, 1.0, 3.0))
        shapeCollector.add(Triangle(black, white, 4.0, 5.0, 3.0))

        assertEquals(10.0, shapeCollector.getAreasSum())
    }

    @Test
    fun `Find shape with min area test`() {
        val shapeCollector = ShapeCollector()
        val shapeWithMinArea = Square(black, white, 1.0)

        shapeCollector.add(shapeWithMinArea)
        shapeCollector.add(Rectangle(blue, red, 1.0, 3.0))
        shapeCollector.add(Triangle(black, white, 4.0, 5.0, 3.0))

        assertEquals(shapeWithMinArea, shapeCollector.findShapeWithMinArea())
    }

    @Test
    fun `Find shape with max area test`() {
        val shapeCollector = ShapeCollector()
        val shapeWithMaxArea = Triangle(black, white, 4.0, 5.0, 3.0)

        shapeCollector.add(Square(black, white, 1.0))
        shapeCollector.add(Rectangle(blue, red, 1.0, 3.0))
        shapeCollector.add(shapeWithMaxArea)

        assertEquals(shapeWithMaxArea, shapeCollector.findShapeWithMaxArea())
    }

    @Test
    fun `Find shapes with border color`() {
        val shapeCollector = ShapeCollector()
        val square = Square(black, red, 2.0)
        val rectangle = Rectangle(blue, red, 1.0, 2.0)
        val triangle = Triangle(black, white, 4.0, 5.0, 3.0)

        shapeCollector.add(square)
        shapeCollector.add(rectangle)
        shapeCollector.add(triangle)

        val expectedList = listOf(square, triangle)

        assertEquals(expectedList, shapeCollector.findShapesWithBorderColor(black))
    }

    @Test
    fun `Find shapes with fill color`() {
        val shapeCollector = ShapeCollector()
        val circle = Circle(black, red, 2.0)
        val rectangle = Rectangle(blue, red, 1.0, 2.0)
        val triangle = Triangle(black, white, 4.0, 5.0, 3.0)

        shapeCollector.add(circle)
        shapeCollector.add(rectangle)
        shapeCollector.add(triangle)

        val expectedList = listOf(circle, rectangle)

        assertEquals(expectedList, shapeCollector.findShapesWithFillColor(red))
    }

    @Test
    fun `Get shapes grouped by border color`() {
        val shapeCollector = ShapeCollector()
        val circle = Circle(black, red, 2.0)
        val rectangle = Rectangle(blue, red, 1.0, 2.0)
        val triangle = Triangle(black, white, 4.0, 5.0, 3.0)

        shapeCollector.add(circle)
        shapeCollector.add(rectangle)
        shapeCollector.add(triangle)

        val expectedMap = listOf(circle, rectangle, triangle).groupBy { it.borderColor }

        assertEquals(expectedMap, shapeCollector.getShapesGroupedByBorderColor())
    }

    @Test
    fun `Get shapes grouped by fill color`() {
        val shapeCollector = ShapeCollector()
        val circle = Circle(black, red, 2.0)
        val rectangle = Rectangle(blue, red, 1.0, 2.0)
        val triangle = Triangle(black, white, 4.0, 5.0, 3.0)

        shapeCollector.add(circle)
        shapeCollector.add(rectangle)
        shapeCollector.add(triangle)

        val expectedMap = listOf(circle, rectangle, triangle).groupBy { it.fillColor }

        assertEquals(expectedMap, shapeCollector.getShapesGroupedByFillColor())
    }

    @Test
    fun `Get shapes of certain type`() {
        val shapeCollector = ShapeCollector()
        val square1 = Square(black, blue, 4.0)
        val rectangle = Rectangle(blue, green, 3.0, 1.0)
        val circle = Circle(green, black, 2.0)
        val square2 = Square(black, white, 1.0)

        shapeCollector.add(square1)
        shapeCollector.add(rectangle)
        shapeCollector.add(circle)
        shapeCollector.add(square2)

        val expectedList = listOf(square1, rectangle, square2)

        assertEquals(expectedList, shapeCollector.getAllShapesByType<Rectangle>())
    }
}