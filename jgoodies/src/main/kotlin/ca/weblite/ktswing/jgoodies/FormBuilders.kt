package ca.weblite.ktswing.jgoodies

import ca.weblite.ktswing.extensions.isAutoAddEnabled
import java.awt.Container

fun Container.form(cols: String, rows: String = "", init: Form.() -> Unit = {}): Form {
    val form = Form(cols, rows)
    form.init()
    if (isAutoAddEnabled()) {
        add(form)
    }
    return form
}