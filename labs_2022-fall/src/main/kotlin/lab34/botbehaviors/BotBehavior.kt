package lab34.botbehaviors

interface BotBehavior {
    fun chooseCity(text: String, namedCities: Set<String>): String?
}
