package ca.weblite.swinky.forms

import ca.weblite.swinky.button
import ca.weblite.swinky.extensions.at
import java.awt.BorderLayout
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main() {
    SwingUtilities.invokeLater {
        val frame = JFrame("BorderLayout Form Test").apply {
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

                button {
                    text = "Center"
                } at BorderLayout.CENTER
            }
        }

        frame.isVisible = true
    }
}