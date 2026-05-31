package middlewares;

import core.Middleware;
import models.Request;
import models.Response;

public class AuthMiddleware implements Middleware {
    public void handle(Request request, Response response, Runnable next) {
        System.out.println("AuthMiddleware.handle");
        boolean isAuthenticated = true;
        if(isAuthenticated){
            System.out.println("AuthMiddleware isAuthenticated");
            next.run();
        }
        else{
            response.sendResponse(401, "Unauthorized");
        }
    }
}
