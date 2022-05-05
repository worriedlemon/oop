import org.junit.jupiter.api.Test
import kotlin.test.*
import lab5.shapes.*

class Lab5UnitTests {

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
        assertEquals(ShapeCollector(expectedList), shapeCollector.getSorted(comparingFromMinToMaxRules))
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
        assertEquals(ShapeCollector(expectedList), shapeCollector.getSorted(comparingFromMaxToMinRules))
    }
}