package ca.weblite.swinky.style

import java.awt.Component

abstract class AbstractStyle<T: Component>(private val targetType: Class<T>, private val selector: Selector) : Style<T> {

    override fun getSelector(): Selector {
        return selector
    }

    override fun getTargetClass(): Class<T> {
        return targetType
    }
}