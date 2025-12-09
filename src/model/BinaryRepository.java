package model;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class BinaryRepository implements IRepository {
    private final Path filePath = Paths.get(System.getProperty("user.home"), "questions.bin");

    @Override
    public void saveQuestions(List<Question> questions) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            oos.writeObject(questions);
            System.out.println("\nGuardado exitoso.");
        }
    }

    // Eliminar supress tras control de excepciones
    @SuppressWarnings("unchecked")
    @Override
    public List<Question> loadQuestions() throws Exception {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(filePath))) {
            List<Question> questions = (ArrayList<Question>) ois.readObject();
            return questions;
        }
    }
}
