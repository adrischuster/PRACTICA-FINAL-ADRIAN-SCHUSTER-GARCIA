package model;

import java.util.List;

public interface QuestionBackupIO {
    void exportQuestions(List<Question> questions);
    List<Question> importQuestions();
}
