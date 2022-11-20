package lab2.Readers

interface BigFileReader {
    fun readInfoFrom(file: java.io.File): lab2.ParseResults
}