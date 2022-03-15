package lab1.address

class Address(
    var index: Int,
    var city: String,
    var street: String,
    var house: Int
) {
    fun full(): String {
        return "$index, $city, $street, $house"
    }
}

fun parseAddresses(addresses: String): List<Address> {
    val addressesList: MutableList<Address> = emptyList<Address>().toMutableList()
    val addressesInString: List<String> = addresses.split('\n')
    for (s in addressesInString) {
        val splitAddresses: Array<String> = s.split(',').toTypedArray()
        if (splitAddresses.size != 4) throw ArrayIndexOutOfBoundsException("Arguments count it wrong")
        addressesList.add(
            Address(
                splitAddresses[0].trim().toInt(),
                splitAddresses[1].trim(),
                splitAddresses[2].trim(),
                splitAddresses[3].trim().toInt()
            )
        )
    }
    return addressesList.toList()
}