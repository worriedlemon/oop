package lab256.shapes

data class ShapeCollector<T : ColoredShape2D>(
    private var _shapes: MutableList<T> = mutableListOf()
) {
    fun addAll(collection: Collection<T>) {
        _shapes.addAll(collection)
    }

    fun addAll(collector: ShapeCollector<out T>) {
        _shapes.addAll(collector.getShapes())
    }

    fun getSorted(comparator: Comparator<in T>): List<T> {
        return _shapes.sortedWith(comparator)
    }

    fun add(shape: T) {
        _shapes.add(shape)
    }

    fun findShapeWithMinArea(): T? {
        return _shapes.minByOrNull { it.calcArea() }
    }

    fun findShapeWithMaxArea(): T? {
        return _shapes.maxByOrNull { it.calcArea() }
    }

    fun getAreasSum(): Double {
        if (_shapes.isEmpty()) error("There are no shapes yet")

        return _shapes.sumOf { it.calcArea() }
    }

    fun findShapesWithBorderColor(borderColor: Color): List<T> {
        return _shapes.filter { it.borderColor == borderColor }
    }

    fun findShapesWithFillColor(fillColor: Color): List<T> {
        return _shapes.filter { it.fillColor == fillColor }
    }

    fun getShapes(): List<T> = _shapes

    fun getShapesCount(): Int = _shapes.size

    fun getShapesGroupedByBorderColor(): Map<Color, List<T>> {
        return _shapes.groupBy { it.borderColor }
    }

    fun getShapesGroupedByFillColor(): Map<Color, List<T>> {
        return _shapes.groupBy { it.fillColor }
    }

    inline fun <reified Type : ColoredShape2D> getAllShapesByType(): List<Type> {
        return this.getShapes().filterIsInstance<Type>()
    }
}