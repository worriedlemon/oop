package lab2.readers

interface BigFileReader {
    fun readInfoFrom(file: java.io.File): lab2.ParseResults
}