package ca.weblite.ktswing.swingx

import ca.weblite.ktswing.extensions.createComponent
import org.jdesktop.swingx.JXImagePanel
import org.jdesktop.swingx.JXSearchField
import java.awt.Container
import java.net.URL

fun Container.searchField(init: JXSearchField.() -> Unit = {}): JXSearchField  =
    createComponent(factory = { JXSearchField() }, init = init)

fun Container.imagePanel(init: JXImagePanel.() -> Unit = {}): JXImagePanel
    = createComponent(factory = null, init = init)

fun Container.imagePanel(imageUrl: URL, init: JXImagePanel.() -> Unit = {}): JXImagePanel =
    createComponent(factory = { JXImagePanel(imageUrl) }, init = init)