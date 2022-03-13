// Address class, which contains [Index], [City], [Street] and [House Number]
class Address(
    var index: Int,
    var city: String,
    var street: String,
    var house: Int
) {
    fun getFullAddress(): String {
        return "$index, $city, $street st., $house"
    }
}

// Function for parsing one string line into Address structure
fun parse(str: String): Address {
    val strArray: Array<String> = str.split(", ").toTypedArray()
    if (strArray.size != 4) throw IndexOutOfBoundsException("Argument count is wrong!")
    return Address(
        strArray[0].toInt(),
        strArray[1],
        strArray[2].dropLast(4),
        strArray[3].toInt()
    )
}

fun parseAddresses(addresses: String) : List<Address> {
    var addressesList: List<Address> = emptyList()
    val addressesString = addresses.split('\n')
    addressesString.forEach { value ->
        addressesList += parse(value)
    }
    return addressesList
}

fun readMultiline() : String {
    var result = "";
    var input: String = readln()
    while (input != "") {
        result += input + '\n'
        input = readln()
    }
    return result.dropLast(1)
}

fun main() {
    val addressesList = parseAddresses(readMultiline())
    addressesList.forEach { value ->
        println(value.getFullAddress())
    }
}