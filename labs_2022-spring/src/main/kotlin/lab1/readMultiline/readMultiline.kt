package lab1.readMultiline

fun readMultiline(): String {
    var result = ""
    var input = readln()
    while (input != "") {
        result += input + '\n'
        input = readln()
    }
    return result.dropLast(1)
}