package ca.weblite.swinky.jgoodies

import ca.weblite.swinky.editorPane
import ca.weblite.swinky.label
import ca.weblite.swinky.textField
import javax.swing.BorderFactory
import javax.swing.JFrame
import javax.swing.SwingUtilities


fun main() {
    SwingUtilities.invokeLater{
        val frame = JFrame("Test Form").apply {
            form("p, 3dlu, p") {
                border = BorderFactory.createEmptyBorder(5,5,5,5)
                row {
                    label {
                        text = "First Name"
                    } at x(1)

                    textField {
                        columns = 20
                    } at x(3)
                }
                row {
                    editorPane {
                        text = "This is a test"
                    } at xw(1, 3)
                }

                row {
                    label {
                        text = "Last Name"
                    } at x(1)

                    textField {
                        columns = 20
                    } at x(3)
                }

            }
            pack()
            isVisible = true
        }

    }
}
