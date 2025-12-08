package model;

import java.util.List;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.io.File;
import java.io.IOException;
import com.google.gson.Gson;

import com.coti.tools.Esdia;

public class JSONQuestionBackupIO implements QuestionBackupIO {
    @Override
    public void exportQuestions(List<Question> questions) {
        String fileName = Esdia.readString("\nIntroduce el nombre del archivo en el 'home' de tu usuario (con extensión), donde se exportarán las preguntas: \n");
        File file = new File(System.getProperty("user.home"), fileName);

        try {
            Gson gson = new Gson();
            String json = gson.toJson(questions);
            Files.write(file.toPath(), json.getBytes(StandardCharsets.UTF_8));
            System.out.println("\nPreguntas exportadas correctamente a " + file.getAbsolutePath());
        } catch (IOException ex) {
            System.err.println("\nError:" + ex.getMessage());
        }
    }

    @Override
    public List<Question> importQuestions() {
        
    }
}