import lab1.address.*
import lab1.readMultiline.readMultiline

fun main() {
    val addresses = parseAddresses(readMultiline())

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