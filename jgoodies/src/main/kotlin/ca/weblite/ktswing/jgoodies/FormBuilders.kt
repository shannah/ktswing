package ca.weblite.swinky.jgoodies

import ca.weblite.swinky.extensions.createComponent
import ca.weblite.swinky.extensions.isAutoAddEnabled
import java.awt.Container
fun Container.form(cols: String, rows: String = "", init: Form.() -> Unit = {}): Form =
    createComponent(factory = { Form(cols, rows) }, init = init)