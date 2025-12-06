package view;

import controller.Controller;

public abstract class BaseView {
    // Atributos
    protected Controller controller;

    // Métodos
    public abstract void init();
    public abstract void menuCRUD();
    public abstract void menuListar();

    public abstract void showMessage(String msg);

    public abstract void showErrorMessage(String msg);

    public abstract void end();

    // Getters y setters
    public void setController(Controller controller) {
        this.controller = controller;
    }
}
