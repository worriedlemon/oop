package lab2.Readers

import lab2.AddressData
import lab2.ParseResults
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class XMLReader : BigFileReader {
    override fun readInfoFrom(file: File): ParseResults {
        val xmlRoot = DocumentBuilderFactory
            .newInstance()
            .newDocumentBuilder()
            .parse(file)
            .firstChild

        val houses = HashMap<AddressData, Int>()

        while (xmlRoot.hasChildNodes()) {
            val child = xmlRoot.firstChild

            if (child.nodeName != "item") {
                xmlRoot.removeChild(child)
                continue
            }

            val key: AddressData = with(child.attributes) {
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

            xmlRoot.removeChild(child)
        }

        return ParseResults(houses)
    }
}