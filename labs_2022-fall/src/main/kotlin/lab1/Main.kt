import lab1.WikipediaRequest

fun main() {
    println("Input your Wikipedia search phrase:")

    val input = readln()
    val request = WikipediaRequest(input)

    if (request.results.isEmpty()) {
        println("\nNo results found for \"$input\"")
    } else {
        println("Search \"$input\" results are:")
        request.results.forEachIndexed { index, result ->
            println("($index) $result")
        }

        println("\nInput index to open:")
        try {
            val inputtedIndex = readln().toInt()
            request.openWikipediaPage(inputtedIndex)
        } catch (_: NumberFormatException) {
            println("ERROR: Entered string is not a valid number")
        } catch (_: IndexOutOfBoundsException) {
            println("ERROR: Inputted index is out of range")
        }
    }
}