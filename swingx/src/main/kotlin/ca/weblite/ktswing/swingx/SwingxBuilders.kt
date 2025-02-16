package ca.weblite.ktswing.swingx

import ca.weblite.ktswing.extensions.getFactoryForComponent
import ca.weblite.ktswing.extensions.isAutoAddEnabled
import org.jdesktop.swingx.JXSearchField
import java.awt.Component
import java.awt.Container

fun Container.searchField(init: JXSearchField.() -> Unit = {}): JXSearchField {
    val factory = this.getFactoryForComponent(JXSearchField::class.java)
    val component = if (factory == null) JXSearchField() else factory()
    component.init()
    if (this.isAutoAddEnabled()) {
        this.add(component as Component)
    }
    return component
}