package ca.weblite.ktswing.jgoodies

import ca.weblite.ktswing.extensions.createComponent
import ca.weblite.ktswing.extensions.isAutoAddEnabled
import java.awt.Container
fun Container.form(cols: String, rows: String = "", init: Form.() -> Unit = {}): Form =
    createComponent(factory = { Form(cols, rows) }, init = init)