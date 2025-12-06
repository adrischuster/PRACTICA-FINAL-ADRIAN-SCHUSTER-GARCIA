import controller.Controller;
import model.Model;
import view.BaseView;

// Arreglar commit en GitHub --------------------

public class App {
    public static void main(String[] args) throws Exception {
        // Settear
        Model model = new Model(); // Pedir por E/S el tipo de ¿guardado?
        BaseView view = new BaseView(); // Pedir por E/S la subclase con new Subclase()

        Controller controller = new Controller(model, view);

        // Iniciar app
        controller.load();

        controller.init();
    }
}
