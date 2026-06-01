import core.Router;
import enums.HttpMethod;
import middlewares.AuthMiddleware;
import middlewares.LogMiddleware;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Router router = new Router();

        router.addRoute(
                HttpMethod.GET,
                "user/profile",
                Arrays.asList(
                        new AuthMiddleware(),
                        new LogMiddleware(),
                        (req, res, next) -> res.sendResponse(200, "User profile data")
                )
        );

        router.addRoute(
                HttpMethod.POST,
                "user/profile",
                Arrays.asList(
                        new AuthMiddleware(),
                        (req, res, next) -> res.sendResponse(201, "Profile updated")
                )
        );

        // Build headers like a real HTTP request
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer test-token-123");
        headers.put("Content-Type", "application/json");

        router.callRoute(HttpMethod.GET, "user/profile", "", headers);
    }
}