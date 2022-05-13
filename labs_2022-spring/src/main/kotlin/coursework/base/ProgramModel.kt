package coursework.base

import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

const val ICONS_PATH = "./src/main/resources/coursework/icons"
const val OUTPUT_PATH = "./src/main/resources/coursework/output"
const val DOCUMENTS_PATH = "./src/main/resources/coursework/doc"

enum class ShapeType {
    NONE, BUCKET, RECTANGLE, OVAL, LINE
}

object ProgramModel {
    // Buffers
    val undoLayers: MutableList<BufferedImage> = mutableListOf()
    val redoLayers: MutableList<BufferedImage> = mutableListOf()

    // Chosen shape
    var shapeType = ShapeType.NONE

    fun saveImage(
        fullPath: String,
        filename: String,
        format: String,
        image: BufferedImage
    ) {
        try {
            ImageIO.write(image, format, File("$fullPath/$filename.$format".replace("//", "/")))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}