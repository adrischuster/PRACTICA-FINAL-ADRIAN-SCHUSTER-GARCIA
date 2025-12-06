package model;

import java.util.HashSet;
import java.util.UUID;
import java.util.ArrayList;

public class Question {
    // Atributos
    private UUID id;
    private String author;
    private HashSet<String> topics;
    private String statement;
    private ArrayList<Option> options;

    // Constructor
    public Question(String author, HashSet<String> topics, String statement, ArrayList<Option> options) {
        this.id = UUID.randomUUID(); // ¿Genero así?
        this.author = author;
        this.topics = topics;
        this.statement = statement;
        this.options = options;
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public HashSet<String> getTopics() {
        return topics;
    }
    public void setTopics(HashSet<String> topics) {
        this.topics = topics;
    }

    public String getStatement() {
        return statement;
    }
    public void setStatement(String statement) {
        this.statement = statement;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }
    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }
}
