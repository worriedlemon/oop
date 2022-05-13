package coursework.base

import java.awt.Polygon

// Shape interface
interface Shape2D {
    val x1: Int
    val x2: Int
    val y1: Int
    val y2: Int
}

// Shapes to draw on screen

class Rectangle(
    override val x1: Int,
    override val y1: Int,
    override val x2: Int,
    override val y2: Int
) : Polygon(
    intArrayOf(x1, x1, x2, x2),
    intArrayOf(y1, y2, y2, y1),
    4
), Shape2D

class Oval(
    override val x1: Int,
    override val y1: Int,
    override val x2: Int,
    override val y2: Int
) : Shape2D {
    val x = x1
    val y = y1
    val width = x2 - x1
    val height = y2 - y1
}

class Line(
    override val x1: Int,
    override val y1: Int,
    override val x2: Int,
    override val y2: Int
) : Polygon(
    intArrayOf(x1, x2),
    intArrayOf(y1, y2),
    2
), Shape2D