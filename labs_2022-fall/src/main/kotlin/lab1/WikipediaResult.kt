package lab1

import com.google.gson.JsonObject

// A class which represents Wikipedia request result, containing page title and index
class WikipediaResult(
    private val pageTitle: String,
    val pageId: String
) {
    companion object {
        fun fromJsonObject(obj: JsonObject): WikipediaResult {
            return WikipediaResult(
                obj.getAsJsonPrimitive("title").asString,
                obj.getAsJsonPrimitive("pageid").asString
            )
        }
    }

    override fun toString(): String {
        return "$pageId: \"$pageTitle\""
    }
}