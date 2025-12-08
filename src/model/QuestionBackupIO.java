package model;

import java.util.List;

public interface QuestionBackupIO {
    void exportQuestions(String fileName, List<Question> questions) throws Exception;
    List<Question> importQuestions(String fileName) throws Exception;
}
