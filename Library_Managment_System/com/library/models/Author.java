package com.library.models;

public class Author {
    private String name;
    private String bio;

    public Author(String name, String bio) {
        this.name = name;
        this.bio  = bio;
    }

    public String getName() { return name; }
    public String getBio()  { return bio; }

    @Override
    public String toString() { return name; }
}


