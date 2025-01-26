```markdown
# Kotlin Swing DSL (`ktswing`)

![Maven Central](https://img.shields.io/maven-central/v/ca.weblite/ktswing-main.svg?label=Maven%20Central)

A **Kotlin-based Domain Specific Language (DSL)** for building Swing user interfaces declaratively. Simplify your Swing UI development with intuitive builder functions, automatic component management, and support for complex layouts like BorderLayout and JSplitPane.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
  - [Maven](#maven)
  - [Gradle](#gradle)
- [Usage](#usage)
  - [Basic Example](#basic-example)
  - [Advanced Layouts](#advanced-layouts)
    - [BorderPane](#borderpane)
    - [SplitPane](#splitpane)
- [Generated Builders](#generated-builders)
- [Extending the DSL](#extending-the-dsl)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Declarative UI Building:** Use Kotlin's expressive syntax to construct UIs without boilerplate.
- **Auto-Generated Builders:** Easily add common Swing components with builder functions like `button { ... }`, `label { ... }`, `textField { ... }`, and more.
- **Context-Aware Component Management:** Prevent unintended component additions in specialized containers like `JSplitPane` using the `isAutoAddEnabled()` mechanism.
- **Support for Complex Layouts:** Seamlessly build complex layouts such as `BorderLayout` and `JSplitPane` with intuitive DSL constructs.
- **Generic Component Support:** Handle generic Swing components (e.g., `JComboBox<E>`, `JList<E>`) with type-safe builder functions.

## Installation

You can include `ktswing` in your project by adding it as a dependency from Maven Central.

### Maven

```xml
<dependencies>
    <dependency>
        <groupId>ca.weblite</groupId>
        <artifactId>ktswing-main</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

Also, ensure you have the Maven Central repository configured:

```xml
<repositories>
    <repository>
        <id>central</id>
        <name>Maven Central Repository</name>
        <url>https://repo.maven.apache.org/maven2</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
    </repository>
</repositories>
```

### Gradle

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'ca.weblite:ktswing-main:1.0.0'
}
```

## Usage

Using `ktswing-main`, you can build Swing UIs declaratively with concise and readable Kotlin code. Below are examples demonstrating how to create simple and complex UIs using the DSL.

### Basic Example

Create a simple `JFrame` with labels, buttons, and text fields.

```kotlin
package ca.weblite.ktswing.example

import ca.weblite.ktswing.generated.button
import ca.weblite.ktswing.generated.label
import ca.weblite.ktswing.generated.panel
import ca.weblite.ktswing.generated.textField
import javax.swing.JFrame
import javax.swing.SwingUtilities
import kotlin.system.exitProcess

fun main() {
    SwingUtilities.invokeLater {
        JFrame("Kotlin Swing DSL Example").apply {
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            setSize(400, 300)

            // Create a main panel using the DSL
            panel {
                label {
                    text = "Hello, Kotlin Swing DSL!"
                }

                button {
                    text = "OK"
                    addActionListener {
                        println("OK button clicked!")
                    }
                }

                label {
                    text = "Enter your name:"
                }

                textField {
                    columns = 20
                    text = "John Doe"
                }

                button {
                    text = "Quit"
                    addActionListener {
                        exitProcess(0)
                    }
                }
            }

            isVisible = true
        }
    }
}
```

**Explanation:**

- **`panel { ... }`**: Creates a `JPanel` and adds it to the `JFrame`.
- **`label { ... }`**: Adds a `JLabel` with specified text.
- **`button { ... }`**: Adds a `JButton` with specified text and action listeners.
- **`textField { ... }`**: Adds a `JTextField` with specified columns and default text.

### Advanced Layouts

#### BorderPane

Create a `JPanel` with `BorderLayout` using the `borderPane` builder, allowing you to assign components to `north`, `south`, `east`, `west`, and `center` regions.

```kotlin
package ca.weblite.ktswing.example

import ca.weblite.ktswing.custom.borderPane
import ca.weblite.ktswing.generated.button
import ca.weblite.ktswing.generated.label
import ca.weblite.ktswing.generated.panel
import javax.swing.JFrame
import javax.swing.SwingUtilities
import kotlin.system.exitProcess

fun main() {
    SwingUtilities.invokeLater {
        JFrame("BorderPane DSL Example").apply {
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            setSize(600, 400)

            // Create a BorderPane layout
            borderPane {
                north = label {
                    text = "North Region"
                }

                south = button {
                    text = "Exit"
                    addActionListener {
                        exitProcess(0)
                    }
                }

                east = label {
                    text = "East Label"
                }

                west = label {
                    text = "West Label"
                }

                center = panel {
                    label {
                        text = "Enter your name:"
                    }
                    textField {
                        columns = 20
                        text = "John Doe"
                    }
                    button {
                        text = "Submit"
                        addActionListener {
                            println("Submitted!")
                        }
                    }
                }
            }

            isVisible = true
        }
    }
}
```

**Explanation:**

- **`borderPane { ... }`**: Creates a `BorderPane` (a custom `JPanel` with `BorderLayout`) and allows assigning components to specific regions.
- **Regions (`north`, `south`, `east`, `west`, `center`)**: Assign components directly to these properties without automatic addition, thanks to the `isAutoAddEnabled()` mechanism.

#### SplitPane

Create a `JSplitPane` using the `splitPane` builder, assigning components to `leftComponent` and `rightComponent` without automatic additions.

```kotlin
package ca.weblite.ktswing.example

import ca.weblite.ktswing.custom.splitPane
import ca.weblite.ktswing.generated.button
import ca.weblite.ktswing.generated.label
import ca.weblite.ktswing.generated.panel
import javax.swing.JFrame
import javax.swing.SwingUtilities
import kotlin.system.exitProcess

fun main() {
    SwingUtilities.invokeLater {
        JFrame("SplitPane DSL Example").apply {
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            setSize(800, 600)

            // Create a main panel using the DSL
            panel {
                // Using the custom splitPane builder within the panel
                splitPane {
                    // Assigning components to leftComponent and rightComponent
                    leftComponent = label {
                        text = "Left Side Label"
                    }
                    rightComponent = panel {
                        label {
                            text = "Right Side Label"
                        }
                        button {
                            text = "Right Side Button"
                            addActionListener {
                                println("Button inside SplitPane clicked!")
                            }
                        }
                    }
                }
            }

            isVisible = true
        }
    }
}
```

**Explanation:**

- **`splitPane { ... }`**: Creates a `JSplitPane` and allows assigning components to `leftComponent` and `rightComponent` without automatic additions.
- **`leftComponent` & `rightComponent`**: Assign components directly, leveraging existing builder functions without triggering unintended additions.

## Generated Builders

`ktswing-main` auto-generates builder functions for common Swing components. These builders simplify component creation and configuration within containers.

### Example: `label`

```kotlin
/**
 * Creates a [JLabel] and conditionally adds it to the calling [Container].
 *
 * @param init Lambda to configure the [JLabel].
 * @return The configured [JLabel].
 */
fun Container.label(init: JLabel.() -> Unit = {}): JLabel {
    val component = JLabel()
    component.init()
    if (this.isAutoAddEnabled()) {
        this.add(component as Component)
    }
    return component
}
```

### Example: `comboBox` (Generic)

```kotlin
/**
 * Creates a [JComboBox] with type parameters and conditionally adds it to the calling [Container].
 *
 * @param init Lambda to configure the [JComboBox].
 * @return The configured [JComboBox].
 */
fun <E> Container.comboBox(init: JComboBox<E>.() -> Unit = {}): JComboBox<E> {
    val component = JComboBox<E>()
    component.init()
    if (this.isAutoAddEnabled()) {
        this.add(component as Component)
    }
    return component
}
```

**Note:** All builder functions check `isAutoAddEnabled()` before adding components, ensuring context-aware behavior.

## Extending the DSL

You can easily extend `ktswing-main` with custom builders or additional functionalities as needed. Here's how:

### Adding Custom Builders

For specialized containers or custom components, implement manual builders or extend existing ones. For example, to add a custom `BorderPane` builder:

```kotlin
package ca.weblite.ktswing.custom

import javax.swing.JSplitPane
import javax.swing.JComponent

/**
 * Creates a [JSplitPane] and adds it to the calling [Container].
 * Provides a DSL to assign components to left and right regions without automatic addition.
 *
 * Usage Example:
 * \```
* splitPane {
*     leftComponent = label { text = "Left Side" }
*     rightComponent = label { text = "Right Side" }
* }
* \```
*/
fun Container.splitPane(init: SplitPaneBuilder.() -> Unit = {}): JSplitPane {
val splitPane = JSplitPane()
this.add(splitPane)
val builder = SplitPaneBuilder(splitPane)
builder.init()
return splitPane
}

/**
* A builder class for JSplitPane that allows assigning components to left and right regions
* without automatic addition.
  */
  class SplitPaneBuilder(private val splitPane: JSplitPane) {

  /**
    * Assigns a component to the left side of the split pane.
      */
      var leftComponent: JComponent?
      get() = splitPane.leftComponent as? JComponent
      set(value) {
      splitPane.leftComponent = value
      }

  /**
    * Assigns a component to the right side of the split pane.
      */
      var rightComponent: JComponent?
      get() = splitPane.rightComponent as? JComponent
      set(value) {
      splitPane.rightComponent = value
      }
      }
```

**Explanation:**

- **`splitPane { ... }`**: Creates a `JSplitPane` and uses `SplitPaneBuilder` to assign components without triggering automatic additions.
- **`SplitPaneBuilder`**: Manages the assignment of components to specific regions of the `JSplitPane`.

### Creating Custom Components

You can also create builders for custom components by following the pattern of existing builders. Ensure they respect the `isAutoAddEnabled()` mechanism to maintain consistency.

## Contributing

Contributions are welcome! If you have suggestions, bug reports, or want to contribute enhancements, please follow these steps:

1. **Fork the Repository:**  
   Click the "Fork" button at the top-right corner of this page.

2. **Clone Your Fork:**
   ```bash
   git clone https://github.com/shannah/ktswing.git
   cd ktswing
   ```

3. **Create a New Branch:**
   ```bash
   git checkout -b feature/YourFeatureName
   ```

4. **Make Your Changes:**
   Implement your feature or fix.

5. **Commit Your Changes:**
   ```bash
   git commit -m "Add feature: YourFeatureName"
   ```

6. **Push to Your Fork:**
   ```bash
   git push origin feature/YourFeatureName
   ```

7. **Create a Pull Request:**
   Go to the original repository and create a pull request from your fork and branch.

## License

This project is licensed under the [MIT License](LICENSE).

---

*Developed with ❤️ by Weblite.ca*
```
