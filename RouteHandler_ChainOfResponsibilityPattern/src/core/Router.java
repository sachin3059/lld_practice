package core;

import enums.HttpMethod;
import models.Request;
import models.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Router {
    private TrieNode root;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public Router() {
        root = new TrieNode();
    }

    public void addRoute(HttpMethod method, String url, List<Middleware> middlewares) {
        lock.writeLock().lock();
        try {
            String[] words =  url.split("/");
            TrieNode node = root;

            for(int i = 1; i < words.length; i++) {
                node.children.putIfAbsent(words[i], new TrieNode());
                node = node.children.get(words[i]);
            }

            node.handlers.put(method, middlewares);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void callRoute(HttpMethod method, String url, String body, Map<String, String> headers) {
        lock.readLock().lock();
        try {
            String[] words =  url.split("/");
            List<TrieNode> matches = new ArrayList<>();

            dfs(root, words, 1, matches);

            if(matches.isEmpty()) {
                Response response = new Response();
                response.sendResponse(404, "Route not found: " + method + " " + url);
            }

            for(TrieNode node : matches) {
                List<Middleware> chain = node.handlers.get(method);
                if(chain != null) {
                    Request req = new Request(method, url, body, headers);
                    Response res = new Response();
                    executeChain(chain, req, res);
                }
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    public void dfs(TrieNode node, String[] segments, int level, List<TrieNode> matches) {
        if(level == segments.length) {
            matches.add(node);
            return;
        }

        String word = segments[level];

        if(word.equals("*")) {
            for(TrieNode child : node.children.values()) {
                dfs(child, segments, level + 1, matches);
            }
        }
        else{
            if(node.children.containsKey(word)) {
                dfs(node.children.get(word), segments, level + 1, matches);
            }

            if(node.children.containsKey("*")) {
                dfs(node.children.get("*"), segments, level + 1, matches);
            }
        }
    }

    private void executeChain(List<Middleware> middlewares, Request request, Response response) {
        executeFromIndex(middlewares, 0, request, response);
    }

    private void executeFromIndex(List<Middleware> middlewares, int level, Request request, Response response) {
        if(level == middlewares.size()) {
            return;
        }

        Middleware m = middlewares.get(level);
        NextHandler next = () -> executeFromIndex(middlewares, level + 1, request, response);

        m.handle(request, response, next);
    }
}
