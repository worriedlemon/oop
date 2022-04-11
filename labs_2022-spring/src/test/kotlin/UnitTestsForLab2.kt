import org.junit.jupiter.api.Test
import kotlin.test.*
import lab2.shapes.*

class Lab2UnitTests {

    @Test
    fun `Add test passes`() {
        val shapeCollector = ShapeCollector()
        shapeCollector.add(Square(Color.BLACK, Color.WHITE, 2.0))
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 2.0))

        val expectedList =
            mutableListOf<ColoredShape2D>(
                Square(Color.BLACK, Color.WHITE, 2.0),
                Rectangle(Color.BLUE, Color.RED, 1.0, 2.0)
            )
        assertEquals(ShapeCollector(expectedList), shapeCollector)
    }

    @Test
    fun `Add test fails with negative sides`() {
        assertFails {
            val shapeCollector = ShapeCollector()
            shapeCollector.add(Rectangle(Color.BLACK, Color.WHITE, -1.0, 2.0))
        }
    }

    @Test
    fun `Add test fails with triangle rule`() {
        assertFails {
            val shapeCollector = ShapeCollector()
            shapeCollector.add(Triangle(Color.BLACK, Color.WHITE, 1.0, 4.0, 2.0))
        }
    }

    @Test
    fun `Get shapes test`() {
        val shapeCollector = ShapeCollector()

        shapeCollector.add(Square(Color.BLACK, Color.WHITE, 2.0))
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 2.0))

        val expectedList: MutableList<ColoredShape2D> =
            mutableListOf(
                Square(Color.BLACK, Color.WHITE, 2.0),
                Rectangle(Color.BLUE, Color.RED, 1.0, 2.0)
            )

        assertEquals(expectedList, shapeCollector.getShapes())
    }

    @Test
    fun `Get shapes count test`() {
        val shapeCollector = ShapeCollector()

        shapeCollector.add(Square(Color.BLACK, Color.WHITE, 2.0))
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 2.0))
        shapeCollector.add(Circle(Color.BLACK, Color.WHITE, 4.0))

        assertEquals(3, shapeCollector.getShapesCount())
    }

    @Test
    fun `Get areas sum test`() {
        val shapeCollector = ShapeCollector()

        shapeCollector.add(Square(Color.BLACK, Color.WHITE, 1.0))
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 3.0))
        shapeCollector.add(Triangle(Color.BLACK, Color.WHITE, 4.0, 5.0, 3.0))

        assertEquals(10.0, shapeCollector.getAreasSum())
    }

    @Test
    fun `Find shape with min area test`() {
        val shapeCollector = ShapeCollector()
        val shapeWithMinArea = Square(Color.BLACK, Color.WHITE, 1.0)

        shapeCollector.add(shapeWithMinArea)
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 3.0))
        shapeCollector.add(Triangle(Color.BLACK, Color.WHITE, 4.0, 5.0, 3.0))

        assertEquals(shapeWithMinArea, shapeCollector.findShapeWithMinArea())
    }

    @Test
    fun `Find shape with max area test`() {
        val shapeCollector = ShapeCollector()
        val shapeWithMaxArea = Triangle(Color.BLACK, Color.WHITE, 4.0, 5.0, 3.0)

        shapeCollector.add(Square(Color.BLACK, Color.WHITE, 1.0))
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 3.0))
        shapeCollector.add(shapeWithMaxArea)

        assertEquals(shapeWithMaxArea, shapeCollector.findShapeWithMaxArea())
    }

    @Test
    fun `Find shape with min area in empty ShapeCollector test`()
    {
        val shapeCollector = ShapeCollector()
        assertNull(shapeCollector.findShapeWithMinArea())
    }

    @Test
    fun `Find shape with max area in empty ShapeCollector test`()
    {
        val shapeCollector = ShapeCollector()
        assertNull(shapeCollector.findShapeWithMaxArea())
    }

    @Test
    fun `Find shapes with border color`() {
        val shapeCollector = ShapeCollector()

        shapeCollector.add(Square(Color.BLACK, Color.RED, 2.0))
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 2.0))
        shapeCollector.add(Triangle(Color.BLACK, Color.WHITE, 4.0, 5.0, 3.0))

        val expectedList =
            listOf(
                Square(Color.BLACK, Color.RED, 2.0),
                Triangle(Color.BLACK, Color.WHITE, 4.0, 5.0, 3.0)
            )

        assertEquals(expectedList, shapeCollector.findShapesWithBorderColor(Color.BLACK))
    }

    @Test
    fun `Find shapes with fill color`() {
        val shapeCollector = ShapeCollector()

        shapeCollector.add(Circle(Color.BLACK, Color.RED, 2.0))
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 2.0))
        shapeCollector.add(Triangle(Color.BLACK, Color.WHITE, 4.0, 5.0, 3.0))

        val expectedList =
            listOf(
                Circle(Color.BLACK, Color.RED, 2.0),
                Rectangle(Color.BLUE, Color.RED, 1.0, 2.0)
            )

        assertEquals(expectedList, shapeCollector.findShapesWithFillColor(Color.RED))
    }

    @Test
    fun `Get shapes grouped by border color`() {
        val shapeCollector = ShapeCollector()

        shapeCollector.add(Circle(Color.BLACK, Color.RED, 2.0))
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 2.0))
        shapeCollector.add(Triangle(Color.BLACK, Color.WHITE, 4.0, 5.0, 3.0))

        val expectedMap =
            listOf(
                Circle(Color.BLACK, Color.RED, 2.0),
                Rectangle(Color.BLUE, Color.RED, 1.0, 2.0),
                Triangle(Color.BLACK, Color.WHITE, 4.0, 5.0, 3.0)
            ).groupBy { it.borderColor }

        assertEquals(expectedMap, shapeCollector.getShapesGroupedByBorderColor())
    }

    @Test
    fun `Get shapes grouped by fill color`() {
        val shapeCollector = ShapeCollector()

        shapeCollector.add(Circle(Color.BLACK, Color.RED, 2.0))
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 2.0))
        shapeCollector.add(Triangle(Color.BLACK, Color.WHITE, 4.0, 5.0, 3.0))

        val expectedMap =
            listOf(
                Circle(Color.BLACK, Color.RED, 2.0),
                Rectangle(Color.BLUE, Color.RED, 1.0, 2.0),
                Triangle(Color.BLACK, Color.WHITE, 4.0, 5.0, 3.0)
            ).groupBy { it.fillColor }

        assertEquals(expectedMap, shapeCollector.getShapesGroupedByFillColor())
    }

    @Test
    fun `Get shapes of certain type`() {
        val shapeCollector = ShapeCollector()

        shapeCollector.add(Square(Color.BLACK, Color.BLUE, 4.0))
        shapeCollector.add(Rectangle(Color.BLUE, Color.GREEN, 3.0, 1.0))
        shapeCollector.add(Circle(Color.GREEN, Color.BLACK, 2.0))
        shapeCollector.add(Square(Color.BLACK, Color.WHITE, 1.0))

        val expectedList =
            listOf(
                Square(Color.BLACK, Color.BLUE, 4.0),
                Rectangle(Color.BLUE, Color.GREEN, 3.0, 1.0),
                Square(Color.BLACK, Color.WHITE, 1.0)
            )

        assertEquals(expectedList, shapeCollector.getAllShapesByType<Rectangle>())
    }
}