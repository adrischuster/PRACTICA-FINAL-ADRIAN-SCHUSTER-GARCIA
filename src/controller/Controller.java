package controller;

import model.Model;
import model.Question;
import view.BaseView;
import java.util.List;
import model.Option;
import java.util.Set;
import model.Exam;
import model.FeedbackDTO;

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

    // FUNCIONES MENÚ CRUD
    public void addQuestion(Question question) throws Exception {
        model.addQuestion(question);
    }

    public void removeQuestion(Question question) throws Exception {
        model.removeQuestion(question);
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

    public void addOnlyAllTopics(String newTopic) {
        model.addOnlyAllTopics(newTopic);
    }

    public void removeOnlyAllTopics(String oldTopic) {
        model.removeOnlyAllTopics(oldTopic);
    }

    public void updateAllTopics(String oldTopic, String newTopic) {
        model.updateAllTopics(oldTopic, newTopic);
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
        view.showMessage("\nIniciando Modo Examen...");
        String T = view.selectExamTopic();

        int maxQuestions = maxQuestionsByTopic(T);
        int N;
        if (maxQuestions == 1) {
            view.showErrorMessage("\nSolo hay una pregunta disponible.");
            N = 1;
        } else {
            N = view.askNumQuestions(maxQuestions);
        }

        do {
            Exam exam = model.configureExam(T, N); 
            view.askInput("\nPulse INTRO para comenzar el examen.");
            model.startExam(exam);
            for (Question q : exam.getQuestions()) {
                // añadir nº de pregunta? ó 'Siguiente pregunta'?
                view.askQuestion(q);
                Integer answer = view.answerQuestion();
                FeedbackDTO feedback = model.studyAnswer(exam, q, answer);
                view.feedbackAnswer(feedback);
            }

            model.evaluateExam(exam);
            view.showResults(exam);
        } while (view.showYesOrNo("\n¿Desea repetir el examen?"));
    }

    public int maxQuestionsByTopic(String topic) {
        return model.getMaxQuestions(topic);
    }

    public void showQuestion(Question question) {
        view.showQuestion(question);
    }

    // FUNCIONES AUXILIARES
    public List<Question> getQuestions() {
        return model.getQuestions();
    }

    public boolean hasQuestions() {
        return model.hasQuestions();
    }

    public Set<String> getAllTopics() {
        return model.getAllTopics();
    }

    // CERRAR
    public void saveQuestions() throws Exception {
        model.saveQuestions();
    }
}
