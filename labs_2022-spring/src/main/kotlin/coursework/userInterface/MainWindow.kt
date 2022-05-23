package coursework.userInterface

import coursework.controller.*
import coursework.base.ClosingWindowListener
import coursework.base.FocusRequestByMouseClickListener
import coursework.base.MainWindowFocusListener
import java.awt.BorderLayout
import javax.swing.BorderFactory
import javax.swing.JFrame
import javax.swing.JPanel

class MainWindow : JFrame("Kotlin JPaint") {
    private val paintingPanel = MainPanel(this)
    private val menuPanel = MenuPanel(paintingPanel)
    private val keyListener = HotKeysListener(menuPanel, paintingPanel)
    val mouseListener = MainPanelMouseListener(paintingPanel, keyListener)

    init {
        // Setting up
        val mainContainer = JPanel().apply {
            border = BorderFactory.createEmptyBorder(0, 5, 5, 5)
            layout = BorderLayout()
        }
        isFocusable = true
        setSize(1280, 720)
        setLocationRelativeTo(null)
        rootPane.contentPane.add(mainContainer)
        defaultCloseOperation = DO_NOTHING_ON_CLOSE

        // Initializing listeners
        addWindowListener(ClosingWindowListener(this))
        addWindowFocusListener(MainWindowFocusListener(this))
        addMouseListener(FocusRequestByMouseClickListener(this))
        addKeyListener(keyListener)
        paintingPanel.initializeMouseListeners()

        // Adding panels
        jMenuBar = menuPanel
        mainContainer.apply {
            add(paintingPanel, BorderLayout.CENTER)
            add(Toolbar(paintingPanel), BorderLayout.NORTH)
            add(SelectionPanel(paintingPanel), BorderLayout.WEST)
        }

        // Displaying
        isVisible = true
    }
}