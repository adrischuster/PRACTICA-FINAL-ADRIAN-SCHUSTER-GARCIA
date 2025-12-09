package model;

import java.util.Set;
import java.util.UUID;
import java.util.List;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

public class Question implements Serializable {
    // Atributos
    private static final long serialVersionUID = 1L;
    private final UUID id;
    private String statement;
    private List<Option> options;
    private String author;
    private Set<String> topics;
    private final String creationDate;

    // Constructor
    public Question(String statement, List<Option> options, String author, Set<String> topics) {
        this.id = UUID.randomUUID();
        this.statement = statement;
        this.options = options;
        this.author = author;
        this.topics = topics;
        this.creationDate = formattedDate(Instant.now().getEpochSecond());
    }

    // Métodos
    public String formattedDate(long epochSeconds) {
        Instant instant = Instant.ofEpochSecond(epochSeconds);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public Set<String> getTopics() {
        return topics;
    }
    public void setTopics(Set<String> topics) {
        this.topics = topics;
    }

    public String getStatement() {
        return statement;
    }
    public void setStatement(String statement) {
        this.statement = statement;
    }

    public List<Option> getOptions() {
        return options;
    }
    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public String getCreationDate() {
        return creationDate;
    }
}
