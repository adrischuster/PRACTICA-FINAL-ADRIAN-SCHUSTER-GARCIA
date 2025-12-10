package model;

import java.io.Serializable;

public class Option implements Serializable {
    // Atributos
    private static final long serialVersionUID = 1L;
    private String text;
    private boolean correct;
    private String rationale;

    // Constructor
    public Option(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
        this.rationale = null;
    }

    public Option(String text, String rationale, boolean correct) {
        this(text, correct);
        this.rationale = rationale;
    }

    // Getters y Setters
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return correct;
    }
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getRationale() {
        return rationale;
    }
    public void setRationale(String rationale) {
        this.rationale = rationale;
    }
}
