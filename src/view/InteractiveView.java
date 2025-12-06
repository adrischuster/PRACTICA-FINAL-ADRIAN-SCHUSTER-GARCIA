package view;

public class InteractiveView extends BaseView {
    //Atributos
    
    //Métodos
    @Override
    public void init() {
    
    }

    @Override
    public void showMessage(String msg) {
        System.out.println(msg);
    }

    @Override
    public void showErrorMessage(String msg) {
        System.err.println(msg);
    }

    @Override
    public void end() {
        // ¿Qúe hace?
    }

}
