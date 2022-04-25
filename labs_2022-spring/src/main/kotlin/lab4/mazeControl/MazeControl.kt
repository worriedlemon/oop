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

    enum class Controls(val keys: Set<Int>) {
        GO_LEFT(setOf(KeyEvent.VK_LEFT, KeyEvent.VK_A)),
        GO_RIGHT(setOf(KeyEvent.VK_RIGHT, KeyEvent.VK_D)),
        GO_DOWN(setOf(KeyEvent.VK_DOWN, KeyEvent.VK_S)),
        GO_UP(setOf(KeyEvent.VK_UP, KeyEvent.VK_W)),
    }

    fun start() {
        view.addKeyListener(this)
    }

    override fun keyTyped(e: KeyEvent?) {}

    override fun keyPressed(e: KeyEvent?) {
        when (e!!.keyCode) {
            in Controls.GO_LEFT.keys -> direction = MoveDirection.LEFT
            in Controls.GO_RIGHT.keys  -> direction = MoveDirection.RIGHT
            in Controls.GO_DOWN.keys -> direction = MoveDirection.DOWN
            in Controls.GO_UP.keys -> direction = MoveDirection.UP
        }

        try {
            model.move(direction)
        } catch (_: Exception) {}
    }

    override fun keyReleased(e: KeyEvent?) {
        direction = MoveDirection.IDLE
    }
}