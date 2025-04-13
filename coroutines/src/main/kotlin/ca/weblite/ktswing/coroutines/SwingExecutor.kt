package ca.weblite.ktswing.coroutines

import java.util.concurrent.Executor
import javax.swing.SwingUtilities

val swingExecutor = Executor { block ->
    if (SwingUtilities.isEventDispatchThread()) {
        block.run()
    } else {
        SwingUtilities.invokeLater(block)
    }
}
