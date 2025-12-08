package controller;

import model.Model;
import model.Question;
import view.BaseView;
import java.util.List;
import model.Option;
import java.util.Set;

public class Controller {
    // Atributos
    private Model model;
    private BaseView view;

    // Constructores
    public Controller(Model model, BaseView view) {
        this.model = model;
        this.view = view;
        
        view.setController(this);
    }

    // Métodos
    public void load() {
        // Lógica de carga para IRepo, utilizar if else
        
        // Mostrar mensaje por view
    }

    public void init() {
        view.init();
    }

    public List<Question> getQuestions() {
        return model.getQuestions();
    }

    public Set<String> getAllTopics() {
        return model.getAllTopics();
    }

    public void addQuestion(Question question) throws Exception {
        model.addQuestion(question);
    }

    public void addAllTopics(Set<String> topics) {
        model.addAllTopics(topics);
    }

    public void removeAllTopics(Set<String> topics) {
        model.removeAllTopics(topics);
    }

    public void updateAllTopics(String oldTopic, String newTopic) {
        model.updateAllTopics(oldTopic, newTopic);
    }

    public void updateStatement(Question question, String newStatement) {
        model.updateStatement(question, newStatement);
    }

    public void updateOption(Question question, int optionNumber, Option newOption) {
        model.updateOption(question, optionNumber, newOption);
    }

    public void updateAuthor(Question question, String newAuthor) {
        model.updateAuthor(question, newAuthor);
    }

    public void updateTopic(Question question, String oldTopic, String newTopic) {
        model.updateTopic(question, oldTopic, newTopic);
    }

    public void remove(Question question) throws Exception {
        model.remove(question);
    }
}
