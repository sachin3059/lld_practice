package models;

import enums.HttpMethod;

public class Request {
    String path;
    HttpMethod method;
    String body;

    public Request(HttpMethod method, String path) {
        this.path = path;
        this.method = method;
    }
}
