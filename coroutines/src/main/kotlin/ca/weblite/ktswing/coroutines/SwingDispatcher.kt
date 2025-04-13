package ca.weblite.ktswing.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import javax.swing.SwingUtilities
import kotlin.coroutines.CoroutineContext

object SwingDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if (SwingUtilities.isEventDispatchThread()) {
            block.run()
        } else {
            SwingUtilities.invokeLater(block)
        }
    }
}
