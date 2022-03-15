import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.assertDoesNotThrow
import lab1.address.*

class Lab1UnitTests {

    @Test
    fun `Parse addresses pass`() {
        val stringAddresses: String = """
            1, Archangelsk, Lenin st., 23
            2, Boxitogorsk, Stalin alley, 53
            3, Volgograd, Khrushchev roadway, 64
            4, Grozny, Brezhnev Boulevard, 84
        """.trimIndent()
        assertDoesNotThrow { parseAddresses(stringAddresses) }
    }

    @Test
    fun `Parse addresses fail`() {
        val stringAddress: String = """
            1, Archangelsk, Lenin st., 23
            2, Boxitogorsk, Stalin alley, 53
            3, Volgograd, 
            4, Grozny, Brezhnev Boulevard, 84
        """.trimIndent()
        assertThrows<ArrayIndexOutOfBoundsException> {
            parseAddresses(stringAddress)
        }
    }

    @Test
    fun `Get full address`() {
        val address = parseAddresses("123456, Saint-Petersburg, Basseynaya st., 12")
        assertEquals(address[0].full(), "123456, Saint-Petersburg, Basseynaya st., 12")
    }
}