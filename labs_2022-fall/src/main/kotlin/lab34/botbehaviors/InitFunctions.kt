package lab34.botbehaviors

fun getBotBehaviour(): BotBehavior {
    while (true) {
        print("> ")
        when (readln()) {
            "1" -> return BuildInBehavior()
            "2" -> return SQLBehavior()
            else -> {
                println("Wrong input, try again.")
            }
        }
    }
}