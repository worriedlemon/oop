package lab34

const val TOKEN_FILE = "labs_2022-fall/src/main/resources/gitignored/token.txt"
val EXCLUDED_LETTERS = setOf('ь', 'ъ', 'ы')
//const val SERVERNAME = "HUAWEI-MATEBOOK\\SQLEXPRESS"
const val SERVERNAME = "localhost"

fun String.lastCharExclude(chars: Set<Char> = EXCLUDED_LETTERS) : Char {
    return if (this.last().lowercaseChar() in EXCLUDED_LETTERS) {
        this[this.length - 2]
    } else {
        this.last().lowercaseChar()
    }
}