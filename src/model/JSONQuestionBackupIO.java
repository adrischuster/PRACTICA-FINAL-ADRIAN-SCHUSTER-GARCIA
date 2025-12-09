package model;

import java.util.List;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.File;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class JSONQuestionBackupIO implements QuestionBackupIO {
    @Override
    public void exportQuestions(String fileName, List<Question> questions) throws Exception {
        File file = new File(System.getProperty("user.home"), fileName);
        Gson gson = new Gson();
        String json = gson.toJson(questions);
        Files.write(file.toPath(), json.getBytes(StandardCharsets.UTF_8));
    }
    
    @Override
    public List<Question> importQuestions(String fileName) throws Exception {
        File file = new File(System.getProperty("user.home"), fileName);
        String json = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Question>>(){}.getType();
        List<Question> importedQuestions = gson.fromJson(json, listType);
        return importedQuestions;
    }
}