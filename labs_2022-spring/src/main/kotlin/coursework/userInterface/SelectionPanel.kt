package coursework.userInterface

import coursework.base.*
import java.awt.Color
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.*

class SelectionPanel(private val paintingPanel: MainPanel) : JPanel(GridLayout(5, 1, 10, 10)) {
    init {
        preferredSize = Dimension(120, height)
        val menu = createShapesMenu()
        add(JButton(ImageIcon("$ICONS_PATH/brush.png")).apply {
            addActionListener {
                ProgramModel.shapeType = ShapeType.NONE
            }
        })
        add(JButton(ImageIcon("$ICONS_PATH/bucket.png")).apply {
            addActionListener {
                ProgramModel.shapeType = ShapeType.BUCKET
            }
        })
        add(JButton("Shapes").apply {
            addActionListener {
                menu.isVisible = true
            }
        })
        add(createOutlineColorChooser())
        add(createFillColorChooser())
    }

    private fun createOutlineColorChooser(): JButton {
        return JButton("Outline\nColor".toHtml()).apply {
            addActionListener {
                val chosenColor: Color? = JColorChooser.showDialog(
                    JFrame(),
                    "Outline Color",
                    paintingPanel.outlineColor
                )
                if (chosenColor != null) paintingPanel.outlineColor = chosenColor
            }
        }
    }

    private fun createFillColorChooser(): JButton {
        return JButton("Fill\nColor".toHtml()).apply {
            addActionListener {
                val chosenColor: Color? = JColorChooser.showDialog(
                    JFrame(),
                    "Fill Color",
                    paintingPanel.fillColor
                )
                if (chosenColor != null) paintingPanel.fillColor = chosenColor
            }
        }
    }

    private fun createShapesMenu(): JDialog {
        val dialog = JDialog(JFrame(), "Choose shape")
        return dialog.apply {
            setLocationRelativeTo(null)
            setSize(360, 160)
            layout = GridLayout(1, 3)
            contentPane.add(JButton(ImageIcon("$ICONS_PATH/rect.png")).apply {
                addActionListener {
                    ProgramModel.shapeType = ShapeType.RECTANGLE
                    dialog.isVisible = false
                }
            })
            contentPane.add(JButton(ImageIcon("$ICONS_PATH/oval.png")).apply {
                addActionListener {
                    ProgramModel.shapeType = ShapeType.OVAL
                    dialog.isVisible = false
                }
            })
            contentPane.add(JButton(ImageIcon("$ICONS_PATH/line.png")).apply {
                addActionListener {
                    ProgramModel.shapeType = ShapeType.LINE
                    dialog.isVisible = false
                }
            })
        }
    }
}