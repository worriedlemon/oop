package lab6.shapes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShapeCollector(
    @SerialName("shapes") private val _shapes: MutableList<ColoredShape2D> = mutableListOf()
) {
    fun add(shape: ColoredShape2D) {
        _shapes.add(shape)
    }

    fun findShapeWithMinArea(): ColoredShape2D? {
        return _shapes.minByOrNull { it.calcArea() }
    }

    fun findShapeWithMaxArea(): ColoredShape2D? {
        return _shapes.maxByOrNull { it.calcArea() }
    }

    fun getAreasSum(): Double {
        if (_shapes.isEmpty()) error("There are no shapes yet")

        return _shapes.sumOf { it.calcArea() }
    }

    fun findShapesWithBorderColor(borderColor: Color): List<ColoredShape2D> {
        return _shapes.filter { it.borderColor == borderColor }
    }

    fun findShapesWithFillColor(fillColor: Color): List<ColoredShape2D> {
        return _shapes.filter { it.fillColor == fillColor }
    }

    fun getShapes(): List<ColoredShape2D> = _shapes

    fun getShapesCount(): Int = _shapes.size

    fun getShapesGroupedByBorderColor(): Map<Color, List<ColoredShape2D>> {
        return _shapes.groupBy { it.borderColor }
    }

    fun getShapesGroupedByFillColor(): Map<Color, List<ColoredShape2D>> {
        return _shapes.groupBy { it.fillColor }
    }

    inline fun <reified T> getAllShapesByType(): List<ColoredShape2D> {
        return this.getShapes().filter { it is T }
    }

    override fun toString(): String {
        var string = "ShapeCollector consists of\n"
        _shapes.forEach {
            string += "\t$it\n"
        }
        return string.dropLast(1)
    }
}