package ca.weblite.ktswing.swingx

import ca.weblite.ktswing.extensions.getFactoryForComponent
import ca.weblite.ktswing.extensions.isAutoAddEnabled
import org.jdesktop.swingx.JXImagePanel
import org.jdesktop.swingx.JXSearchField
import java.awt.Component
import java.awt.Container
import java.net.URL
import javax.swing.ImageIcon

fun Container.searchField(init: JXSearchField.() -> Unit = {}): JXSearchField {
    val factory = this.getFactoryForComponent(JXSearchField::class.java)
    val component = if (factory == null) JXSearchField() else factory()
    component.init()
    if (this.isAutoAddEnabled()) {
        this.add(component as Component)
    }
    return component
}

fun Container.imagePanel(init: JXImagePanel.() -> Unit = {}): JXImagePanel {
    val factory = this.getFactoryForComponent(JXImagePanel::class.java)
    val component = if (factory == null) JXImagePanel() else factory()
    component.init()
    if (this.isAutoAddEnabled()) {
        this.add(component as Component)
    }
    return component
}

fun Container.imagePanel(imageUrl: URL, init: JXImagePanel.() -> Unit = {}): JXImagePanel {
    val component = JXImagePanel(imageUrl)
    component.init()
    if (this.isAutoAddEnabled()) {
        this.add(component as Component)
    }
    return component
}