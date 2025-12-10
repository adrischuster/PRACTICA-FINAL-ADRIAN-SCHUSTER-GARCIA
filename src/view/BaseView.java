package view;

import controller.Controller;
import model.Question;
import model.FeedbackDTO;
import model.Exam;

public abstract class BaseView {
    // Atributos
    protected Controller controller;

    // Métodos
    public abstract void showMessage(String msg);

    public abstract void showErrorMessage(String msg);

    public abstract void showQuestion(Question question);

    public abstract void askQuestion(Question q);

    public abstract int showChoiceMessage(int choiceSize);

    public abstract boolean showYesOrNo(String msg);

    public abstract void askInput(String msg);

    public abstract void init();

    public abstract void showDetail(Question q);

    public abstract String selectTopic();

    public abstract String selectExamTopic();

    public abstract int askNumQuestions(int maxQuestions);

    public abstract Integer answerQuestion();

    public abstract void feedbackAnswer(FeedbackDTO feedback);

    public abstract void showResults(Exam exam);

    public abstract void end();

    // Getters y setters
    public void setController(Controller controller) {
        this.controller = controller;
    }
}
