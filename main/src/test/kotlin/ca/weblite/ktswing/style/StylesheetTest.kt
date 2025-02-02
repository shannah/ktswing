package ca.weblite.ktswing.style

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import java.awt.Component

/**
 * Unit tests for the Stylesheet class.
 */
class StylesheetTest {

    /**
     * Test that a style registered for a JButton (using a selector that matches by name)
     * is applied to the button.
     */
    @Test
    fun `test button style applied when matching`() {
        val stylesheet = Stylesheet()
        val button = JButton("Click Me")
        // Set the name to match the selector "#myButton" (CssSelector looks at Component.name for id selectors)
        button.name = "myButton"

        // Register a style for JButton matching "#myButton"
        stylesheet.button("#myButton") {
            // In our test, we simulate styling by setting a client property "styled" to true.
            this.putClientProperty("styled", true)
        }

        // Apply styles (the stylesheet will traverse the component tree)
        stylesheet.apply(button)

        // Verify that the button was styled
        assertEquals(true, button.getClientProperty("styled"),
                "Button with matching name should be styled")
    }

    /**
     * Test that a style is not applied when the selector does not match the component.
     */
    @Test
    fun `test button style not applied when not matching`() {
        val stylesheet = Stylesheet()
        val button = JButton("Click Me")
        button.name = "otherButton"

        // Register a style for JButton with selector "#myButton"
        stylesheet.button("#myButton") {
            this.putClientProperty("styled", true)
        }

        stylesheet.apply(button)

        // Since the button's name does not match, the style should not be applied.
        assertNull(button.getClientProperty("styled"),
                "Button with non-matching name should not be styled")
    }

    /**
     * Test that styles are applied recursively to child components.
     * In this case a panel contains a button that should be styled.
     */
    @Test
    fun `test style application on nested components`() {
        val stylesheet = Stylesheet()
        val panel = JPanel()
        val button = JButton("Nested Button")
        button.name = "myButton"
        panel.add(button)

        // Register a style for JButton matching "#myButton"
        stylesheet.button("#myButton") {
            this.putClientProperty("styled", true)
        }

        // Apply styles starting at the panel; the stylesheet should traverse into its children.
        stylesheet.apply(panel)

        assertEquals(true, button.getClientProperty("styled"),
                "Nested button should be styled when its parent is styled recursively")
    }

    /**
     * Test that a style registered without a selector (i.e. for all buttons) is applied.
     */
    @Test
    fun `test style registration without selector`() {
        val stylesheet = Stylesheet()
        val button = JButton("Any Button")
        // No name is set here; we use the helper method that registers a style with selector "*"
        stylesheet.button {
            this.putClientProperty("styled", true)
        }

        stylesheet.apply(button)

        assertEquals(true, button.getClientProperty("styled"),
                "Button should be styled even without a selector restriction")
    }

    /**
     * Test that multiple styles registered for the same component type are both applied.
     */
    @Test
    fun `test multiple styles on same component`() {
        val stylesheet = Stylesheet()
        val button = JButton("Multi-Style Button")
        button.name = "btn"

        // Register one style using a selector and another without a selector.
        stylesheet.button("#btn") {
            this.putClientProperty("styled1", true)
        }
        stylesheet.button {
            this.putClientProperty("styled2", true)
        }

        stylesheet.apply(button)

        assertEquals(true, button.getClientProperty("styled1"),
                "Button should receive first style based on selector")
        assertEquals(true, button.getClientProperty("styled2"),
                "Button should receive second style registered without a selector")
    }

    /**
     * Test that a style registered for a specific component type (e.g. JTextField) is not applied
     * to a component of a different type (e.g. JButton), even if the selector matches.
     */
    @Test
    fun `test style not applied to mismatched component type`() {
        val stylesheet = Stylesheet()
        val button = JButton("Not a TextField")
        // Even though we set the name so that the selector would match,
        // the style is registered for JTextField.
        button.name = "textField"

        stylesheet.textField("#textField") {
            this.putClientProperty("styled", true)
        }

        stylesheet.apply(button)

        // The button is not a JTextField, so the style should not be applied.
        assertNull(button.getClientProperty("styled"),
                "Style for JTextField should not be applied to a JButton")
    }

    /**
     * Test that styles are applied to components in deeply nested containers.
     */
    @Test
    fun `test recursive style application on nested containers`() {
        val stylesheet = Stylesheet()
        val outerPanel = JPanel()
        val innerPanel = JPanel()
        val button = JButton("Deep Button")
        button.name = "btn"
        innerPanel.add(button)
        outerPanel.add(innerPanel)

        // Register a style for JButton matching "#btn"
        stylesheet.button("#btn") {
            this.putClientProperty("styled", true)
        }

        // Apply styles starting from the outermost container.
        stylesheet.apply(outerPanel)

        assertEquals(true, button.getClientProperty("styled"),
                "Button in a deeply nested container should be styled")
    }

    /**
     * Test that the helper method for labels applies a style.
     */
    @Test
    fun `test label style applied`() {
        val stylesheet = Stylesheet()
        val label = JLabel("Test Label")
        label.name = "lbl"

        stylesheet.label("#lbl") {
            this.putClientProperty("styled", true)
        }

        stylesheet.apply(label)

        assertEquals(true, label.getClientProperty("styled"),
                "Label with matching selector should be styled")
    }

    /**
     * Test that when no styles are registered, applying the stylesheet does nothing.
     */
    @Test
    fun `test no style registered`() {
        val stylesheet = Stylesheet()
        val button = JButton("No Style Button")

        // Do not register any style.
        stylesheet.apply(button)

        // Since no style was registered, no client property should have been set.
        assertNull(button.getClientProperty("styled"),
                "Button should not be styled when no style is registered")
    }

    @Test
    fun `test chain`() {
        val stylesheet = Stylesheet() {
            register(JPanel::class.java) chain register(JButton::class.java) {
                font = font.deriveFont(20f)
            }
        }

        val panel = JPanel()
        val button = JButton("Click Me")
        panel.add(button)
        stylesheet.apply(panel)

        assertEquals(20, button.font.size, "Button font size should be 20")
    }

    @Test
    fun `test chain panel in button`() {
        val stylesheet = Stylesheet() {
            panel{} chain button {
                font = font.deriveFont(20f)
            }
        }

        val panel = JPanel()
        val button = JButton("Click Me")
        panel.add(button)
        stylesheet.apply(panel)

        assertEquals(20, button.font.size, "Button font size should be 20")
    }
}
