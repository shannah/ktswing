package ca.weblite.ktswing.codegen

import java.awt.Component
import java.awt.Container
import java.io.File
import java.lang.reflect.Modifier
import java.lang.reflect.TypeVariable

/**
 * Instead of scanning with Reflections, we keep a curated list of Swing components.
 * For each listed class, we attempt to load it, ensure it's public & non-abstract,
 * then generate a DSL builder function (if it extends java.awt.Component).
 *
 * Now we detect classes that have generic type parameters (e.g. JComboBox<E>, JLayer<V>)
 * and expose them in the generated builder function signature.
 *
 * Additionally, we integrate the isAutoAddEnabled() extension method to control
 * whether components are automatically added to the container.
 */
object CodeGenerator {

    // A curated list of known Swing classes with no-arg constructors (adjust as needed).
    // Classes like JLayer<V> or JComboBox<E> have generics, which we'll handle automatically now.
    private val KNOWN_SWING_CLASSES = listOf(
        "javax.swing.JButton",
        "javax.swing.JCheckBox",
        "javax.swing.JRadioButton",
        "javax.swing.JComboBox",
        "javax.swing.JDesktopPane",
        "javax.swing.JDialog",
        "javax.swing.JEditorPane",
        "javax.swing.JFrame",
        "javax.swing.JInternalFrame",
        "javax.swing.JLabel",
        "javax.swing.JLayer",
        "javax.swing.JList",
        "javax.swing.JMenuBar",
        "javax.swing.JOptionPane",
        "javax.swing.JPanel",
        "javax.swing.JPasswordField",
        "javax.swing.JPopupMenu",
        "javax.swing.JProgressBar",
        "javax.swing.JRootPane",
        "javax.swing.JScrollBar",
        "javax.swing.JScrollPane",
        "javax.swing.JSeparator",
        "javax.swing.JSlider",
        "javax.swing.JSpinner",
        "javax.swing.JSplitPane",
        "javax.swing.JTabbedPane",
        "javax.swing.JTable",
        "javax.swing.JTextArea",
        "javax.swing.JTextField",
        "javax.swing.JTextPane",
        "javax.swing.JToggleButton",
        "javax.swing.JToolBar",
        "javax.swing.JToolTip",
        "javax.swing.JTree",
        "javax.swing.JViewport",
        "javax.swing.JWindow"
        // Potentially: "javax.swing.JFileChooser", "javax.swing.JMenuItem", etc., if desired
    )

    @JvmStatic
    fun main(args: Array<String>) {
        // 1) Output directory for generated .kt files
        val outputDir = args.firstOrNull()?.let(::File)
            ?: File("target/generated-sources/kotlin")
        outputDir.mkdirs()


        // 2) Load each class by name, filter out abstract or non-public classes
        val loadedComponents = KNOWN_SWING_CLASSES.mapNotNull { className ->
            try {
                val cls = Class.forName(className)
                // We only want classes that extend java.awt.Component
                if (Component::class.java.isAssignableFrom(cls)
                    && Modifier.isPublic(cls.modifiers)
                    && !Modifier.isAbstract(cls.modifiers)
                ) {
                    @Suppress("UNCHECKED_CAST")
                    cls as Class<out Component>
                } else {
                    null
                }
            } catch (ex: ClassNotFoundException) {
                println("Class not found: $className (skipping)")
                null
            } catch (ex: NoClassDefFoundError) {
                println("NoClassDefFoundError for $className (skipping)")
                null
            }
        }.sortedBy { it.name }

        println("Found ${loadedComponents.size} curated Swing classes to generate builders for.")

        // 3) Group them by subpackage, e.g. "javax.swing" vs. "javax.swing.text"
        val pkgMap = mutableMapOf<String, MutableList<Class<out Component>>>()
        for (cls in loadedComponents) {
            // e.g. "javax.swing" -> subPkg = "" ;  "javax.swing.text" -> subPkg = "text"
            val subPackage = cls.packageName
                .removePrefix("javax.swing")
                .removePrefix(".")
            pkgMap.getOrPut(subPackage) { mutableListOf() }.add(cls)
        }

        // 4) For each subpackage group, write a single "SwingBuilders.kt"
        for ((subPkg, classes) in pkgMap) {
            val newPackage = buildString {
                append("ca.weblite.ktswing")
                if (subPkg.isNotEmpty()) {
                    append(".")
                    append(subPkg)
                }
            }
            val swingPkg = "javax.swing" + if (subPkg.isNotEmpty()) ".$subPkg" else ""
            val pkgPath = newPackage.replace('.', '/')
            val packageDir = File(outputDir, pkgPath).apply { mkdirs() }

            // Generate builder methods
            val builderMethods = classes.joinToString("\n\n") { cls ->
                generateBuilderFunction(cls)
            }

            // Put them all in one file
            val fileContent = """
                package $newPackage

                import ca.weblite.ktswing.extensions.isAutoAddEnabled
                import ca.weblite.ktswing.extensions.getFactoryForComponent
                import javax.swing.*
                import java.awt.Container
                import java.awt.Component

                /**
                 * Auto-generated builder DSL extension functions for the curated list of
                 * Swing classes in "javax.swing${if (subPkg.isNotEmpty()) ".$subPkg" else ""}".
                 *
                 * Modify the curated list in [CodeGenerator.KNOWN_SWING_CLASSES].
                 */
                $builderMethods

            """.trimIndent()

            val outputFile = File(packageDir, "SwingBuilders.kt")
            outputFile.writeText(fileContent)
            println("Generated ${classes.size} builder functions in: ${outputFile.absolutePath}")
        }
    }

    /**
     * Generates the extension function for a single Swing class.
     * E.g. "javax.swing.JButton" -> "fun Container.button(init: JButton.() -> Unit = {}): JButton { ... }"
     *
     * If the class has generic type parameters (like JLayer<V>, JComboBox<E>),
     * we expose them in the function signature.
     */
    private fun generateBuilderFunction(cls: Class<out Component>): String {
        val simpleName = cls.simpleName
        val functionName = deriveFunctionName(simpleName)

        // Step 1) Check if this class has type parameters
        val typeParams = cls.typeParameters // e.g. [E], [V], or empty if no generics
        return if (typeParams.isEmpty()) {
            // No generics => standard approach
            """
            /**
             * Creates a [$simpleName] and conditionally adds it to the calling [Container].
             *
             * @param init Lambda to configure the [$simpleName].
             * @return The configured [$simpleName].
             */
            fun Container.$functionName(init: $simpleName.() -> Unit = {}): $simpleName {
                val factory = this.getFactoryForComponent(${simpleName}::class.java)
                val component = if (factory == null) $simpleName() else factory()
                
                component.init()
                if (this.isAutoAddEnabled()) {
                    this.add(component as Component)
                } else {
                    if (component is JComponent) {
                        component.putClientProperty("ktswing.Container", this);
                    }
                }
                return component
            }
            """.trimIndent()
        } else {
            // Build a type parameter declaration, e.g. "<E>", or "<V : Component>"
            val paramDecls = typeParams.joinToString(", ") { buildTypeParamDecl(it) }
            // The type parameter names, e.g. "E" or "V"
            val paramNames = typeParams.joinToString(", ") { it.name }

            // Example result:
            // fun <E> Container.comboBox(init: JComboBox<E>.() -> Unit = {}): JComboBox<E> {
            //   val c = javax.swing.JComboBox<E>()
            //   c.init()
            //   if (this.isAutoAddEnabled()) {
            //       this.add(c as Component)
            //   }
            //   return c
            // }

            """
            /**
             * Creates a [$simpleName] with type parameters and conditionally adds it to the calling [Container].
             *
             * @param init Lambda to configure the [$simpleName].
             * @return The configured [$simpleName].
             */
            fun <$paramDecls> Container.$functionName(init: $simpleName<$paramNames>.() -> Unit = {}): $simpleName<$paramNames> {
                val component = $simpleName<$paramNames>()
                component.init()
                if (this.isAutoAddEnabled()) {
                    this.add(component as Component)
                }
                return component
            }
            """.trimIndent()
        }
    }

    /**
     * Build a single type parameter declaration from a TypeVariable, e.g. "<E : Component>" or just "<E>".
     * We do a simple check for:
     *  - If there's exactly one bound and it's not Object, we show it like "<E : SomeBound>".
     *  - Otherwise, we omit the bound.
     */
    private fun buildTypeParamDecl(typeVar: TypeVariable<*>): String {
        val name = typeVar.name // e.g. "E"
        val bounds = typeVar.bounds // e.g. [java.lang.Object], or [class java.awt.Component], etc.
        if (bounds.size == 1) {
            val bound = bounds[0]
            // If it's just "java.lang.Object", skip the bound.
            // If it's "java.awt.Component", produce "E : Component".
            // If it's something else, we might produce e.g. "E : Foo" (simple approach).
            val boundClassName = bound.typeName // e.g. "java.awt.Component"
            if (boundClassName == "java.lang.Object") {
                return name // e.g. "E"
            }
            // Attempt to parse the short name from a typical fully qualified class
            val shortName = boundClassName.substringAfterLast('.')
            // If it has weird symbols, or multiple bounds, we skip. For a simple approach:
            return "$name : $shortName"
        }
        // If multiple or zero bounds (?), fallback to just the name
        return name
    }

    /**
     * Drop a leading 'J' if the second letter is uppercase (e.g. JButton -> button),
     * otherwise lowercase the first letter.
     */
    private fun deriveFunctionName(simpleName: String): String {
        return if (simpleName.length >= 2 && simpleName[0] == 'J' && simpleName[1].isUpperCase()) {
            simpleName.substring(1).replaceFirstChar { it.lowercase() }
        } else {
            simpleName.replaceFirstChar { it.lowercase() }
        }
    }
}
