package ca.weblite.ktswing.swingx

import ca.weblite.ktswing.extensions.createComponent
import ca.weblite.ktswing.extensions.getFactoryForComponent
import ca.weblite.ktswing.extensions.isAutoAddEnabled
import org.jdesktop.swingx.JXImagePanel
import org.jdesktop.swingx.JXSearchField
import java.awt.Component
import java.awt.Container
import java.net.URL
import javax.swing.ImageIcon

fun Container.searchField(init: JXSearchField.() -> Unit = {}): JXSearchField  =
    createComponent(factory = { JXSearchField() }, init = init)

fun Container.imagePanel(init: JXImagePanel.() -> Unit = {}): JXImagePanel
    = createComponent(factory = null, init = init)

fun Container.imagePanel(imageUrl: URL, init: JXImagePanel.() -> Unit = {}): JXImagePanel =
    createComponent(factory = { JXImagePanel(imageUrl) }, init = init)