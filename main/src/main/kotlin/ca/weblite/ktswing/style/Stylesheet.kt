package ca.weblite.ktswing.style

import java.awt.Component
import java.awt.Container

class Stylesheet(init: Stylesheet.() -> Unit = {}) {

    private val styles = mutableListOf<Style<out Component>>()
    private val registerStack: MutableList<Style<out Component>> = mutableListOf()

    fun <T : Component> register(style: Style<T>) {
        styles.add(style)
    }

    fun <T : Component> register(selectorString: String, targetClass: Class<T>, apply: T.() -> Unit) {
        register(object : AbstractStyle<T>(targetClass, CssSelector(selectorString)) {
            override fun apply(target: T) {
                // Call the lambda with the target as its receiver.
                target.apply(apply)
            }
        })
    }

    fun <T : Component> register(targetClass: Class<T>, apply: T.() -> Unit) {
        register("*", targetClass, apply)
    }

    fun apply(root: Component) {
        if (root is Container) {
            for (i in 0 until root.componentCount) {
                apply(root.getComponent(i))
            }
        }
        for (style in styles) {
            if (style.getTargetClass().isAssignableFrom(root.javaClass)
                    && style.getSelector().matches(root)
            ) {
                @Suppress("UNCHECKED_CAST")
                (style as Style<Component>).apply(root)
            }
        }
    }

    fun button(selector: String, apply: javax.swing.JButton.() -> Unit) {
        register(selector, javax.swing.JButton::class.java, apply)
    }

    fun button(apply: javax.swing.JButton.() -> Unit) {
        register(javax.swing.JButton::class.java, apply)
    }

    fun label(selector: String, apply: javax.swing.JLabel.() -> Unit) {
        register(selector, javax.swing.JLabel::class.java, apply)
    }

    fun label(apply: javax.swing.JLabel.() -> Unit) {
        register(javax.swing.JLabel::class.java, apply)
    }

    fun panel(selector: String, apply: javax.swing.JPanel.() -> Unit) {
        register(selector, javax.swing.JPanel::class.java, apply)
    }

    fun panel(apply: javax.swing.JPanel.() -> Unit) {
        register(javax.swing.JPanel::class.java, apply)
    }

    fun textField(selector: String, apply: javax.swing.JTextField.() -> Unit) {
        register(selector, javax.swing.JTextField::class.java, apply)
    }

    fun textField(apply: javax.swing.JTextField.() -> Unit) {
        register(javax.swing.JTextField::class.java, apply)
    }

    fun textArea(selector: String, apply: javax.swing.JTextArea.() -> Unit) {
        register(selector, javax.swing.JTextArea::class.java, apply)
    }

    fun textArea(apply: javax.swing.JTextArea.() -> Unit) {
        register(javax.swing.JTextArea::class.java, apply)
    }

    fun comboBox(selector: String, apply: javax.swing.JComboBox<*>.() -> Unit) {
        register(selector, javax.swing.JComboBox::class.java, apply)
    }

    fun comboBox(apply: javax.swing.JComboBox<*>.() -> Unit) {
        register(javax.swing.JComboBox::class.java, apply)
    }

    fun checkBox(selector: String, apply: javax.swing.JCheckBox.() -> Unit) {
        register(selector, javax.swing.JCheckBox::class.java, apply)
    }

    fun checkBox(apply: javax.swing.JCheckBox.() -> Unit) {
        register(javax.swing.JCheckBox::class.java, apply)
    }

    fun radioButton(selector: String, apply: javax.swing.JRadioButton.() -> Unit) {
        register(selector, javax.swing.JRadioButton::class.java, apply)
    }

    fun radioButton(apply: javax.swing.JRadioButton.() -> Unit) {
        register(javax.swing.JRadioButton::class.java, apply)
    }

    fun slider(selector: String, apply: javax.swing.JSlider.() -> Unit) {
        register(selector, javax.swing.JSlider::class.java, apply)
    }

    fun slider(apply: javax.swing.JSlider.() -> Unit) {
        register(javax.swing.JSlider::class.java, apply)
    }

    fun spinner(selector: String, apply: javax.swing.JSpinner.() -> Unit) {
        register(selector, javax.swing.JSpinner::class.java, apply)
    }

    fun spinner(apply: javax.swing.JSpinner.() -> Unit) {
        register(javax.swing.JSpinner::class.java, apply)
    }

    fun progressBar(selector: String, apply: javax.swing.JProgressBar.() -> Unit) {
        register(selector, javax.swing.JProgressBar::class.java, apply)
    }

    fun progressBar(apply: javax.swing.JProgressBar.() -> Unit) {
        register(javax.swing.JProgressBar::class.java, apply)
    }

    fun scrollPane(selector: String, apply: javax.swing.JScrollPane.() -> Unit) {
        register(selector, javax.swing.JScrollPane::class.java, apply)
    }

    fun scrollPane(apply: javax.swing.JScrollPane.() -> Unit) {
        register(javax.swing.JScrollPane::class.java, apply)
    }

    fun table(selector: String, apply: javax.swing.JTable.() -> Unit) {
        register(selector, javax.swing.JTable::class.java, apply)
    }

    fun table(apply: javax.swing.JTable.() -> Unit) {
        register(javax.swing.JTable::class.java, apply)
    }

    fun tree(selector: String, apply: javax.swing.JTree.() -> Unit) {
        register(selector, javax.swing.JTree::class.java, apply)
    }

    fun tree(apply: javax.swing.JTree.() -> Unit) {
        register(javax.swing.JTree::class.java, apply)
    }

    fun tabbedPane(selector: String, apply: javax.swing.JTabbedPane.() -> Unit) {
        register(selector, javax.swing.JTabbedPane::class.java, apply)
    }

    fun tabbedPane(apply: javax.swing.JTabbedPane.() -> Unit) {
        register(javax.swing.JTabbedPane::class.java, apply)
    }

    fun menuBar(selector: String, apply: javax.swing.JMenuBar.() -> Unit) {
        register(selector, javax.swing.JMenuBar::class.java, apply)
    }

    fun menuBar(apply: javax.swing.JMenuBar.() -> Unit) {
        register(javax.swing.JMenuBar::class.java, apply)
    }

    fun menuItem(selector: String, apply: javax.swing.JMenuItem.() -> Unit) {
        register(selector, javax.swing.JMenuItem::class.java, apply)
    }

    fun menuItem(apply: javax.swing.JMenuItem.() -> Unit) {
        register(javax.swing.JMenuItem::class.java, apply)
    }

    fun menu(selector: String, apply: javax.swing.JMenu.() -> Unit) {
        register(selector, javax.swing.JMenu::class.java, apply)
    }

    fun menu(apply: javax.swing.JMenu.() -> Unit) {
        register(javax.swing.JMenu::class.java, apply)
    }

    fun popupMenu(selector: String, apply: javax.swing.JPopupMenu.() -> Unit) {
        register(selector, javax.swing.JPopupMenu::class.java, apply)
    }

    fun popupMenu(apply: javax.swing.JPopupMenu.() -> Unit) {
        register(javax.swing.JPopupMenu::class.java, apply)
    }

    fun toolBar(selector: String, apply: javax.swing.JToolBar.() -> Unit) {
        register(selector, javax.swing.JToolBar::class.java, apply)
    }

    fun toolBar(apply: javax.swing.JToolBar.() -> Unit) {
        register(javax.swing.JToolBar::class.java, apply)
    }

    fun toolTip(selector: String, apply: javax.swing.JToolTip.() -> Unit) {
        register(selector, javax.swing.JToolTip::class.java, apply)
    }

    fun toolTip(apply: javax.swing.JToolTip.() -> Unit) {
        register(javax.swing.JToolTip::class.java, apply)
    }

    fun dialog(selector: String, apply: javax.swing.JDialog.() -> Unit) {
        register(selector, javax.swing.JDialog::class.java, apply)
    }

    fun dialog(apply: javax.swing.JDialog.() -> Unit) {
        register(javax.swing.JDialog::class.java, apply)
    }

    fun frame(selector: String, apply: javax.swing.JFrame.() -> Unit) {
        register(selector, javax.swing.JFrame::class.java, apply)
    }

    fun frame(apply: javax.swing.JFrame.() -> Unit) {
        register(javax.swing.JFrame::class.java, apply)
    }

    fun internalFrame(selector: String, apply: javax.swing.JInternalFrame.() -> Unit) {
        register(selector, javax.swing.JInternalFrame::class.java, apply)
    }

    fun internalFrame(apply: javax.swing.JInternalFrame.() -> Unit) {
        register(javax.swing.JInternalFrame::class.java, apply)
    }

    fun desktopPane(selector: String, apply: javax.swing.JDesktopPane.() -> Unit) {
        register(selector, javax.swing.JDesktopPane::class.java, apply)
    }

    fun desktopPane(apply: javax.swing.JDesktopPane.() -> Unit) {
        register(javax.swing.JDesktopPane::class.java, apply)
    }

    fun layeredPane(selector: String, apply: javax.swing.JLayeredPane.() -> Unit) {
        register(selector, javax.swing.JLayeredPane::class.java, apply)
    }

    fun layeredPane(apply: javax.swing.JLayeredPane.() -> Unit) {
        register(javax.swing.JLayeredPane::class.java, apply)
    }

    fun splitPane(selector: String, apply: javax.swing.JSplitPane.() -> Unit) {
        register(selector, javax.swing.JSplitPane::class.java, apply)
    }

    fun splitPane(apply: javax.swing.JSplitPane.() -> Unit) {
        register(javax.swing.JSplitPane::class.java, apply)
    }

    fun scrollBar(selector: String, apply: javax.swing.JScrollBar.() -> Unit) {
        register(selector, javax.swing.JScrollBar::class.java, apply)
    }

    fun scrollBar(apply: javax.swing.JScrollBar.() -> Unit) {
        register(javax.swing.JScrollBar::class.java, apply)
    }

    fun viewport(selector: String, apply: javax.swing.JViewport.() -> Unit) {
        register(selector, javax.swing.JViewport::class.java, apply)
    }

    fun viewport(apply: javax.swing.JViewport.() -> Unit) {
        register(javax.swing.JViewport::class.java, apply)
    }

    fun rootPane(selector: String, apply: javax.swing.JRootPane.() -> Unit) {
        register(selector, javax.swing.JRootPane::class.java, apply)
    }

    fun rootPane(apply: javax.swing.JRootPane.() -> Unit) {
        register(javax.swing.JRootPane::class.java, apply)
    }

    fun contentPane(selector: String, apply: javax.swing.JPanel.() -> Unit) {
        register(selector, javax.swing.JPanel::class.java, apply)
    }

    fun contentPane(apply: javax.swing.JPanel.() -> Unit) {
        register(javax.swing.JPanel::class.java, apply)
    }

    fun glassPane(selector: String, apply: javax.swing.JPanel.() -> Unit) {
        register(selector, javax.swing.JPanel::class.java, apply)
    }

    fun glassPane(apply: javax.swing.JPanel.() -> Unit) {
        register(javax.swing.JPanel::class.java, apply)
    }

    fun textPane(selector: String, apply: javax.swing.JTextPane.() -> Unit) {
        register(selector, javax.swing.JTextPane::class.java, apply)
    }

    fun textPane(apply: javax.swing.JTextPane.() -> Unit) {
        register(javax.swing.JTextPane::class.java, apply)
    }

    fun editorPane(selector: String, apply: javax.swing.JEditorPane.() -> Unit) {
        register(selector, javax.swing.JEditorPane::class.java, apply)
    }

    fun editorPane(apply: javax.swing.JEditorPane.() -> Unit) {
        register(javax.swing.JEditorPane::class.java, apply)
    }

    fun textComponent(selector: String, apply: javax.swing.text.JTextComponent.() -> Unit) {
        register(selector, javax.swing.text.JTextComponent::class.java, apply)
    }

    fun textComponent(apply: javax.swing.text.JTextComponent.() -> Unit) {
        register(javax.swing.text.JTextComponent::class.java, apply)
    }

    fun borderPane(selector: String, apply: ca.weblite.ktswing.BorderPane.() -> Unit) {
        register(selector, ca.weblite.ktswing.BorderPane::class.java, apply)
    }

    fun borderPane(apply: ca.weblite.ktswing.BorderPane.() -> Unit) {
        register(ca.weblite.ktswing.BorderPane::class.java, apply)
    }

    fun list(selector: String, apply: javax.swing.JList<*>.() -> Unit) {
        register(selector, javax.swing.JList::class.java, apply)
    }

    fun list(apply: javax.swing.JList<*>.() -> Unit) {
        register(javax.swing.JList::class.java, apply)
    }
}
