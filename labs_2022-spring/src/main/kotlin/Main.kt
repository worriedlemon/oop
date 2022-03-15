import lab1.address.*

fun main() {
    val addressesString = """
        188480, Kingisepp, Krikkovskoye roadway, 24
        920721, Los Angeles, Sunset Boulevard, 75
        168512, Moscow, New Arbat, 211
        151716, Saint-Petersburg, Klinskiy alley, 19
    """.trimIndent()
    val addresses = parseAddresses(addressesString)

    if (addresses.isEmpty()) return

    println("Found cities are:")
    addresses.map { it.city }.forEach { println(it) }
    println("\nFiltered by index < 200000")
    addresses.filter { it.index < 200000 }.forEach { println(it.full()) }

    with(addresses) {
        println(
            "\nAddress with the largest index: "
                    + maxByOrNull { it.index }!!.full()
                    + "\nAddress with the shortest index: "
                    + minByOrNull { it.index }!!.full()
        )
        println(
            "\nAddress with the longest street name: "
                    + maxByOrNull { it.street.length }!!.full()
                    + "\nAddress with the shortest street name: "
                    + minByOrNull { it.street.length }!!.full()
        )
    }
}