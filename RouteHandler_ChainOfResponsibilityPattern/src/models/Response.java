package models;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private int statusCode;
    private String body;
    private Map<String,String> headers;
    private boolean sent; // prevent sending twice

    public Response(){
        this.headers = new HashMap<>();
        this.sent = false;
    }

    public void setHeader(String key, String value){
        this.headers.put(key,value);
    }


    public void sendResponse(int statusCode, String body) {
        if(sent == true){
            System.out.println("[Warning] Response already sent. Ignoring");
            return;
        }

        this.statusCode = statusCode;
        this.body = body;
        this.sent = true;
        System.out.println("Sending response with status code " + statusCode + " and body " + body);
    }

    // getters
    public int getStatusCode() {
        return statusCode;
    }
    public String getBody() {
        return body;
    }
    public Map<String, String> getHeaders() {
        return headers;
    }
    public boolean isSent() {
        return sent;
    }
}
