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
    public void saveQuestions(List<Question> questions) {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            oos.writeObject(questions);
            System.out.println("\nGuardado exitoso.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Eliminar supress tras control de excepciones
    @SuppressWarnings("unchecked")
    @Override
    public List<Question> loadQuestions() {
        if (Files.exists(filePath)) {
            try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(filePath))) {
                return (ArrayList<Question>) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<Question>();            
            }
            
        } else {
            return new ArrayList<Question>();
        }
    }
}
