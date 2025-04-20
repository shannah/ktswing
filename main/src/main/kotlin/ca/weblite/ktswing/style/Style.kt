package ca.weblite.swinky.style

import java.awt.Component

interface Style<T: Component> {
    fun apply(target: T);
    fun getSelector(): Selector;
    fun getTargetClass(): Class<T>
}