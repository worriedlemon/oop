package coursework.base

import coursework.userInterface.MainWindow
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import java.awt.event.WindowEvent
import java.awt.event.WindowFocusListener

/* If window unfocuses because of other window or window component
 * interruption of keyboard listening tend to happen. This listeners
 * request for this focus back to main window
 */

class MainWindowFocusListener(private val window: MainWindow) : WindowFocusListener {
    override fun windowGainedFocus(e: WindowEvent?) {
        window.requestFocus()
    }

    override fun windowLostFocus(e: WindowEvent?) {}

}

class FrameFocusListener(private val window: MainWindow) : FocusListener {
    override fun focusGained(e: FocusEvent?) {}

    override fun focusLost(e: FocusEvent?) {
        window.requestFocus()
    }

}
