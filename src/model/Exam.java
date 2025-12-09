package model;

import java.util.List;

public class Exam {
    // Atributos
    private final String topic;
    private final int numQuestions;
    private final List<Question> questions;

    // Constructores
    public Exam(String topic, int numQuestions, List<Question> questions) {
        this.topic = topic;
        this.numQuestions = numQuestions;
        this.questions = questions;
    }

    // Métodos

    
    // Getters
    public String getTopic() {
        return topic;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}