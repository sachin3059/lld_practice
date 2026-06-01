package middlewares;

import core.Middleware;
import core.NextHandler;
import models.Request;
import models.Response;

public class LogMiddleware implements Middleware {
    public void handle(Request request, Response response, NextHandler next) {
        System.out.println("LogMiddleware.handle");
        next.proceed();
    }
}
