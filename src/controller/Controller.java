package controller;

import model.Model;
import model.Question;
import view.BaseView;
import java.util.List;
import model.Option;
import java.util.Set;
import model.Exam;

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
    // INICIAR
    public void loadQuestions() {
        try {
            boolean previousSave = model.loadQuestions();
            if (previousSave) {
                view.showMessage("\nGuardado previo cargado correctamente.");
            } else {
                view.showMessage("\nNo se han encontrado datos previos.");
            }
        } catch (Exception e) {
            view.showErrorMessage("Error al cargar las preguntas: " + e.getMessage());
        }
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

    // FUNCIONES MENÚ CRUD
    public void addQuestion(Question question) throws Exception {
        model.addQuestion(question);
    }

    public List<Question> getFilteredQuestions(String topic) {
        return model.getFilteredQuestions(topic);
    }

    public void showDetail(List<Question> questions) {
        // Escribir en el informe el intento de posible separación de estos 2 métodos en otra parte del menú
        // Implementar while?
        if (view.showYesOrNo("¿Desea ver el detalle de alguna pregunta?")) {
            int index = view.showChoiceMessage(questions.size()) - 1;
            Question questionForDetail = questions.get(index);
            view.showDetail(questionForDetail);
        }
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

    public void updateAllTopics(String oldTopic, String newTopic) {
        model.updateAllTopics(oldTopic, newTopic);
    }

    public void remove(Question question) throws Exception {
        model.remove(question);
    }

    public void removeAllTopics(Set<String> topics) {
        model.removeAllTopics(topics);
    }

    // FUNCIONES BACKUP
    public void exportQuestions(String fileName) throws Exception {
        model.exportQuestions(fileName);
    }

    public void importQuestions(String fileName) throws Exception {
        model.importQuestions(fileName);
    }

    // FUNCIONES EXAMEN
    public void examMode() {
        Set<String> allTopics = model.getAllTopics();
        String T = view.selectExamTopic(allTopics);

        int maxQuestions = model.getMaxQuestions(T);
        int N = view.askNumQuestions(maxQuestions);

        Exam exam = model.configureExam(T, N); 
        for (Question question : exam.getQuestions()) {
            view.showQuestion(question);
            if (view.showYesOrNo("¿Responder?")) {
                int choice = view.showChoiceMessage(4);
                Option answer = model.addAnswer(exam, question, answer);
                if (answer.isCorrect()) {
                    view.showMessage("Respuesta correcta.");
                } else {
                    view.showMessage("Respuesta incorrecta.");
                }
                view.showMessage(answer.getRationale());
            }
        }

    }

    public int maxQuestionsByTopic(String topic) {
        int maxQuestions = model.getMaxQuestions(topic);
        return maxQuestions;
    }

    public void showQuestion(Question question) {
        view.showQuestion(question);
    }

    // CERRAR
    public void saveQuestions() throws Exception {
        model.saveQuestions();
    }
}
