package model;

import java.util.List;
import java.util.ArrayList;

public class Model {
    // Atributos
    private IRepository repository;
    private QuestionBackupIO backupHandler;
    private List<QuestionCreator> questionCreators;
    private List<Question> questions = new ArrayList<>();

    // Constructores
    public Model(IRepository repository, QuestionBackupIO backupHandler, List<QuestionCreator> questionCreators) {
        this.repository = repository;
        this.backupHandler = backupHandler;
        this.questionCreators = questionCreators;
    }

    // Métodos
    public void load() {
        // Lógica de carga
        // Aquí setteo las preguntas, y las cojo siempre del Model con preguntas (atributo de la misma clase)
    }

    public void addQuestion(Question question) throws Exception {
        questions.add(question);
    }

    // Getters y setters
    public List<Question> getQuestions() {
        return questions;
    }
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
