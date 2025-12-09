package model;

import java.util.List;

public interface IRepository {
    void saveQuestions(List<Question> questions) throws Exception;
    List<Question> loadQuestions() throws Exception;
}
