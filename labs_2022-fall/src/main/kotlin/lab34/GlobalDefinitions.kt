package lab34

const val RESOURCES_PATH = "labs_2022-fall/src/main/resources"
const val SERVER_NAME = "localhost" // May be {DESKTOP-NAME}\\SQLEXPRESS

fun String.lastCharExclude(): Char {
    return if (this.last().lowercaseChar() in excludedLetters()) {
        this[this.length - 2]
    } else {
        this.last().lowercaseChar()
    }
}

fun excludedLetters() : Set<Char> = setOf('ь', 'ъ', 'ы')