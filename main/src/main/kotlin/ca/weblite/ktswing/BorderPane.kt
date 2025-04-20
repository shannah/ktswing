package ca.weblite.swinky

import java.awt.BorderLayout
import java.awt.Component
import javax.swing.JPanel

/**
 * A custom JPanel that uses BorderLayout and provides properties for each region:
 *   north, south, east, west, center.
 *
 * Setting one of these properties calls add(component, BorderLayout.XXX) internally,
 * replacing any existing component in that region.
 */
class BorderPane : JPanel(BorderLayout()), AutoAddDisabled {

    var north: Component?
        get() = (this.layout as? BorderLayout)?.getLayoutComponent(this, BorderLayout.NORTH)
        set(value) {
            // Remove existing component in NORTH, if any
            val existing = (this.layout as? BorderLayout)?.getLayoutComponent(this, BorderLayout.NORTH)
            if (existing != null) {
                remove(existing)
            }
            // Add new component if not null
            if (value != null) {
                add(value, BorderLayout.NORTH)
            }
            // Refresh the layout
            revalidate()
            repaint()
        }

    var south: Component?
        get() = (this.layout as? BorderLayout)?.getLayoutComponent(this, BorderLayout.SOUTH)
        set(value) {
            val existing = (this.layout as? BorderLayout)?.getLayoutComponent(this, BorderLayout.SOUTH)
            if (existing != null) {
                remove(existing)
            }
            if (value != null) {
                add(value, BorderLayout.SOUTH)
            }
            revalidate()
            repaint()
        }

    var east: Component?
        get() = (this.layout as? BorderLayout)?.getLayoutComponent(this, BorderLayout.EAST)
        set(value) {
            val existing = (this.layout as? BorderLayout)?.getLayoutComponent(this, BorderLayout.EAST)
            if (existing != null) {
                remove(existing)
            }
            if (value != null) {
                add(value, BorderLayout.EAST)
            }
            revalidate()
            repaint()
        }

    var west: Component?
        get() = (this.layout as? BorderLayout)?.getLayoutComponent(this, BorderLayout.WEST)
        set(value) {
            val existing = (this.layout as? BorderLayout)?.getLayoutComponent(this, BorderLayout.WEST)
            if (existing != null) {
                remove(existing)
            }
            if (value != null) {
                add(value, BorderLayout.WEST)
            }
            revalidate()
            repaint()
        }

    var center: Component?
        get() = (this.layout as? BorderLayout)?.getLayoutComponent(this, BorderLayout.CENTER)
        set(value) {
            val existing = (this.layout as? BorderLayout)?.getLayoutComponent(this, BorderLayout.CENTER)
            if (existing != null) {
                remove(existing)
            }
            if (value != null) {
                add(value, BorderLayout.CENTER)
            }
            revalidate()
            repaint()
        }
}
