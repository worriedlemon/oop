package lab34

import dev.inmo.tgbotapi.extensions.api.send.sendTextMessage
import dev.inmo.tgbotapi.extensions.behaviour_builder.BehaviourContext
import dev.inmo.tgbotapi.types.buttons.ReplyKeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.SimpleKeyboardButton
import dev.inmo.tgbotapi.types.chat.Chat
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row

object BotModes {
    enum class BotKnownModes(
        val keyboardMarkup: ReplyKeyboardMarkup
    ) {
        MENU(
            ReplyKeyboardMarkup(
                matrix {
                    row {
                        +SimpleKeyboardButton("Правила")
                        +SimpleKeyboardButton("Играть")
                    }
                }
            )
        ),
        PLAY(
            ReplyKeyboardMarkup(
                matrix {
                    row {
                        +SimpleKeyboardButton("Закончить")
                    }
                }
            )
        )
    }

    val botMode : String
        get() = getCurrentMode()

    private var m_botMode : BotKnownModes

    init {
        m_botMode = BotKnownModes.MENU
    }

    suspend fun setMode(mode: String, bc: BehaviourContext, chat: Chat, message: String? = null) {
        when (mode) {
            "PLAY" -> {
                m_botMode = BotKnownModes.PLAY
                botMessage(
                    bc,
                    chat,
                    message ?: "Поехали! Ты первый.\nКогда устанешь, можешь написать <<Закончить>> или нажать соответствующую кнопку."
                )
            }
            "MENU" -> {
                m_botMode = BotKnownModes.MENU
                botMessage(
                    bc,
                    chat,
                    message ?: "Хорошо поиграли! Возвращайся еще!"
                )
            }
            else -> throw java.lang.IllegalArgumentException("Invalid bot mode, can be only MENU or PLAY")
        }
    }

    private fun getCurrentMode() = when (m_botMode) {
        BotKnownModes.PLAY -> "PLAY"
        BotKnownModes.MENU -> "MENU"
    }

    suspend fun botMessage(bc: BehaviourContext, chat: Chat, message: String) {
        bc.sendTextMessage(
            chat,
            message,
            replyMarkup = m_botMode.keyboardMarkup
        )
    }

    suspend fun unknownCommandOrText(bc: BehaviourContext, chat: Chat) {
        val randomText = listOf("А?", "Что?", "Я не понимаю.", "Думаю, ты забыл нажать кнопку.")
        bc.sendTextMessage(
            chat,
            randomText.random(),
            replyMarkup = m_botMode.keyboardMarkup
        )
    }
}

