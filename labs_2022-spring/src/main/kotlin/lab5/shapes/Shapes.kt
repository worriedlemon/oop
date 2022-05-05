package lab5.shapes

import kotlin.math.sqrt

data class Color(
    val red: Int,
    val green: Int,
    val blue: Int,
    val opacity: Double
) {
    init {
        require(
            red in 0..255 && green in 0..255 && blue in 0..255
        ) { "Color components support 256 values (from 0 to 255)" }
        require(opacity in 0.0..1.0) { "Opacity should be in range from 0 to 1" }
    }

    companion object {
        // Grayscale colors
        val BLACK = Color(0, 0, 0, 1.0)
        val GRAY = Color(127, 127, 127, 1.0)
        val WHITE = Color(255, 255, 255, 1.0)

        // RGB colors
        val RED = Color(255, 0, 0, 1.0)
        val GREEN = Color(0, 255, 0, 1.0)
        val BLUE = Color(0, 0, 255, 1.0)

        /* CMY colors
        val CYAN = Color(0, 255, 255, 1.0)
        val MAGENTA = Color(255, 0, 255, 1.0)
        val YELLOW = Color(255, 255, 0, 1.0)
        */
    }

    override fun toString(): String {
        return "#${"%02X".format(red)}${"%02X".format(green)}${"%02X".format(blue)} ${opacity*100}%"
    }
}

interface Shape2D {
    fun calcArea(): Double
}

interface ColoredShape2D : Shape2D {
    val borderColor: Color
    val fillColor: Color
}

class Circle(
    override val borderColor: Color,
    override val fillColor: Color,
    val radius: Double
) : ColoredShape2D {

    init {
        require(radius > 0.0) { "Radius has to be positive" }
    }

    override fun calcArea(): Double {
        return radius * 2 * Math.PI
    }

    override fun toString(): String {
        return "Circle of ($radius), BC=${borderColor}, FC=${fillColor}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Circle
        return (((radius == other.radius) && (fillColor == other.fillColor) && (borderColor == other.borderColor)))
    }

    override fun hashCode(): Int {
        var result = borderColor.hashCode()
        result = 31 * result + fillColor.hashCode()
        result = 31 * result + radius.hashCode()
        return result
    }
}

// Geometrically, a square is a special case of a rectangle, so technically it IS a rectangle
class Square(
    override val borderColor: Color,
    override val fillColor: Color,
    private val side: Double
) : Rectangle(borderColor, fillColor, side, side) {

    override fun calcArea(): Double {
        return side * side
    }

    override fun toString(): String {
        return "Square of ($side), BC=${borderColor}, FC=${fillColor}"
    }

    override fun hashCode(): Int {
        var result = borderColor.hashCode()
        result = 31 * result + fillColor.hashCode()
        result = 31 * result + side.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rectangle
        return (((side == other.side1) && (side == other.side2) && (fillColor == other.fillColor) && (borderColor == other.borderColor)))
    }
}

open class Rectangle(
    override val borderColor: Color,
    override val fillColor: Color,
    val side1: Double,
    val side2: Double
) : ColoredShape2D {

    init {
        require(side1 > 0.0 && side2 > 0.0) { "Sides have to be positive" }
    }

    override fun calcArea(): Double {
        return side1 * side2
    }

    override fun toString(): String {
        return "Rectangle of ($side1 x $side2), BC=${borderColor}, FC=${fillColor}"
    }

    override fun hashCode(): Int {
        var result = borderColor.hashCode()
        result = 31 * result + fillColor.hashCode()
        result = 31 * result + side1.hashCode()
        result = 31 * result + side2.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rectangle
        return (((side1 == other.side1) && (side2 == other.side2) && (fillColor == other.fillColor) && (borderColor == other.borderColor)))
    }
}

class Triangle(
    override val borderColor: Color,
    override val fillColor: Color,
    private val side1: Double,
    private val side2: Double,
    private val side3: Double
) : ColoredShape2D {

    private fun satisfiesTriangleRule(
        x: Double,
        y: Double,
        z: Double
    ): Boolean {
        return x + y > z && x + z > y && y + z > x
    }

    init {
        require (side1 > 0.0 && side2 > 0.0 && side3 > 0.0 && satisfiesTriangleRule(side1, side2, side3)) {
            "It is impossible to create a triangle with these parameters"
        }
    }

    constructor(
        borderColor: Color,
        fillColor: Color,
        _side1: Double
    ) : this(
        borderColor,
        fillColor,
        _side1,
        _side1,
        _side1
    )

    // Representation of a Heron's formula
    override fun calcArea(): Double {
        val p = (side1 + side2 + side3) / 2
        return sqrt(p * (p - side1) * (p - side2) * (p - side3))
    }

    override fun toString(): String {
        return "Triangle of ($side1, $side2, $side3), BC=${borderColor}, FC=${fillColor}"
    }

    override fun hashCode(): Int {
        var result = borderColor.hashCode()
        result = 31 * result + fillColor.hashCode()
        result = 31 * result + side1.hashCode()
        result = 31 * result + side2.hashCode()
        result = 31 * result + side3.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Triangle
        return (((side1 == other.side1) && (side2 == other.side2) && (side3 == other.side3) && (fillColor == other.fillColor) && (borderColor == other.borderColor)))
    }
}