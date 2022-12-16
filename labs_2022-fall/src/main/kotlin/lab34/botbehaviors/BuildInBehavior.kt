package lab34.botbehaviors

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import io.ktor.server.plugins.*
import lab34.lastCharExclude

class BuildInBehavior : BotBehavior {
    private val namedCities = mutableSetOf<String>()
    private val citiesCsvPath = "labs_2022-fall/src/main/resources/BotFiles/cities.csv"
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

    override fun chooseCity(text: String): String? {
        if (!checkCity(text)) throw NotFoundException("No such city in a data-class")

        addCity(text)

        val lastChar = text.lastCharExclude()

        val possibleCities = cities.filter {
            it.first().lowercaseChar() == lastChar && !checkCity(it)
        }
        return if (possibleCities.isEmpty()) null else possibleCities.random()
    }

    override fun addCity(text: String) {
        namedCities.add(text)
    }

    override fun clearNamedCities() {
        namedCities.clear()
    }

    override fun checkCity(text: String): Boolean = namedCities.contains(text)
}
