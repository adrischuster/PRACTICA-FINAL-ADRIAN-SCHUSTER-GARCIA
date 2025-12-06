package model;

import java.util.ArrayList;

public class Model {
    // Atributos
    private IRepository repository;
    private QuestionBackupIO backupHandler;
    private ArrayList<QuestionCreator> questionCreators;
    private ArrayList<Question> preguntas;

    // Constructores
    public Model(IRepository repository, QuestionBackupIO backupHandler, ArrayList<QuestionCreator> questionCreators) {
        this.repository = repository;
        this.backupHandler = backupHandler;
        this.questionCreators = questionCreators;
    }
}
