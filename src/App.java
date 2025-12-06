import controller.Controller;
import model.BinaryRepository;
import model.Model;
import model.QuestionBackupIO;
import model.IRepository;
import model.JSONQuestionBackupIO;
import java.util.ArrayList;
import model.QuestionCreator;
import view.BaseView;
import view.InteractiveView;

// PROBLEMA GORDO CON GITHUB EN APP.JAVA

public class App {
    public static void main(String[] args) throws Exception {
        // Settear
        IRepository repository = new BinaryRepository();
        QuestionBackupIO backupHandler = new JSONQuestionBackupIO();
        ArrayList<QuestionCreator> questionCreators = new ArrayList<>();

        // Implementar estructura para QuestionCreator

        Model model = new Model(repository, backupHandler, questionCreators); // Settear para parámetro de QuestionCreator -> ¿Por qué un ArrayList?
        BaseView view = new InteractiveView(); // ¿Abstraer más para nuevas posibles vistas?

        Controller controller = new Controller(model, view);

        // Iniciar app
        controller.load();

        controller.init();
    }
}
