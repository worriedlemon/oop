package lab1

import com.google.gson.Gson
import com.google.gson.JsonObject
import java.awt.Desktop
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.Charset

// Class for requesting Wikipedia server for information from a specific search phrase
class WikipediaRequest(
    private val requestString: String
) {
    private val client = HttpClient.newBuilder().build()
    private val requestLink = "https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch="
    private val resultLink = "https://ru.wikipedia.org/w/index.php?curid="

    val results: List<WikipediaResult>

    init {
        val requestString = getResponse()
        results = getResults(requestString)
    }

    private fun getResponse(): String {
        val fullRequestLink = requestLink + URLEncoder.encode(
            "\"$requestString\"",
            Charset.defaultCharset()
        )

        val request = HttpRequest.newBuilder()
            .uri(URI.create(fullRequestLink))
            .build()

        return client.send(request, HttpResponse.BodyHandlers.ofString()).body()
    }

    private fun getResults(jsonString: String): List<WikipediaResult> {
        val jsonArray = Gson()
            .fromJson(jsonString, JsonObject::class.java)
            .getAsJsonObject("query")
            .getAsJsonArray("search")

        val results = emptyList<WikipediaResult>().toMutableList()

        jsonArray.forEach {
            results.add(
                WikipediaResult.fromJsonObject(it.asJsonObject)
            )
        }
        return results
    }

    fun openWikipediaPage(id: Int) {
        Desktop.getDesktop().browse(
            URI(resultLink + results[id].pageId)
        )
    }
}