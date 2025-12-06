package controller;

import model.Model;
import view.BaseView;

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
        // Lógica de carga, utilizar if else
        
        // Mostrar mensaje por view
    }

    public void init() {
        view.init();
    }
}
