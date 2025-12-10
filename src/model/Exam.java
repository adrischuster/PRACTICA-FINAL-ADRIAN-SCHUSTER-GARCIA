package model;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class Exam {
    // Atributos
    private final String topic;
    private final int numQuestions;
    private final List<Question> questions;
    private int correctAnswers = 0;
    private int incorrectAnswers = 0;
    private int unanswered = 0;
    private Instant startTime;
    private Duration duration;
    private float result = 0;

    // Constructores
    public Exam(String topic, int numQuestions, List<Question> questions) {
        this.topic = topic;
        this.numQuestions = numQuestions;
        this.questions = questions;
    }

    // Métodos
    public void addCorrectAnswer() {
        correctAnswers++;
    }

    public void addIncorrectAnswer() {
        incorrectAnswers++;
    }

    public void addUnanswered() {
        unanswered++;
    }
    
    public void startExam() {
        setStartTime(Instant.now());
    }

    public void endExam() {
        Instant endTime = Instant.now();
        setDuration(Duration.between(startTime, endTime));
    }

    public String durationToString() {
        long min = duration.toMinutes();
        long sec = duration.toSecondsPart();
        return String.format("%02d min %02d seg", min, sec);
        // poner record?
    }

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

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public int getUnanswered() {
        return unanswered;
    }

    public Instant getStartTime() {
        return startTime;
    }
    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public float getResult() {
        return result;
    }
    public void setResult(float result) {
        this.result = result;
    }
}