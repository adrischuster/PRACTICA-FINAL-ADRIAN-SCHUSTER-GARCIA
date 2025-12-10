package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.lang.Integer;

public class Model {
    // Atributos
    private IRepository repository;
    private QuestionBackupIO backupHandler;
    private List<QuestionCreator> questionCreators;
    // Comentar en informe esta decisión
    private List<Question> questions = new ArrayList<>();
    private Set<String> allTopics = new HashSet<>();
    private static final String ALL_TOPICS = "TODOS";

    // Constructores
    public Model(IRepository repository, QuestionBackupIO backupHandler, List<QuestionCreator> questionCreators) {
        this.repository = repository;
        this.backupHandler = backupHandler;
        this.questionCreators = questionCreators;
    }

    // Métodos
    // INICIAR
    public boolean loadQuestions() throws Exception {
        List<Question> loadedQuestions = repository.loadQuestions();
        
        setQuestions(loadedQuestions);
        for (Question question : loadedQuestions) {
            allTopics.addAll(question.getTopics());
        }

        boolean previousSave = true;
        if (loadedQuestions.isEmpty()) {
            previousSave = false;
        }
        return previousSave;
    }

    // CRUD
    public void addQuestion(Question question) {
        questions.add(question);
        allTopics.addAll(question.getTopics());
    }

    public List<Question> getFilteredQuestions(String topic) {
        List<Question> filteredQuestions = new ArrayList<>();
        for (Question q : questions) {
            if (q.getTopics().contains(topic)) {
                filteredQuestions.add(q);
            }
        }
        return filteredQuestions;
    }

    public void updateStatement(Question question, String newStatement) {
        question.setStatement(newStatement);
    }

    public void updateOption(Question question, int optionNumber, Option newOption) {
        question.getOptions().set(optionNumber - 1, newOption);
    }

    public void updateAuthor(Question question, String newAuthor) {
        question.setAuthor(newAuthor);
    }

    public void updateTopic(Question question, String oldTopic, String newTopic) {
        Set<String> topics = question.getTopics();
        for (String topic : topics) {
            if (topic.equals(oldTopic)) {
                topics.remove(oldTopic);
                topics.add(newTopic);
                break;
            }
        }
    }

    public void addAllTopics(String newTopic) {
        allTopics.add(newTopic);
    }

    public void removeAllTopics(String oldTopic) {
        allTopics.remove(oldTopic);
    }

    public void updateAllTopics(String oldTopic, String newTopic) {
        allTopics.add(newTopic);
        boolean oldTopicInUse = false;
        for (Question question : questions) {
            Set<String> questionTopics = question.getTopics();
            if (questionTopics.contains(oldTopic)) {
                oldTopicInUse = true;
                break;
            }
        }
        if (!oldTopicInUse) {
            // posibles excepciones?
            allTopics.remove(oldTopic);
        }
    }

    public void remove(Question question) throws Exception {
        questions.remove(question);
    }

    public void removeAllTopics(Set<String> topics) {
        for (String topic : topics) {
            boolean topicInUse = false;
            for (Question question : questions) {
                Set<String> questionTopics = question.getTopics();
                if (questionTopics.contains(topic)) {
                    topicInUse = true;
                    break;
                }
            }
            if (!topicInUse) {
                allTopics.remove(topic);
            }
        }
    }

    // BACKUP
    public void exportQuestions(String fileName) throws Exception {
        backupHandler.exportQuestions(fileName, questions);
    }

    public void importQuestions (String fileName) throws Exception {
        // Añadir comentario en el informe sobre los streams
        List<Question> importedQuestions = backupHandler.importQuestions(fileName);
        for (Question impq : importedQuestions) {
            boolean copy = false;
            for (Question question : questions) {
                if (question.getId().equals(impq.getId())) {
                    copy = true;
                    break;
                }
            }
            if (!copy) {
                addQuestion(impq);
            }
        }
    }

    // EXAMEN
    public int getMaxQuestions(String topic) {
        int count = 0;
        for (Question q : questions) {
            if (q.getTopics().contains(topic)) {
                count++;
            }
        }
        return count;
    }
    
    public Exam configureExam(String topic, int numQuestions) {
        List<Question> examQuestions = new ArrayList<>();
        if (!topic.equals(ALL_TOPICS)) {
            for (Question q : questions) {
                if (q.getTopics().contains(topic)) {
                    examQuestions.add(q);
                }
            } 
        } else {
            examQuestions.addAll(questions);
        }
        return new Exam(topic, numQuestions, examQuestions);
    }

    public boolean studyAnswer(Exam exam, Question q, Integer answer) {
        if (answer != null) {
            int choice = answer.intValue();
            Option selectedOption = q.getOptions().get(choice - 1);
            if (selectedOption.isCorrect()) {
                exam.addCorrectAnswer();
            } else {
                exam.addIncorrectAnswer();
            }

            FeedbackDTO feedback = new FeedbackDTO(true, selectedOption.isCorrect(), selectedOption.getRationale());
            return feedback;
        } else {
            exam.addUnanswered();

            FeedbackDTO feedback = new FeedbackDTO();
            return feedback;
        }
    }

    // CERRAR
    public void saveQuestions() throws Exception {
        repository.saveQuestions(questions);
    }

    // Getters y setters
    public List<Question> getQuestions() {
        return questions;
    }
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Set<String> getAllTopics() {
        return allTopics;
    }
    public void setAllTopics(Set<String> allTopics) {
        this.allTopics = allTopics;
    }
}
