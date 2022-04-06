package lab2.shapes

import kotlin.math.sqrt

data class Color(
    val _red: Int,
    val _green: Int,
    val _blue: Int,
    val _opacity: Double
)

// Grayscale colors
val black = Color(0, 0, 0, 1.0)
val gray = Color(127, 127, 127, 1.0)
val white = Color(255, 255, 255, 1.0)

// RGB colors
val red = Color(255, 0, 0, 1.0)
val green = Color(0, 255, 0, 1.0)
val blue = Color(0, 0, 255, 1.0)

// CMY colors
val cyan = Color(0, 255, 255, 1.0)
val magenta = Color(255, 0, 255, 1.0)
val yellow = Color(255, 255, 0, 1.0)

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
    _radius: Double
) : ColoredShape2D {
    private val radius: Double

    init {
        radius = if (_radius <= 0) error("Radius should be positive") else _radius
    }

    override fun calcArea(): Double {
        return radius * 2 * Math.PI
    }
}

// Geometrically, a square is an special case of a rectangle, so technically it IS a rectangle
class Square(
    override val borderColor: Color,
    override val fillColor: Color,
    _side: Double
) : Rectangle(borderColor, fillColor, _side, _side) {

    override fun calcArea(): Double {
        return x * x
    }
}

open class Rectangle(
    override val borderColor: Color,
    override val fillColor: Color,
    _side1: Double,
    _side2: Double
) : ColoredShape2D {

    protected val x: Double
    private val y: Double

    init {
        x = if (_side1 <= 0) error("Sides should be positive") else _side1
        y = if (_side2 <= 0) error("Sides should be positive") else _side2
    }

    override fun calcArea(): Double {
        return x * y
    }
}

class Triangle(
    override val borderColor: Color,
    override val fillColor: Color,
    _side1: Double,
    _side2: Double,
    _side3: Double
) : ColoredShape2D {
    private val side1: Double
    private val side2: Double
    private val side3: Double

    private fun satisfiesTriangleRule(
        x: Double,
        y: Double,
        z: Double
    ): Boolean {
        return x + y > z && x + z > y && y + z > x
    }

    init {
        if (_side1 > 0 && _side2 > 0 && _side3 > 0 && satisfiesTriangleRule(_side1, _side2, _side3)) {
            side1 = _side1
            side2 = _side2
            side3 = _side3
        } else error("It is impossible to make a triangle with these parameters")
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
}