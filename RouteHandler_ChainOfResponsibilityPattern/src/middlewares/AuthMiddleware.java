package middlewares;

import core.Middleware;
import core.NextHandler;
import models.Request;
import models.Response;

public class AuthMiddleware implements Middleware {
    public void handle(Request request, Response response, NextHandler next) {
        System.out.println("AuthMiddleware.handle");
        boolean isAuthenticated = true;
        if(isAuthenticated){
            System.out.println("AuthMiddleware isAuthenticated");
            next.proceed();
        }
        else{
            response.sendResponse(401, "Unauthorized");
        }
    }
}
