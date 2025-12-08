package model;

import java.util.List;

public interface IRepository {
    void saveQuestions(List<Question> questions);
    List<Question> loadQuestions();
}
