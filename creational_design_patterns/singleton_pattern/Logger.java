

public class Logger {
    
    // 1. Private static instance (volatile for thread safety)
    private static volatile Logger instance;

    // 2. Private constructor - prevents external instantiation
    private Logger(){
        if(instance != null){
            throw new RuntimeException("Use getInstance method");
        }
    }

    // 3. Public static method to get instance (Double checked locking)
    public static Logger getInstance(){
        if(instance == null){
            synchronized(Logger.class){
                if(instance == null){
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    public void log(String message){
        System.out.println("[LOG]: " + message);
    }
}


/*
usage
Logger logger1 = Logger.getInstance();
Logger logger2 = Logger.getInstance();
System.out.println(logger1 == logger2);   -> you will get true.
 */
