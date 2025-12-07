package model;

public class Option {
    // Atributos
    private String text;
    private String rationale;
    private boolean correct;

    // Constructor
    public Option(String text, boolean correct) {
        this.text = text;
        this.rationale = null;
        this.correct = correct;
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

    public String getRationale() {
        return rationale;
    }
    public void setRationale(String rationale) {
        this.rationale = rationale;
    }

    public boolean isCorrect() {
        return correct;
    }
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
