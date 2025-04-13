package ca.weblite.ktswing.coroutines
import ca.weblite.ktswing.coroutines.SwingDispatcher
import kotlinx.coroutines.*
import javax.swing.*
import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

fun main() {
    SwingUtilities.invokeLater {
        val frame = JFrame("Swing Coroutine Demo")
        val button = JButton("Load Data")

        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(300, 150)
        frame.layout = BorderLayout()
        frame.add(button, BorderLayout.CENTER)
        frame.setLocationRelativeTo(null)
        frame.isVisible = true

        // Set up coroutine scope bound to the SwingDispatcher
        val scope = CoroutineScope(SwingDispatcher)

        button.addActionListener(object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                scope.launch {
                    // We are on the EDT here

                    val progressDialog = showNonBlockingProgressDialog(frame, "Loading data...")

                    // Simulate background task
                    val result = withContext(Dispatchers.IO) {
                        // simulate slow task (this is suspendable, not blocking)
                        Thread.sleep(3000) // (could be delay(3000), but real blocking for realism)
                        "Data loaded!"
                    }

                    progressDialog.dispose() // back on EDT (thanks to SwingDispatcher)
                    JOptionPane.showMessageDialog(frame, result)
                }
            }
        })
    }
}

fun showNonBlockingProgressDialog(parent: JFrame, message: String): JDialog {
    val dialog = JDialog(parent, "Working...", false) // false = non-modal
    dialog.defaultCloseOperation = JDialog.DO_NOTHING_ON_CLOSE
    dialog.setSize(200, 100)
    dialog.setLocationRelativeTo(parent)
    dialog.layout = BorderLayout()
    dialog.add(JLabel(message, SwingConstants.CENTER), BorderLayout.CENTER)

    // Show it without blocking (important!)
    SwingUtilities.invokeLater {
        dialog.isVisible = true
    }

    return dialog
}

