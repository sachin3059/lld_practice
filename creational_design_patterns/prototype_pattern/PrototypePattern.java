package creational_design_patterns.prototype_pattern;

abstract class Document implements Cloneable {
    protected String content;
    protected String author;

    public Document clone() {
        try {
            return (Document) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public void setContent(String content) { this.content = content;}
    public abstract void show();
}

class Resume extends Document{
    public Resume(String author){
        this.author = author;
        this.content = "Default resume template";
    }

    public void show(){
        System.out.println("Author: " + author + ", Content: " + content);
    }
}

public class PrototypePattern {
    // Resume original = new Resume("Rahul");
    // Resume copy = (Resume) original.clone();
    // copy.setContent("Customized resume for google");

    // original.show();
    // copy.show();
}
