package lab2

data class AddressData(
    val city: String,
    val street: String,
    val house: Int,
    val floor: Int
) {
    override fun toString(): String {
        return "c. $city, st. $street, $house, $floor-floored"
    }
}