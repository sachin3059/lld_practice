// Main.java
public class Main {
    public static void main(String[] args) {
        User user = new User("John Doe", "john.doe@example.com");
        UserFileManager fileManager = new UserFileManager();
        fileManager.saveToFile(user);
    }
}