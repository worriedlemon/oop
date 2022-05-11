package lab4.mazeView

import lab4.mazeControl.MazeControl
import lab4.mazeModel.*
import java.awt.BorderLayout
import java.awt.GridLayout
import java.awt.Image.SCALE_SMOOTH
import javax.swing.*
import kotlin.system.exitProcess

const val TILE_SIZE = 16
const val RES_X = TILE_SIZE * CELLS_X_FULL
const val RES_Y = TILE_SIZE * CELLS_Y_FULL

class MazeView : JFrame("Maze Game"), ModelChangeListener {
    class Textures(
        val wall: Icon,
        val emptySpace: Icon,
        val character: Icon,
        val finish: Icon,
    )

    private fun loadSprites(): Textures {
        return Textures(
            ImageIcon(
                ImageIcon("src/main/resources/lab4/bricks.png").image.getScaledInstance(
                    TILE_SIZE,
                    TILE_SIZE,
                    SCALE_SMOOTH
                )
            ),
            ImageIcon(
                ImageIcon("src/main/resources/lab4/grass.png").image.getScaledInstance(
                    TILE_SIZE,
                    TILE_SIZE,
                    SCALE_SMOOTH
                )
            ),
            ImageIcon(
                ImageIcon("src/main/resources/lab4/steve.png").image.getScaledInstance(
                    TILE_SIZE,
                    TILE_SIZE,
                    SCALE_SMOOTH
                )
            ),
            ImageIcon(
                ImageIcon("src/main/resources/lab4/finish.png").image.getScaledInstance(
                    TILE_SIZE,
                    TILE_SIZE,
                    SCALE_SMOOTH
                )
            )
        )
    }

    private var mazeModel: MazeModel
    private val textures = loadSprites()
    private lateinit var player: JComponent
    private var controller: MazeControl? = null

    init {
        mazeModel = MazeModel()

        layout = null
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(RES_X, RES_Y + 30)
        setLocationRelativeTo(null)
        isResizable = false
        isVisible = true

        resubscribe()
    }

    private fun createUIField(): JComponent {
        // Layered panel
        val mazeLayerContainer = JLayeredPane()
        mazeLayerContainer.setBounds(0, 0, RES_X, RES_Y)

        // Maze UI panel
        val mazePanel = JPanel(GridLayout(CELLS_Y_FULL, CELLS_X_FULL, 0, 0))
        mazePanel.setBounds(0, 0, RES_X, RES_Y)

        for (i in 0 until CELLS_Y_FULL) {
            for (j in 0 until CELLS_X_FULL) {
                val label = if (
                    i == 0 ||
                    i == CELLS_Y_FULL - 1 ||
                    j == 0 ||
                    j == CELLS_X_FULL - 1
                ) JLabel(textures.wall) else JLabel(
                    when (mazeModel.field[i - 1][j - 1]) {
                        '#' -> textures.wall
                        ' ' -> textures.emptySpace
                        else -> {
                            throw IllegalStateException()
                        }
                    }
                )
                mazePanel.add(label)
            }
        }

        mazeLayerContainer.add(mazePanel, 0, 0)

        // Finish UI panel
        val finishPanel = JPanel().apply {
            isOpaque = false
            setBounds(
                TILE_SIZE * (mazeModel.finishPosition.x + 1),
                TILE_SIZE * (mazeModel.finishPosition.y + 1) - 2,
                TILE_SIZE,
                TILE_SIZE
            )
            add(JLabel(textures.finish))
        }

        mazeLayerContainer.add(finishPanel, 1, 1)

        // Player UI panel
        val playerPanel = JPanel().apply {
            isOpaque = false
            setBounds(
                TILE_SIZE * (mazeModel.currentPosition.x + 1),
                TILE_SIZE * (mazeModel.currentPosition.y + 1) - 2,
                TILE_SIZE,
                TILE_SIZE
            )
            add(JLabel(textures.character))
        }

        mazeLayerContainer.add(playerPanel, 2, 2)
        player = playerPanel
        return mazeLayerContainer
    }

    private fun resubscribe() {
        mazeModel.removeModelChangeListener(this)
        mazeModel = MazeModel()
        mazeModel.addModelChangeListener(this)
        rootPane.contentPane.removeAll()
        rootPane.contentPane.add(createUIField(), BorderLayout.CENTER)
        revalidate()
        repaint()
        removeKeyListener(controller)
        controller = MazeControl(this, mazeModel)
    }

    private fun updatePlayerPosition() {
        for (i in 0 until CELLS_Y) {
            for (j in 0 until CELLS_X) {
                if (mazeModel.currentPosition.x == j && mazeModel.currentPosition.y == i) {
                    player.setBounds(
                        TILE_SIZE * (mazeModel.currentPosition.x + 1),
                        TILE_SIZE * (mazeModel.currentPosition.y + 1) - 2,
                        TILE_SIZE,
                        TILE_SIZE
                    )
                }
            }
        }
    }

    private fun showRetryDialog()
    {
        val dialog = JOptionPane.showConfirmDialog(
            this,
            "You won! Do you want to try again?",
            "Restart",
            JOptionPane.YES_NO_OPTION
        )

        if (dialog == JOptionPane.YES_OPTION) {
            resubscribe()
        } else exitProcess(0)
    }

    override fun onModelChanged() {
        updatePlayerPosition()
        if (mazeModel.state == ModelState.FINISHED) showRetryDialog()
    }
}