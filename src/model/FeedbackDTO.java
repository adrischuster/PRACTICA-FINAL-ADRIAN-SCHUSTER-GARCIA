package model;

public class FeedbackDTO {
    private boolean answered;
    private boolean correct;
    private String rationale;

    public FeedbackDTO() {
        this.answered = false;
    }

    public FeedbackDTO(boolean answered, boolean correct, String rationale) {
        this.answered = answered;
        this.correct = correct;
        this.rationale = rationale;
    }

    public boolean isAnswered() {
        return answered;
    }
    public boolean isCorrect() {
        return correct;
    }
    public String getRationale() {
        return rationale;
    }
}