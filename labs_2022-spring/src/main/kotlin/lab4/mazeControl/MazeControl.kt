package lab4.mazeControl

import lab4.mazeModel.*
import lab4.mazeView.MazeView
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class MazeControl(
    private val view: MazeView,
    private val model: MazeModel
) : KeyListener {
    private var direction = MoveDirection.IDLE

    fun start() {
        view.addKeyListener(this)
    }

    override fun keyTyped(e: KeyEvent?) {}

    override fun keyPressed(e: KeyEvent?) {
        if (e == null) return

        when (e.keyCode) {
            KeyEvent.VK_LEFT -> direction = MoveDirection.LEFT
            KeyEvent.VK_RIGHT -> direction = MoveDirection.RIGHT
            KeyEvent.VK_DOWN -> direction = MoveDirection.DOWN
            KeyEvent.VK_UP -> direction = MoveDirection.UP
        }

        try {
            model.move(direction)
        } catch (_: Exception) {}
    }

    override fun keyReleased(e: KeyEvent?) {
        direction = MoveDirection.IDLE
    }
}