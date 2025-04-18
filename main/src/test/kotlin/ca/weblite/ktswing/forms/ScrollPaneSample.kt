package ca.weblite.ktswing.forms

import ca.weblite.ktswing.*
import ca.weblite.ktswing.extensions.at
import java.awt.BorderLayout
import javax.swing.BoxLayout
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main() {
    SwingUtilities.invokeLater {
        val frame = JFrame("ScrollPane Form Test").apply {
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            setSize(400, 300)

            contentPane.apply {
                layout = BorderLayout()
                button {
                    text = "North"
                } at BorderLayout.NORTH

                button {
                    text = "South"
                } at BorderLayout.SOUTH

                button {
                    text = "East"
                } at BorderLayout.EAST

                button {
                    text = "West"
                } at BorderLayout.WEST

                scrollPane {
                    panel {
                        layout = BoxLayout(this, BoxLayout.Y_AXIS)
                        for (i in 1..100) {
                            label {
                                text = "This is a test $i"
                            }
                        }
                    }
                } at BorderLayout.CENTER
            }
        }

        frame.isVisible = true
    }
}