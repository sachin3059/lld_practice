package models;

public class Response {
    int statusCode;
    String body;


    public void sendResponse(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
        System.out.println("Sending response with status code " + statusCode + " and body " + body);
    }
}
