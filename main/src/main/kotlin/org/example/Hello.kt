package org.example
import ca.weblite.ktswing.*
import javax.swing.JFrame
import javax.swing.SwingUtilities
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
                panel {
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
                    }

                    label {
                        text = "Enter your name:"
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


            isVisible = true
        }
    }
}

