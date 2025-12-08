package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class Model {
    // Atributos
    private IRepository repository;
    private QuestionBackupIO backupHandler;
    private List<QuestionCreator> questionCreators;
    // Comentar en informe esta decisión
    private List<Question> questions = new ArrayList<>();
    private Set<String> allTopics = new HashSet<>();

    // Constructores
    public Model(IRepository repository, QuestionBackupIO backupHandler, List<QuestionCreator> questionCreators) {
        this.repository = repository;
        this.backupHandler = backupHandler;
        this.questionCreators = questionCreators;
    }

    // Métodos
    public boolean load() {
        List<Question> loadedQuestions = repository.load();
        
        setQuestions(loadedQuestions);
        Set<String> loadedAllTopics = new HashSet<>();
        for (Question question : loadedQuestions) {
            loadedAllTopics.addAll(question.getTopics());
        }
        setAllTopics(loadedAllTopics);

        boolean previousSave = true;
        if (loadedQuestions.isEmpty()) {
            previousSave = false;
        }
        return previousSave;
    }

    public void addQuestion(Question question) throws Exception {
        questions.add(question);
        allTopics.addAll(question.getTopics());
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
            allTopics.remove(oldTopic);
        }
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

    public void remove(Question question) throws Exception {
        questions.remove(question);
    }

    public void save() {
        repository.save(questions);
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
