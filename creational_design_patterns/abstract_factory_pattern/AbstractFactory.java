package creational_design_patterns.abstract_factory_pattern;

// Abstract products
interface Button { void render(); }
interface Checkbox { void render(); }

// Concrete products — Windows family
class WindowsButton implements Button {
    public void render() { System.out.println("Windows Button"); }
}
class WindowsCheckbox implements Checkbox {
    public void render() { System.out.println("Windows Checkbox"); }
}

// Concrete products — Mac family
class MacButton implements Button {
    public void render() { System.out.println("Mac Button"); }
}
class MacCheckbox implements Checkbox {
    public void render() { System.out.println("Mac Checkbox"); }
}

// Abstract Factory
interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

// Concrete factories
class WindowsFactory implements GUIFactory {
    public Button createButton() { return new WindowsButton(); }
    public Checkbox createCheckbox() { return new WindowsCheckbox(); }
}

class MacFactory implements GUIFactory {
    public Button createButton() { return new MacButton(); }
    public Checkbox createCheckbox() { return new MacCheckbox(); }
}

public class AbstractFactory {
    public static void main(String[] args) {
        // Usage
        GUIFactory factory = new MacFactory(); // decided at runtime
        Button button = factory.createButton();
        Checkbox checkbox = factory.createCheckbox();
        button.render();   // Mac Button
        checkbox.render(); // Mac Checkbox
    }
}



