package lab2

data class ParseResults (
    val housesData: HashMap<AddressData, Int>
) {
    fun outputGroupedByFloors() {
        val counter = sortedMapOf<Int, Int>()
        housesData.forEach {
            if (counter[it.key.floor] == null) {
                counter[it.key.floor] = 1
            } else {
                counter.replace(it.key.floor, counter[it.key.floor]!! + 1)
            }
        }

        counter.forEach {
            println("${it.key}-floored: ${it.value} houses")
        }
    }

    fun outputRecurrences() {
        housesData.filter {
            it.value > 1
        }.forEach {
            println("${it.key} - ${it.value} times")
        }
    }
}

