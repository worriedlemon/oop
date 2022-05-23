import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.*
import lab256.iomanager.*
import lab256.serialization.JsonSerialization
import lab256.shapes.*
import java.io.FileInputStream

const val WR_TEST_PATH = "./src/test/resources/UnitTestsForLab6_Files/ReadAndWriteTest.json"
const val ENCODING_TEST_PATH = "./src/test/resources/UnitTestsForLab6_Files/EncodingTest.json"

class Lab2UnitTests {
    // Unit tests for Laboratory 2

    @Test
    fun `Add test passes`() {
        val shapeCollector = ShapeCollector<ColoredShape2D>()
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
            val shapeCollector = ShapeCollector<ColoredShape2D>()
            shapeCollector.add(Rectangle(Color.BLACK, Color.WHITE, -1.0, 2.0))
        }
    }

    @Test
    fun `Add test fails with triangle rule`() {
        assertFails {
            val shapeCollector = ShapeCollector<ColoredShape2D>()
            shapeCollector.add(Triangle(Color.BLACK, Color.WHITE, 1.0, 4.0, 2.0))
        }
    }

    @Test
    fun `Get shapes test`() {
        val shapeCollector = ShapeCollector<ColoredShape2D>()

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
        val shapeCollector = ShapeCollector<ColoredShape2D>()

        shapeCollector.add(Square(Color.BLACK, Color.WHITE, 2.0))
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 2.0))
        shapeCollector.add(Circle(Color.BLACK, Color.WHITE, 4.0))

        assertEquals(3, shapeCollector.getShapesCount())
    }

    @Test
    fun `Get areas sum test`() {
        val shapeCollector = ShapeCollector<ColoredShape2D>()

        shapeCollector.add(Square(Color.BLACK, Color.WHITE, 1.0))
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 3.0))
        shapeCollector.add(Triangle(Color.BLACK, Color.WHITE, 4.0, 5.0, 3.0))

        assertEquals(10.0, shapeCollector.getAreasSum())
    }

    @Test
    fun `Find shape with min area test`() {
        val shapeCollector = ShapeCollector<ColoredShape2D>()
        val shapeWithMinArea = Square(Color.BLACK, Color.WHITE, 1.0)

        shapeCollector.add(shapeWithMinArea)
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 3.0))
        shapeCollector.add(Triangle(Color.BLACK, Color.WHITE, 4.0, 5.0, 3.0))

        assertEquals(shapeWithMinArea, shapeCollector.findShapeWithMinArea())
    }

    @Test
    fun `Find shape with max area test`() {
        val shapeCollector = ShapeCollector<ColoredShape2D>()
        val shapeWithMaxArea = Triangle(Color.BLACK, Color.WHITE, 4.0, 5.0, 3.0)

        shapeCollector.add(Square(Color.BLACK, Color.WHITE, 1.0))
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 3.0))
        shapeCollector.add(shapeWithMaxArea)

        assertEquals(shapeWithMaxArea, shapeCollector.findShapeWithMaxArea())
    }

    @Test
    fun `Find shape with min area in empty ShapeCollector test`()
    {
        val shapeCollector = ShapeCollector<ColoredShape2D>()
        assertNull(shapeCollector.findShapeWithMinArea())
    }

    @Test
    fun `Find shape with max area in empty ShapeCollector test`()
    {
        val shapeCollector = ShapeCollector<ColoredShape2D>()
        assertNull(shapeCollector.findShapeWithMaxArea())
    }

    @Test
    fun `Find shapes with border color`() {
        val shapeCollector = ShapeCollector<ColoredShape2D>()

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
        val shapeCollector = ShapeCollector<ColoredShape2D>()

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
        val shapeCollector = ShapeCollector<ColoredShape2D>()

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
        val shapeCollector = ShapeCollector<ColoredShape2D>()

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
        val shapeCollector = ShapeCollector<ColoredShape2D>()

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
        val actualList = shapeCollector.getAllShapesByType<Rectangle>()
        assertEquals(expectedList, actualList)
    }

    // Unit tests for Laboratory 5

    @Test
    fun `Add shape collector test`() {
        val rectangleCollector = ShapeCollector<Rectangle>()
        rectangleCollector.add(Square(Color.BLACK, Color.WHITE, 2.0))
        rectangleCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 2.0))

        val shapeCollector = ShapeCollector<ColoredShape2D>()
        shapeCollector.add(Circle(Color.BLACK, Color.WHITE, 2.0))
        shapeCollector.addAll(rectangleCollector)

        val expectedList =
            mutableListOf(
                Circle(Color.BLACK, Color.WHITE, 2.0),
                Square(Color.BLACK, Color.WHITE, 2.0),
                Rectangle(Color.BLUE, Color.RED, 1.0, 2.0)
            )
        assertEquals(ShapeCollector(expectedList), shapeCollector)
    }

    @Test
    fun `Add collection test`() {
        val rectangleCollector = listOf(
            Square(Color.BLACK, Color.WHITE, 2.0),
            Rectangle(Color.BLUE, Color.RED, 1.0, 2.0)
        )

        val shapeCollector = ShapeCollector<ColoredShape2D>()
        shapeCollector.add(Circle(Color.BLACK, Color.WHITE, 2.0))
        shapeCollector.addAll(rectangleCollector)

        val expectedList =
            mutableListOf(
                Circle(Color.BLACK, Color.WHITE, 2.0),
                Square(Color.BLACK, Color.WHITE, 2.0),
                Rectangle(Color.BLUE, Color.RED, 1.0, 2.0)
            )
        assertEquals(ShapeCollector(expectedList), shapeCollector)
    }

    @Test
    fun `Get sorted from min to max test`() {
        val shapeCollector = ShapeCollector<ColoredShape2D>()
        shapeCollector.add(Circle(Color.BLACK, Color.WHITE, 2.0)) // Area of 12.566
        shapeCollector.add(Triangle(Color.BLACK, Color.WHITE, 4.0)) // Area of 6.928
        shapeCollector.add(Square(Color.BLACK, Color.WHITE, 1.0)) // Area of 1.000
        shapeCollector.add(Rectangle(Color.BLACK, Color.WHITE, 1.5, 2.0)) // Area of 3.000

        val comparingFromMinToMaxRules = Comparator<ColoredShape2D> { o1, o2 ->
            when {
                o1.calcArea() > o2.calcArea() -> 1
                o1.calcArea() < o2.calcArea() -> -1
                else -> 0
            }
        }

        val expectedList =
            mutableListOf(
                Square(Color.BLACK, Color.WHITE, 1.0),
                Rectangle(Color.BLACK, Color.WHITE, 1.5, 2.0),
                Triangle(Color.BLACK, Color.WHITE, 4.0),
                Circle(Color.BLACK, Color.WHITE, 2.0)
            )
        assertEquals(expectedList, shapeCollector.getSorted(comparingFromMinToMaxRules))
    }

    @Test
    fun `Get sorted from max to min test`() {
        val shapeCollector = ShapeCollector<ColoredShape2D>()
        shapeCollector.add(Circle(Color.BLACK, Color.WHITE, 2.0)) // Area of 12.566
        shapeCollector.add(Triangle(Color.BLACK, Color.WHITE, 4.0)) // Area of 6.928
        shapeCollector.add(Square(Color.BLACK, Color.WHITE, 1.0)) // Area of 1.000
        shapeCollector.add(Rectangle(Color.BLACK, Color.WHITE, 1.5, 2.0)) // Area of 3.000

        val comparingFromMaxToMinRules = Comparator<ColoredShape2D> { o1, o2 ->
            when {
                o1.calcArea() < o2.calcArea() -> 1
                o1.calcArea() > o2.calcArea() -> -1
                else -> 0
            }
        }

        val expectedList =
            mutableListOf(
                Circle(Color.BLACK, Color.WHITE, 2.0),
                Triangle(Color.BLACK, Color.WHITE, 4.0),
                Rectangle(Color.BLACK, Color.WHITE, 1.5, 2.0),
                Square(Color.BLACK, Color.WHITE, 1.0)
            )
        assertEquals(expectedList, shapeCollector.getSorted(comparingFromMaxToMinRules))
    }

    // Unit tests for Laboratory 6

    @Test
    fun `Create JSON serialization`() {
        val shapeCollector = ShapeCollector<ColoredShape2D>()
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 2.0))
        shapeCollector.add(Circle(Color.BLACK, Color.WHITE, 2.0))

        val expectedResult = String(FileInputStream(ENCODING_TEST_PATH).readAllBytes())
        assertEquals(expectedResult, JsonSerialization.encodeToString(shapeCollector.getShapes()))
    }

    @Test
    fun `Deserialize JSON text`() {
        val shapeCollector = ShapeCollector<ColoredShape2D>()
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 2.0))
        shapeCollector.add(Circle(Color.BLACK, Color.WHITE, 2.0))

        assertEquals(shapeCollector.getShapes(),
            JsonSerialization.decodeFromString(
                String(FileInputStream(ENCODING_TEST_PATH).readAllBytes())
            )
        )
    }

    @Test
    fun `Write JSON file test`() {
        val shapeCollector = ShapeCollector<ColoredShape2D>()
        shapeCollector.add(Square(Color.BLACK, Color.WHITE, 2.0))
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 2.0))
        shapeCollector.add(Circle(Color.BLACK, Color.WHITE, 2.0))

        assertDoesNotThrow {
            SerializeIO.writeJsonFile(WR_TEST_PATH, shapeCollector.getShapes())
        }
    }

    @Test
    fun `Read JSON file test`() {
        val shapeCollector = ShapeCollector<ColoredShape2D>()
        shapeCollector.add(Square(Color.BLACK, Color.WHITE, 2.0))
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 2.0))
        shapeCollector.add(Circle(Color.BLACK, Color.WHITE, 2.0))

        assertEquals(shapeCollector.getShapes(), SerializeIO.readJsonFile(WR_TEST_PATH))
    }
}