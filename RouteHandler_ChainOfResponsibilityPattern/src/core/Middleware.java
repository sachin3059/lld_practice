package core;

import models.Request;
import models.Response;

@FunctionalInterface
public interface Middleware {
    void handle(Request request, Response response, Runnable nextHandler);
}
