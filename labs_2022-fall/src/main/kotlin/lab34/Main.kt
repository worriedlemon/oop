package lab34

import dev.inmo.tgbotapi.bot.ktor.telegramBot
import dev.inmo.tgbotapi.extensions.api.bot.getMe
import dev.inmo.tgbotapi.extensions.behaviour_builder.buildBehaviourWithLongPolling
import dev.inmo.tgbotapi.extensions.behaviour_builder.expectations.waitText
import dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling.onCommand
import dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling.onText
import io.ktor.server.plugins.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import lab34.botbehaviors.*
import java.io.FileInputStream
import java.io.FileNotFoundException

suspend fun main() {

    println("Telegram Bot processor is pre-initializing...\nChoose bot behaviour:\n(1) Build-In behaviour\n(2) SQL behaviour")

    val botBehavior: BotBehavior = getBotBehaviour()

    println("OK")

    val token = try {
        println("Reading token...")
        String(withContext(Dispatchers.IO) {
            FileInputStream(TOKEN_FILE).readAllBytes()
        })
    } catch (_: FileNotFoundException) {
        println("Incorrect path: $TOKEN_FILE")
        return
    }

    println("OK\nInitializing bot...")

    val bot = telegramBot(token)
    var lastChar = ' '

    bot.buildBehaviourWithLongPolling {
        println("Done\n${getMe()}")

        val rules =
            "Правила просты: ты называешь название города, следом я называю город на последнюю букву (кроме случаев с буквами $EXCLUDED_LETTERS, тогда берется предпоследняя). И так по кругу, пока хватает воображения и знаний."

        onCommand("start") {
            BotModes.botMessage(
                this,
                it.chat,
                """
                Привет!
                Я могу с тобой поиграть в города.

                Чтобы начать играть, нажми кнопку <<Играть>> или введи команду /play. 
                Можешь почитать правила, написав <<Правила>>, или нажав такую кнопку во время игры.
                
                Вперед!
            """.trimIndent()
            )
        }

        onCommand("play") {
            BotModes.setMode("PLAY", this, it.chat)
            botBehavior.clearNamedCities()
        }

        onCommand("rules") {
            BotModes.botMessage(
                this,
                it.chat,
                rules
            )
        }

        onText {
            val text = waitText {
                null
            }.first().text

            if (text in listOf("/play", "/rules", "/start")) return@onText

            if (BotModes.botMode == "PLAY") {
                if (text.lowercase() == "закончить") {
                    BotModes.setMode("MENU", this, it.chat)
                    botBehavior.clearNamedCities()
                    lastChar = ' '
                } else {
                    if (lastChar != ' ' && lastChar != text.first().lowercaseChar() && lastChar !in EXCLUDED_LETTERS) {
                        BotModes.botMessage(
                            this,
                            it.chat,
                            "Твой город должен начинаться на букву '${lastChar.uppercaseChar()}'.\nПопробуй еще раз!"
                        )
                    } else if (!botBehavior.checkCity(text)) {
                        BotModes.botMessage(
                            this,
                            it.chat,
                            "Этот город мы уже называли. Придумай другой!"
                        )
                    } else {
                        try {
                            val chosen = botBehavior.chooseCity(text)

                            if (chosen == null) {
                                BotModes.setMode(
                                    "MENU",
                                    this,
                                    it.chat,
                                    "Не могу вспомнить подходящих городов...\nКажется, ты выиграл!"
                                )
                            } else {
                                lastChar = chosen.lastCharExclude()
                                botBehavior.addCity(chosen)
                                BotModes.botMessage(
                                    this,
                                    it.chat,
                                    chosen
                                )
                            }
                        } catch (_ : NotFoundException) {
                            BotModes.botMessage(
                                this,
                                it.chat,
                                "Не знаю такого города... Может еще разок?"
                            )
                        }
                    }
                }
            } else {
                when (text.lowercase()) {
                    "играть" -> {
                        BotModes.setMode("PLAY", this, it.chat)
                        botBehavior.clearNamedCities()
                    }
                    "правила" -> BotModes.botMessage(this, it.chat, rules)
                    else -> BotModes.unknownCommandOrText(this, it.chat)
                }
            }
        }
    }.join()
}