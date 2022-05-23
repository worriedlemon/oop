package lab4.mazeModel

import java.lang.Math.random
import kotlin.math.roundToInt

// Sizes of maze should be odd
const val CELLS_X = 71
const val CELLS_Y = 37

const val CELLS_X_FULL = (CELLS_X + 2)
const val CELLS_Y_FULL = (CELLS_Y + 2)

enum class ModelState {
    IDLE, MADE_MOVE, FINISHED
}

enum class MoveDirection(private val x: Int, private val y: Int) {
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, -1),
    DOWN(0, 1),
    IDLE(0, 0);

    fun getParameters(): Array<Int> {
        return arrayOf(x, y)
    }
}

interface ModelChangeListener {
    fun onModelChanged()
}

data class Coordinates(
    val x: Int,
    val y: Int
)

class MazeModel {
    var currentPosition = Coordinates(0, 0)
        private set

    var finishPosition = Coordinates(0, 0)
        private set

    var state: ModelState = ModelState.IDLE
        private set

    private val maze: Array<Array<Char>> = Array(CELLS_Y) { Array(CELLS_X) { ' ' } }
    val field: Array<Array<Char>>
        get() = maze

    init {
        generateMaze()
    }

    private val listeners: MutableSet<ModelChangeListener> = mutableSetOf()

    fun addModelChangeListener(listener: ModelChangeListener) {
        listeners.add(listener)
    }

    fun removeModelChangeListener(listener: ModelChangeListener) {
        listeners.remove(listener)
    }

    private fun notifyListeners() {
        listeners.forEach { it.onModelChanged() }
    }

    // Eller Algorithm
    private fun generateMaze() {
        val auxRow: Array<Int> = Array(CELLS_X) { 0 }

        // Assigning numbers to first row
        for (column in 0 until CELLS_X step 2) {
            auxRow[column] = column / 2 + 1
        }

        for (row in 0 until CELLS_Y - 2 step 2) {
            // Creating vertical borders
            for (column in 0 until CELLS_X - 2 step 2) {
                if (random() < 0.5 || auxRow[column] == auxRow[column + 2]) {
                    auxRow[column + 1] = -1
                    maze[row][column + 1] = '#'
                } else {
                    auxRow[column + 1] = auxRow[column]
                    auxRow[column + 2] = auxRow[column]
                }
                maze[row + 1][column + 1] = '#'
            }

            // Creating horizontal borders
            var hasEntry = false

            for (column in 0 until CELLS_X step 2) {
                if (column != 0 && auxRow[column - 1] == -1) {
                    if (!hasEntry) {
                        maze[row + 1][column - 2] = ' '
                    } else hasEntry = false
                }

                if (random() < 0.8) {
                    maze[row + 1][column] = '#'
                } else hasEntry = true
            }

            if (!hasEntry) {
                maze[row + 1][CELLS_X - 1] = ' '
            }

            // Assigning numbers to a new row
            var borders = 0

            for (column in 2 until CELLS_X step 2) {
                if (row != CELLS_Y - 3) auxRow[column - 1] = 0
                if (maze[row + 1][column] == '#') {
                    auxRow[column] = auxRow[column - 2] + 1
                    borders++
                } else {
                    auxRow[column] += borders
                }
            }
        }

        currentPosition = Coordinates((random() * (CELLS_X - 1)).roundToInt(), CELLS_Y - 1)

        finishPosition = Coordinates((random() * (CELLS_X - 1)).roundToInt(), 0)
        if (maze[0][finishPosition.x] == '#')
        {
            finishPosition = Coordinates(if (finishPosition.x == CELLS_X - 1) -1 else 1, finishPosition.y)
        }
    }

    fun move(direction: MoveDirection) {
        require(state != ModelState.FINISHED) { "Maze is completed" }

        val shift = direction.getParameters()
        val newPosX = currentPosition.x + shift[0]
        val newPosY = currentPosition.y + shift[1]

        require(newPosX in 0 until CELLS_X) { "Out of bounds by X" }
        require(newPosY in 0 until CELLS_Y) { "Out of bounds by Y" }
        require(maze[newPosY][newPosX] != '#') { "Move in wall" }

        currentPosition = Coordinates(newPosX, newPosY)

        state = if (checkWin()) ModelState.FINISHED else ModelState.MADE_MOVE

        notifyListeners()
    }

    private fun checkWin(): Boolean = (currentPosition == finishPosition)
}