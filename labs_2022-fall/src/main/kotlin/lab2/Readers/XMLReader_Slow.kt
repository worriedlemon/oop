package lab2.Readers

import lab2.AddressData
import lab2.ParseResults
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class XMLReader_Slow : BigFileReader {
    override fun readInfoFrom(file: File): ParseResults {
        val xmlItems = DocumentBuilderFactory
            .newInstance()
            .newDocumentBuilder()
            .parse(file)
            .getElementsByTagName("item")

        val itemsCount = xmlItems.length

        val houses = HashMap<AddressData, Int>()

        for (index in 0 until itemsCount) {
            val key: AddressData = with(xmlItems.item(index).attributes) {
                AddressData(
                    getNamedItem("city").textContent,
                    getNamedItem("street").textContent,
                    getNamedItem("house").textContent.toInt(),
                    getNamedItem("floor").textContent.toInt()
                )
            }

            if (houses[key] == null) {
                houses[key] = 1
            } else {
                houses[key] = houses[key]!! + 1
            }
        }

        return ParseResults(houses)
    }
}