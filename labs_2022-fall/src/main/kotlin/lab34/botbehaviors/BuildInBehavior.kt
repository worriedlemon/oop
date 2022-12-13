package lab34.botbehaviors

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import io.ktor.server.plugins.*
import lab34.EXCLUDED_LETTERS

class BuildInBehavior : BotBehavior {
    private val citiesCsvPath = "src/main/resources/BotFiles/cities.csv"
    private val cities = mutableListOf<String>()

    init {
        csvReader {
            delimiter = ','
        }.open(citiesCsvPath) {
            readAllAsSequence().forEach {
                cities.add(it[1])
            }
        }

        println("Working with build-in cities:\n$cities")
    }

    override fun chooseCity(text: String, namedCities: Set<String>): String? {
        if (text !in cities) throw NotFoundException("No such city in a data-class")

        val lastChar = if (text.last().lowercaseChar() in EXCLUDED_LETTERS) {
            text[text.length - 2]
        } else {
            text.last().lowercaseChar()
        }

        val possibleCities = cities.filter {
            it.first().lowercaseChar() == lastChar && !namedCities.contains(it)
        }
        return if (possibleCities.isEmpty()) null else possibleCities.random()
    }
}
