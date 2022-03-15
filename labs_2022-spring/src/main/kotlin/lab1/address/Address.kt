package lab1.address

// Class which represents the address by such properties as [index], [city], [street] and [house]
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

// Function for parsing a [String] into list of addresses
fun parseAddresses(addresses: String): List<Address> {
    val addressesList: MutableList<Address> = mutableListOf()
    val addressesInString: List<String> = addresses.split('\n')
    for (subAddress in addressesInString) {
        val splitAddresses: Array<String> = subAddress.split(',').toTypedArray()
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