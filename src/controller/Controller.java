package controller;

import model.Model;
import model.Question;
import view.BaseView;
import java.util.List;

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

    public List<Question> getQuestions() {
        return model.getQuestions();
    }

    public void addQuestion(Question question) throws Exception {
        model.addQuestion(question);
    }

    public 

    public void init() {
        view.init();
    }
}
