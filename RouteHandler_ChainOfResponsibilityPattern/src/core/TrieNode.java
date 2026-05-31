package core;

import enums.HttpMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieNode {
    Map<String, TrieNode> children;
    Map<HttpMethod, List<Middleware>> handlers;

    public TrieNode() {
        children = new HashMap<>();
        handlers = new HashMap<>();
    }
}
