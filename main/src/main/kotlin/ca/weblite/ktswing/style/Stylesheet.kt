package ca.weblite.swinky.style

import java.awt.Component
import java.awt.Container
import java.util.WeakHashMap
import javax.swing.*

class Stylesheet {

    private val styles = mutableListOf<Style<out Component>>()

    private val appliedComponents: WeakHashMap<Component,Boolean> = WeakHashMap()

    constructor(init: Stylesheet.() -> Unit = {}) {
        apply(init)
    }

    fun isRegistered(style: Style<out Component>): Boolean = styles.contains(style)

    fun <T : Component> register(style: Style<T>): Style<T> {
        styles.add(style)
        return style
    }

    fun <T : Component> register(selectorString: String, targetClass: Class<T>): Style<T> {
        val style = ChainableStyle(targetClass, CssSelector(selectorString))
        return register(style)
    }

    fun <T : Component> register(targetClass: Class<T>): Style<T> {
        return register("*", targetClass)
    }

    infix fun <T : Component, E : Component> Style<T>.chain(style: Style<E>): Style<E> {
        unregister(style)
        if (this is ChainableStyle) {
            return this.add(style)
        } else {
            unregister(this)
            val chainableStyle = ChainableStyle(this)
            val addedChainable = chainableStyle.add(style)
            register(chainableStyle)
            return addedChainable
        }
    }

    fun <T : Component> register(selectorString: String, targetClass: Class<T>, apply: T.() -> Unit): Style<T> {
        val style = object : AbstractStyle<T>(targetClass, CssSelector(selectorString)) {
            override fun apply(target: T) {
                target.apply(apply)
            }
        }
        return register(style)
    }

    fun <T : Component> register(targetClass: Class<T>, apply: T.() -> Unit): Style<T> {
        return register("*", targetClass, apply)
    }

    fun unregister(style: Style<out Component>) {
        styles.remove(style)
    }

    fun apply(root: Component, isRoot: Boolean = true) {
        if (isRoot) {
            appliedComponents.put(root, true)
        }

        for (style in styles) {
            if (
                    style.getTargetClass().isAssignableFrom(root.javaClass) &&
                    style.getSelector().matches(root)
            ) {
                @Suppress("UNCHECKED_CAST")
                (style as Style<Component>).apply(root)
            }
        }

        if (root is Container) {
            for (i in 0 until root.componentCount) {
                apply(root.getComponent(i), false)
            }
        }
    }

    fun revalidate(component: Component) {
        if (appliedComponents.containsKey(component)) {
            apply(component)
        }
        if (component.parent != null) {
            revalidate(component.parent)
        }
    }

    // Shortcut functions that return the registered Style

    fun button(selector: String, apply: JButton.() -> Unit): Style<JButton> =
            register(selector, JButton::class.java, apply)

    fun button(apply: JButton.() -> Unit): Style<JButton> =
            register(JButton::class.java, apply)

    fun label(selector: String, apply: JLabel.() -> Unit): Style<JLabel> =
            register(selector, JLabel::class.java, apply)

    fun label(apply: JLabel.() -> Unit): Style<JLabel> =
            register(JLabel::class.java, apply)

    fun panel(selector: String, apply: JPanel.() -> Unit): Style<JPanel> =
            register(selector, JPanel::class.java, apply)

    fun panel(apply: JPanel.() -> Unit): Style<JPanel> =
            register(JPanel::class.java, apply)

    fun textField(selector: String, apply: JTextField.() -> Unit): Style<JTextField> =
            register(selector, JTextField::class.java, apply)

    fun textField(apply: JTextField.() -> Unit): Style<JTextField> =
            register(JTextField::class.java, apply)

    fun textArea(selector: String, apply: JTextArea.() -> Unit): Style<JTextArea> =
            register(selector, JTextArea::class.java, apply)

    fun textArea(apply: JTextArea.() -> Unit): Style<JTextArea> =
            register(JTextArea::class.java, apply)

    fun comboBox(selector: String, apply: JComboBox<*>.() -> Unit): Style<JComboBox<*>> =
            register(selector, JComboBox::class.java, apply)

    fun comboBox(apply: JComboBox<*>.() -> Unit): Style<JComboBox<*>> =
            register(JComboBox::class.java, apply)

    fun checkBox(selector: String, apply: JCheckBox.() -> Unit): Style<JCheckBox> =
            register(selector, JCheckBox::class.java, apply)

    fun checkBox(apply: JCheckBox.() -> Unit): Style<JCheckBox> =
            register(JCheckBox::class.java, apply)

    fun radioButton(selector: String, apply: JRadioButton.() -> Unit): Style<JRadioButton> =
            register(selector, JRadioButton::class.java, apply)

    fun radioButton(apply: JRadioButton.() -> Unit): Style<JRadioButton> =
            register(JRadioButton::class.java, apply)

    fun slider(selector: String, apply: JSlider.() -> Unit): Style<JSlider> =
            register(selector, JSlider::class.java, apply)

    fun slider(apply: JSlider.() -> Unit): Style<JSlider> =
            register(JSlider::class.java, apply)

    fun spinner(selector: String, apply: JSpinner.() -> Unit): Style<JSpinner> =
            register(selector, JSpinner::class.java, apply)

    fun spinner(apply: JSpinner.() -> Unit): Style<JSpinner> =
            register(JSpinner::class.java, apply)

    fun progressBar(selector: String, apply: JProgressBar.() -> Unit): Style<JProgressBar> =
            register(selector, JProgressBar::class.java, apply)

    fun progressBar(apply: JProgressBar.() -> Unit): Style<JProgressBar> =
            register(JProgressBar::class.java, apply)

    fun scrollPane(selector: String, apply: JScrollPane.() -> Unit): Style<JScrollPane> =
            register(selector, JScrollPane::class.java, apply)

    fun scrollPane(apply: JScrollPane.() -> Unit): Style<JScrollPane> =
            register(JScrollPane::class.java, apply)

    fun table(selector: String, apply: JTable.() -> Unit): Style<JTable> =
            register(selector, JTable::class.java, apply)

    fun table(apply: JTable.() -> Unit): Style<JTable> =
            register(JTable::class.java, apply)

    fun tree(selector: String, apply: JTree.() -> Unit): Style<JTree> =
            register(selector, JTree::class.java, apply)

    fun tree(apply: JTree.() -> Unit): Style<JTree> =
            register(JTree::class.java, apply)

    fun tabbedPane(selector: String, apply: JTabbedPane.() -> Unit): Style<JTabbedPane> =
            register(selector, JTabbedPane::class.java, apply)

    fun tabbedPane(apply: JTabbedPane.() -> Unit): Style<JTabbedPane> =
            register(JTabbedPane::class.java, apply)

    fun menuBar(selector: String, apply: JMenuBar.() -> Unit): Style<JMenuBar> =
            register(selector, JMenuBar::class.java, apply)

    fun menuBar(apply: JMenuBar.() -> Unit): Style<JMenuBar> =
            register(JMenuBar::class.java, apply)

    fun menuItem(selector: String, apply: JMenuItem.() -> Unit): Style<JMenuItem> =
            register(selector, JMenuItem::class.java, apply)

    fun menuItem(apply: JMenuItem.() -> Unit): Style<JMenuItem> =
            register(JMenuItem::class.java, apply)

    fun menu(selector: String, apply: JMenu.() -> Unit): Style<JMenu> =
            register(selector, JMenu::class.java, apply)

    fun menu(apply: JMenu.() -> Unit): Style<JMenu> =
            register(JMenu::class.java, apply)

    fun popupMenu(selector: String, apply: JPopupMenu.() -> Unit): Style<JPopupMenu> =
            register(selector, JPopupMenu::class.java, apply)

    fun popupMenu(apply: JPopupMenu.() -> Unit): Style<JPopupMenu> =
            register(JPopupMenu::class.java, apply)

    fun toolBar(selector: String, apply: JToolBar.() -> Unit): Style<JToolBar> =
            register(selector, JToolBar::class.java, apply)

    fun toolBar(apply: JToolBar.() -> Unit): Style<JToolBar> =
            register(JToolBar::class.java, apply)

    fun toolTip(selector: String, apply: JToolTip.() -> Unit): Style<JToolTip> =
            register(selector, JToolTip::class.java, apply)

    fun toolTip(apply: JToolTip.() -> Unit): Style<JToolTip> =
            register(JToolTip::class.java, apply)

    fun dialog(selector: String, apply: JDialog.() -> Unit): Style<JDialog> =
            register(selector, JDialog::class.java, apply)

    fun dialog(apply: JDialog.() -> Unit): Style<JDialog> =
            register(JDialog::class.java, apply)

    fun frame(selector: String, apply: JFrame.() -> Unit): Style<JFrame> =
            register(selector, JFrame::class.java, apply)

    fun frame(apply: JFrame.() -> Unit): Style<JFrame> =
            register(JFrame::class.java, apply)

    fun internalFrame(selector: String, apply: JInternalFrame.() -> Unit): Style<JInternalFrame> =
            register(selector, JInternalFrame::class.java, apply)

    fun internalFrame(apply: JInternalFrame.() -> Unit): Style<JInternalFrame> =
            register(JInternalFrame::class.java, apply)

    fun desktopPane(selector: String, apply: JDesktopPane.() -> Unit): Style<JDesktopPane> =
            register(selector, JDesktopPane::class.java, apply)

    fun desktopPane(apply: JDesktopPane.() -> Unit): Style<JDesktopPane> =
            register(JDesktopPane::class.java, apply)

    fun layeredPane(selector: String, apply: JLayeredPane.() -> Unit): Style<JLayeredPane> =
            register(selector, JLayeredPane::class.java, apply)

    fun layeredPane(apply: JLayeredPane.() -> Unit): Style<JLayeredPane> =
            register(JLayeredPane::class.java, apply)

    fun splitPane(selector: String, apply: JSplitPane.() -> Unit): Style<JSplitPane> =
            register(selector, JSplitPane::class.java, apply)

    fun splitPane(apply: JSplitPane.() -> Unit): Style<JSplitPane> =
            register(JSplitPane::class.java, apply)

    fun scrollBar(selector: String, apply: JScrollBar.() -> Unit): Style<JScrollBar> =
            register(selector, JScrollBar::class.java, apply)

    fun scrollBar(apply: JScrollBar.() -> Unit): Style<JScrollBar> =
            register(JScrollBar::class.java, apply)

    fun viewport(selector: String, apply: JViewport.() -> Unit): Style<JViewport> =
            register(selector, JViewport::class.java, apply)

    fun viewport(apply: JViewport.() -> Unit): Style<JViewport> =
            register(JViewport::class.java, apply)

    fun rootPane(selector: String, apply: JRootPane.() -> Unit): Style<JRootPane> =
            register(selector, JRootPane::class.java, apply)

    fun rootPane(apply: JRootPane.() -> Unit): Style<JRootPane> =
            register(JRootPane::class.java, apply)

    fun contentPane(selector: String, apply: JPanel.() -> Unit): Style<JPanel> =
            register(selector, JPanel::class.java, apply)

    fun contentPane(apply: JPanel.() -> Unit): Style<JPanel> =
            register(JPanel::class.java, apply)

    fun glassPane(selector: String, apply: JPanel.() -> Unit): Style<JPanel> =
            register(selector, JPanel::class.java, apply)

    fun glassPane(apply: JPanel.() -> Unit): Style<JPanel> =
            register(JPanel::class.java, apply)

    fun textPane(selector: String, apply: JTextPane.() -> Unit): Style<JTextPane> =
            register(selector, JTextPane::class.java, apply)

    fun textPane(apply: JTextPane.() -> Unit): Style<JTextPane> =
            register(JTextPane::class.java, apply)

    fun editorPane(selector: String, apply: JEditorPane.() -> Unit): Style<JEditorPane> =
            register(selector, JEditorPane::class.java, apply)

    fun editorPane(apply: JEditorPane.() -> Unit): Style<JEditorPane> =
            register(JEditorPane::class.java, apply)

    fun textComponent(selector: String, apply: javax.swing.text.JTextComponent.() -> Unit): Style<javax.swing.text.JTextComponent> =
            register(selector, javax.swing.text.JTextComponent::class.java, apply)

    fun textComponent(apply: javax.swing.text.JTextComponent.() -> Unit): Style<javax.swing.text.JTextComponent> =
            register(javax.swing.text.JTextComponent::class.java, apply)

    fun borderPane(selector: String, apply: ca.weblite.swinky.BorderPane.() -> Unit): Style<ca.weblite.swinky.BorderPane> =
            register(selector, ca.weblite.swinky.BorderPane::class.java, apply)

    fun borderPane(apply: ca.weblite.swinky.BorderPane.() -> Unit): Style<ca.weblite.swinky.BorderPane> =
            register(ca.weblite.swinky.BorderPane::class.java, apply)

    fun list(selector: String, apply: JList<*>.() -> Unit): Style<JList<*>> =
            register(selector, JList::class.java, apply)

    fun list(apply: JList<*>.() -> Unit): Style<JList<*>> =
            register(JList::class.java, apply)
}
