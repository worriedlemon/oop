package lab2

fun main() {
    println("Enter a path to a CSV or XML file to read information from file")
    println("Use command \"exit\" to quit the program")

    while (true) {
        print("> ")

        when (val inputCommand = readln()) {
            "" -> println("Empty input: try again")
            "exit" -> break
            else -> parseInfoFrom(inputCommand)
        }
    }
}