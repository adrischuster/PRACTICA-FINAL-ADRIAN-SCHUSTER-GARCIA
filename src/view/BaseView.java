package view;

import controller.Controller;
import model.Question;
import java.util.Set;

public abstract class BaseView {
    // Atributos
    protected Controller controller;

    // Métodos
    public abstract void showMessage(String msg);

    public abstract void showErrorMessage(String msg);

    public abstract void showQuestion(Question question);

    public abstract int showChoiceMessage(int choiceSize);

    public abstract boolean showYesOrNo(String msg);

    public abstract void init();

    public abstract void showDetail(Question q);

    public abstract String selectTopic(Set<String> allTopics);

    public abstract String selectExamTopic(Set<String> allTopics);

    public abstract int askNumQuestions(int maxQuestions);

    public abstract void end();

    // Getters y setters
    public void setController(Controller controller) {
        this.controller = controller;
    }
}
