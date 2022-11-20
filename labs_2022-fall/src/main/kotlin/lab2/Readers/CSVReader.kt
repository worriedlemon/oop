package lab2.Readers

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import lab2.AddressData
import lab2.ParseResults
import java.io.File

class CSVReader : BigFileReader {
    override fun readInfoFrom(file: File): ParseResults {
        val houses = HashMap<AddressData, Int>()

        csvReader {
            delimiter = ';'
        }.open(file) {
            readAllWithHeaderAsSequence().forEach { item ->
                val key = AddressData(
                    item["city"]!!,
                    item["street"]!!,
                    item["house"]!!.toInt(),
                    item["floor"]!!.toInt()
                )

                if (houses[key] == null) {
                    houses[key] = 1
                } else {
                    houses[key] = houses[key]!! + 1
                }
            }
        }

        return ParseResults(houses)
    }
}