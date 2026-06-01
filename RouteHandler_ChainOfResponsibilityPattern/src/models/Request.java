package models;

import enums.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private String path;
    private HttpMethod method;
    private String body;
    private Map<String, String> headers;


    public Request(HttpMethod method, String path, String body, Map<String, String> headers) {
        this.method = method;
        this.path = path;
        this.body = body;
        this.headers = headers != null ? headers : new HashMap<>();
    }

    // getters
    public String getPath() {
        return path;
    }

    public String getBody() {
        return body;
    }

    public HttpMethod getMethod() {
        return method;
    }
    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String key) {
        return headers.getOrDefault(key, null);
    }
}
