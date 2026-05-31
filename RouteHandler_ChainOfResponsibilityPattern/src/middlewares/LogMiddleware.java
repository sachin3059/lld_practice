package middlewares;

import core.Middleware;
import models.Request;
import models.Response;

public class LogMiddleware implements Middleware {
    public void handle(Request request, Response response, Runnable next) {
        System.out.println("LogMiddleware.handle");
        next.run();
    }
}
