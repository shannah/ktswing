package org.example
import ca.weblite.swinky.*
import ca.weblite.swinky.extensions.classList
import ca.weblite.swinky.extensions.factory
import ca.weblite.swinky.style.Stylesheet
import java.awt.Container
import javax.swing.*
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    SwingUtilities.invokeLater {
        // Create a JFrame as a top-level window
        val frame = JFrame("Kotlin Swing DSL Example").apply {
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            setSize(400, 300)

            // Because JFrame is a Container, we can call DSL extension functions on it.
            // For example, "panel { ... }", "button { ... }", "label { ... }", etc.
            with (contentPane) {
                factory(JButton::class.java) {
                    val btn = JButton()
                    btn.border = BorderFactory.createLineBorder(java.awt.Color.BLUE)
                    btn
                }
                panel {
                    classList.add("super-panel")
                    label {
                        text = "Hello, DSL!"
                    }

                    panel {
                        label {
                            text = "This is a nested panel."
                        }
                        borderPane {
                            north = label {
                                text = "North"
                            }
                            center = panel {
                                button {
                                    text = "Click Me"
                                    addActionListener {
                                        println("Button clicked!")
                                    }
                                }
                            }
                        }
                        splitPane{
                            leftComponent = label {
                                text = "Left"
                            }
                            rightComponent = label {
                                text = "Right"
                            }
                        }
                    }

                    button {
                        text = "OK"
                        addActionListener {
                            println("OK clicked!")
                        }
                        classList.add("green")
                    }

                    label {
                        text = "Enter your name:"
                        classList.add("red")
                    }

                    textField {
                        columns = 20
                        text = "John Doe"
                    }

                    button {
                        text = "Quit"
                        addActionListener {
                            exitProcess(0)
                        }
                    }

                    comboBox<String> {
                        addItem("Item 1")
                        addItem("Item 2")
                        addItem("Item 3")
                        addItem("Item 4")
                        addItem("Item 5")
                    }
                }
            }

            val styles = Stylesheet()
            styles.register(JButton::class.java) {
                font = font.deriveFont(20f)
            }
            styles.register(".green", JButton::class.java) {
                foreground = java.awt.Color.GREEN
                font = font.deriveFont(40f)
            }
            styles.register(".super-panel", JPanel::class.java) {
                border = BorderFactory.createLineBorder(java.awt.Color.RED)
            }
            styles.register(".super-panel > *", JLabel::class.java){
                font = font.deriveFont(30f)
            }
            styles.register(".red", JLabel::class.java) {
                foreground = java.awt.Color.RED
            }
            styles.apply(this)

            val styles2 = Stylesheet(){
                button {
                    font = font.deriveFont(30f)
                }

                button(".green") {
                    foreground = java.awt.Color.GREEN
                    font = font.deriveFont(50f)
                }

                panel(".super-panel") {
                    border = BorderFactory.createLineBorder(java.awt.Color.RED)

                }

                label(".super-panel > *") {
                    font = font.deriveFont(40f)
                }

                label(".red") {
                    foreground = java.awt.Color.RED
                }

            }
            isVisible = true
        }
    }
}

