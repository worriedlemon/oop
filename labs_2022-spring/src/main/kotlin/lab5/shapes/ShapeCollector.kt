package lab5.shapes

data class ShapeCollector<out T : ColoredShape2D>(
    private val _shapes: MutableList<T> = mutableListOf()
) {
    fun addAll(collection: Collection<@UnsafeVariance T>) {
        _shapes.addAll(collection)
    }

    fun addAll(collector: ShapeCollector<@UnsafeVariance T>) {
        _shapes.addAll(collector.getShapes())
    }

    fun getSorted(comparator: Comparator<in T>): ShapeCollector<T> {
        val sortedShapes = _shapes.sortedWith(comparator).toMutableList()
        return ShapeCollector(sortedShapes)
    }

    fun add(shape: @UnsafeVariance T) {
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