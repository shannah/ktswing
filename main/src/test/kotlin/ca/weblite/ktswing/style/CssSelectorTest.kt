package ca.weblite.swinky.style

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import javax.swing.JPanel
import javax.swing.JComponent
import java.awt.Button

/**
 * Unit tests for the CssSelector class.
 */
class CssSelectorTest {

    /**
     * Helper function to create a JComponent (using JPanel) with the given [name] and [cssClasses].
     * The CSS classes are stored in the client property "cssClasses".
     */
    private fun createComponent(name: String? = null, cssClasses: String? = ""): JComponent {
        val comp = JPanel()
        comp.name = name
        // Always set the "cssClasses" client property (even as an empty string) so that matchClass
        // does not immediately return false.
        comp.putClientProperty("cssClasses", cssClasses)
        return comp
    }

    @Test
    fun `test id selector matches`() {
        // Create a component with the expected id ("myId") and an empty cssClasses string.
        val comp = createComponent(name = "myId", cssClasses = "")
        val selector = CssSelector("#myId")
        assertTrue(selector.matches(comp), "Component with matching id should match")
    }

    @Test
    fun `test id selector fails when id does not match`() {
        val comp = createComponent(name = "otherId", cssClasses = "")
        val selector = CssSelector("#myId")
        assertFalse(selector.matches(comp), "Component with non-matching id should not match")
    }

    @Test
    fun `test id and class selector matches`() {
        // For an id+class selector "#myId.foo", the component must have name "myId"
        // and a cssClasses property that (when split on spaces) contains "foo".
        val comp = createComponent(name = "myId", cssClasses = "foo bar")
        val selector = CssSelector("#myId.foo")
        assertTrue(selector.matches(comp), "Component with matching id and containing class should match")
    }

    @Test
    fun `test id and class selector fails when class missing`() {
        val comp = createComponent(name = "myId", cssClasses = "bar")
        val selector = CssSelector("#myId.foo")
        assertFalse(selector.matches(comp), "Component with matching id but missing class should not match")
    }

    @Test
    fun `test class only selector matches`() {
        // For a class-only selector ".foo", the component must be a JComponent with cssClasses containing "foo".
        val comp = createComponent(cssClasses = "foo")
        val selector = CssSelector(".foo")
        assertTrue(selector.matches(comp), "Component with matching class should match")
    }

    @Test
    fun `test class only selector fails when class missing`() {
        val comp = createComponent(cssClasses = "bar")
        val selector = CssSelector(".foo")
        assertFalse(selector.matches(comp), "Component without required class should not match")
    }

    @Test
    fun `test glob selector matches any component without restrictions`() {
        // A glob selector (one that does not start with '#' or '.') creates a SelectorDescription with glob=true.
        // Since no id or class is checked, the matches() method falls back to matchParent(), which returns true
        // when the selector has no parent. Here we use a Component that is not a JComponent.
        val nonJComponent = Button("Test Button") // java.awt.Button is a Component but not a JComponent.
        val selector = CssSelector("button")
        assertTrue(selector.matches(nonJComponent), "Glob selector should match any Component when no conditions")
    }

    @Test
    fun `test parent descendant selector matches`() {
        // Build a hierarchy:
        //   parent (id="parent")
        //     └── child (id="child")
        // Use a descendant selector ("#parent #child") where the child may have a matching ancestor.
        val parent = createComponent(name = "parent", cssClasses = "")
        val child = createComponent(name = "child", cssClasses = "")
        parent.add(child)  // Automatically sets child.parent = parent.
        val selector = CssSelector("#parent #child")
        assertTrue(selector.matches(child), "Child should match descendant selector when parent's id matches")
    }

    @Test
    fun `test parent descendant selector fails when no matching ancestor`() {
        // Parent does not match the required selector "#parent" so the child should not match.
        val parent = createComponent(name = "other", cssClasses = "")
        val child = createComponent(name = "child", cssClasses = "")
        parent.add(child)
        val selector = CssSelector("#parent #child")
        assertFalse(selector.matches(child), "Child should not match descendant selector when ancestor id does not match")
    }

    @Test
    fun `test direct child selector matches`() {
        // For a direct child selector ("#parent > #child"), the child's immediate parent must match.
        val parent = createComponent(name = "parent", cssClasses = "")
        val child = createComponent(name = "child", cssClasses = "")
        parent.add(child)
        val selector = CssSelector("#parent > #child")
        assertTrue(selector.matches(child), "Child should match direct child selector when parent's id matches directly")
    }

    @Test
    fun `test direct child selector fails when not immediate`() {
        // Build a three-level hierarchy:
        //   grandparent (id="parent")
        //     └── intermediate (id irrelevant)
        //           └── child (id="child")
        // With a direct child selector, the child must be an immediate child of the element matching "#parent".
        val grandparent = createComponent(name = "parent", cssClasses = "")
        val intermediate = createComponent(name = "intermediate", cssClasses = "")
        val child = createComponent(name = "child", cssClasses = "")
        grandparent.add(intermediate)
        intermediate.add(child)
        val selector = CssSelector("#parent > #child")
        assertFalse(selector.matches(child), "Child should not match direct child selector when matching ancestor is not immediate")
    }

    @Test
    fun `test non-component returns false`() {
        // Passing an object that is not a Component should result in matches() returning false.
        val selector = CssSelector(".foo")
        val notAComponent = "I am not a component"
        assertFalse(selector.matches(notAComponent), "Non-component should not match")
    }

    @Test
    fun `test multiple class selector matches`() {
        // For a selector with multiple classes like ".foo.bar", the component must have both "foo" and "bar".
        val comp = createComponent(cssClasses = "foo bar baz")
        val selector = CssSelector(".foo.bar")
        assertTrue(selector.matches(comp), "Component with multiple matching classes should match")
    }

    @Test
    fun `test multiple class selector fails when one class is missing`() {
        val comp = createComponent(cssClasses = "foo baz")
        val selector = CssSelector(".foo.bar")
        assertFalse(selector.matches(comp), "Component missing one of the required classes should not match")
    }

    @Test
    fun `test descendant selector with missing immediate parent but matching ancestor`() {
        // Build a hierarchy where the immediate parent does not match, but a higher-level ancestor does:
        //   grandparent (cssClasses = "foo")
        //     └── parent (no classes)
        //           └── child (cssClasses = "bar")
        // The selector ".foo .bar" should match the child because at some level an ancestor (grandparent) has "foo".
        val grandparent = createComponent(cssClasses = "foo")
        val parent = createComponent(cssClasses = "")
        val child = createComponent(cssClasses = "bar")
        grandparent.add(parent)
        parent.add(child)
        val selector = CssSelector(".foo .bar")
        assertTrue(selector.matches(child), "Child should match descendant selector when an ancestor has matching class")
    }

    @Test
    fun `test direct child selector fails in descendant hierarchy`() {
        // With a direct child combinator (">"), the immediate parent must match.
        // Here, even though a grandparent has "foo", the immediate parent does not, so the selector should fail.
        val grandparent = createComponent(cssClasses = "foo")
        val parent = createComponent(cssClasses = "")
        val child = createComponent(cssClasses = "bar")
        grandparent.add(parent)
        parent.add(child)
        val selector = CssSelector(".foo > .bar")
        assertFalse(selector.matches(child), "Child should not match direct child selector when immediate parent does not match")
    }

    // ----------------------------
    // New tests for the adjusted behavior:
    // ----------------------------

    @Test
    fun `test id only selector ignores component css classes`() {
        // For an id-only selector, the css classes of the component should not affect matching.
        val comp = createComponent(name = "myId", cssClasses = "irrelevant")
        val selector = CssSelector("#myId")
        assertTrue(selector.matches(comp), "Id-only selector should match even if component has non-matching css classes")
    }

    @Test
    fun `test class only selector ignores component id`() {
        // For a class-only selector, the component's id should be ignored.
        val comp = createComponent(name = "nonMatchingId", cssClasses = "foo")
        val selector = CssSelector(".foo")
        assertTrue(selector.matches(comp), "Class-only selector should match regardless of component id")
    }

    @Test
    fun `test id only selector matches when cssClasses property is null`() {
        // Create a JPanel without setting the "cssClasses" client property.
        val comp = JPanel().apply { name = "myId" }
        val selector = CssSelector("#myId")
        assertTrue(selector.matches(comp), "Id-only selector should match even if cssClasses property is null")
    }

    @Test
    fun `test class only selector returns false when cssClasses property is null`() {
        // Create a JPanel without setting the "cssClasses" client property.
        val comp = JPanel().apply { name = "anything" }
        val selector = CssSelector(".foo")
        assertFalse(selector.matches(comp), "Class-only selector should not match if cssClasses property is null")
    }
}
