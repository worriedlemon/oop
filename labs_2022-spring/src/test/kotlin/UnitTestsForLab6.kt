import lab6.iomanager.ShapeCollectorIOManager
import lab6.serialization.ShapeCollectorJsonSerialization
import org.junit.jupiter.api.Test
import kotlin.test.*
import lab6.shapes.*
import org.junit.jupiter.api.assertDoesNotThrow
import java.io.FileInputStream

const val WR_TEST_PATH = "./src/test/resources/UnitTestsForLab6_Files/ReadAndWriteTest.json"
const val ENCODING_TEST_PATH = "./src/test/resources/UnitTestsForLab6_Files/EncodingTest.json"

class Lab6UnitTests {

    @Test
    fun `Create JSON serialization`() {
        val shapeCollector = ShapeCollector()
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 2.0))
        shapeCollector.add(Circle(Color.BLACK, Color.WHITE, 2.0))

        val expectedResult = String(FileInputStream(ENCODING_TEST_PATH).readAllBytes())
        assertEquals(expectedResult, ShapeCollectorJsonSerialization.encodeToString(shapeCollector))
    }

    @Test
    fun `Deserialize JSON text`() {
        val shapeCollector = ShapeCollector()
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 2.0))
        shapeCollector.add(Circle(Color.BLACK, Color.WHITE, 2.0))

        assertEquals(shapeCollector,
            ShapeCollectorJsonSerialization.decodeFromString(
                String(FileInputStream(ENCODING_TEST_PATH).readAllBytes())
            )
        )
    }

    @Test
    fun `Write JSON file test`() {
        val shapeCollector = ShapeCollector()
        shapeCollector.add(Square(Color.BLACK, Color.WHITE, 2.0))
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 2.0))
        shapeCollector.add(Circle(Color.BLACK, Color.WHITE, 2.0))

        assertDoesNotThrow {
            ShapeCollectorIOManager.writeJsonFile(WR_TEST_PATH, shapeCollector)
        }
    }

    @Test
    fun `Read JSON file test`() {
        val shapeCollector = ShapeCollector()
        shapeCollector.add(Square(Color.BLACK, Color.WHITE, 2.0))
        shapeCollector.add(Rectangle(Color.BLUE, Color.RED, 1.0, 2.0))
        shapeCollector.add(Circle(Color.BLACK, Color.WHITE, 2.0))

        assertEquals(shapeCollector, ShapeCollectorIOManager.readJsonFile(WR_TEST_PATH))
    }
}