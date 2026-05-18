/*
-> A client shoudl never be forced to implement an interface it does not use.
-> It is better to have many small specific interfaces than one large.
*/


public interface Printer {
    void printDocument();
    void scanDocument();
    void faxDocument();
}

class SimplePrinter implements Printer {
    public void printDocument() {
        System.out.println("Printing document...");
    }

    public void scanDocument() {
        // Not implemented
    }

    public void faxDocument() {
        // Not implemented
    }

    public static void main(String[] args) {
        SimplePrinter printer = new SimplePrinter();
        printer.printDocument();
    }
}