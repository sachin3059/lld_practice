// Printer.java
public interface Printer {
    void printDocument();
}

// Scanner.java
public interface Scanner {
    void scanDocument();
}

// Fax.java
public interface Fax {
    void faxDocument();
}

// SimplePrinter.java
public class SimplePrinter implements Printer {
    public void printDocument() {
        System.out.println("Printing document...");
    }
}

// MultiFunctionPrinter.java
public class MultiFunctionPrinter implements Printer, Scanner, Fax {
    public void printDocument() {
        System.out.println("Printing document...");
    }

    public void scanDocument() {
        System.out.println("Scanning document...");
    }

    public void faxDocument() {
        System.out.println("Faxing document...");
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        Printer simplePrinter = new SimplePrinter();
        simplePrinter.printDocument();

        MultiFunctionPrinter mfp = new MultiFunctionPrinter();
        mfp.printDocument();
        mfp.scanDocument();
        mfp.faxDocument();
    }
}