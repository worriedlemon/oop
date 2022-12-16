package lab34.botbehaviors

interface BotBehavior {
    fun chooseCity(text: String): String?
    fun addCity(text: String)
    fun clearNamedCities()
    fun checkCity(text: String): Boolean
}
