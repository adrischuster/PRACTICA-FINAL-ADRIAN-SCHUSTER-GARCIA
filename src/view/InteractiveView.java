package view;

import com.coti.tools.Esdia;
import model.Question;
import java.util.ArrayList;
import java.util.List;
import model.Option;
import java.util.Set;
import java.util.HashSet;

public class InteractiveView extends BaseView {
    //Atributos
    
    //Métodos
    @Override
    public void showMessage(String msg) {
        System.out.println(msg);
    }

    @Override
    public void showErrorMessage(String msg) {
        System.err.println(msg);
    }

    // Escribir en el informe: motivo-> limpieza por pantalla

    // private?
    private int showChoiceMessage(int choiceSize) {
        int choice = Esdia.readInt("Seleccione una opción: ");
        while (choice < 0 || choice > choiceSize) {
            showErrorMessage("Opción no válida.");
            choice = Esdia.readInt("Seleccione una opción: ");
        }
        return choice;
    }

    private boolean showYesOrNo(String msg) {
        System.out.print(msg + " (s/n): ");
        char response = Character.toUpperCase(Esdia.readString("").charAt(0));
        while (response != 'S' && response != 'N') {
            showErrorMessage("Opción no válida. Introduzca 's' o 'n'.");
            System.out.print(msg + " (s/n): ");
            response = Character.toUpperCase(Esdia.readString("").charAt(0));
        }
        return response == 'S';
    }

    @Override
    public void init() {
        // Comentar en informe: 'entry'?
        int entry;
        do {
            System.out.println("\n        --- Menú  ---         ");
            System.out.println("1) Gestionar preguntas (CRUD)");
            System.out.println("2) Exportar/Importar preguntas");
            System.out.println("3) Modo Examen");
            /* Implementar gestión para QuestionCreator
            if () {
                System.out.println("4) Crear pregunta aleatoria");
                System.out.println("0) Guardar y salir");
                entry = showChoiceMessage(4);
            } else {
            ...    
            */
            System.out.println("0) Guardar y salir");
            entry = showChoiceMessage(3);

            switch (entry) {
                case 1:
                    menuCRUD();
                    break;
                case 2:
                    // Lógica para exportar/importar preguntas
                    break;
                case 3:
                    // Lógica para crear pregunta aleatoria
                    // author debe ser inmutable?
                    break;
                case 4:
                    // Lógica para modo examen
                    break;
                case 0:
                    end();
                    break;
                default:
                    showErrorMessage("Opción no válida.");
                    break;
            }
        } while (entry!=0);
        // Todos los while son excepciones a controlar?
    }

    // ¿Debería heredar?
    private void menuCRUD() {
        int entry;
        do {
            System.out.println("\n  --- Gestión preguntas ---      ");
            System.out.println("1) Crear pregunta nueva");
            System.out.println("2) Ver preguntas existentes");
            System.out.println("0) Volver");
            entry = showChoiceMessage(2);

            switch (entry) {
                case 1:
                    create();
                    break;
                case 2:
                    if (controller.getQuestions().size() == 0) {
                        showErrorMessage("\nNo hay preguntas disponibles.");
                    } else {
                        menuListar();
                    }
                    break;
                case 0:
                    break;
                default:
                    showErrorMessage("Opción no válida.");
                    break;
            }
        } while (entry!=0);
    }

    private void create() {
        // Lógica de creación por E/S
        String statement = Esdia.readString("\nIntroduzca el enunciado de la pregunta: ");
        List<Option> options = new ArrayList<>();
        boolean hasCorrectOption = false;
        for (int i = 0; i < 4; i++) {
            Option option;
            String text = Esdia.readString("Introduzca la opción " + (i + 1) + ": ");
            boolean correct = false;
            if (!hasCorrectOption) {
                correct = showYesOrNo("¿Opción correcta?");
                if (correct) {
                    hasCorrectOption = true;
                } else if (i == 3) {
                    showErrorMessage("Error: Opción marcada como correcta");
                    correct = true;
                }
            }
            if (showYesOrNo("¿Desea introducir un comentario justificativo?")) {
                String rationale = Esdia.readString("Comentario: ");
                option = new Option(text, rationale, correct);
            } else {
                option = new Option(text, correct);
            }
            options.add(option);
        }
        String author = Esdia.readString("Introduzca el autor de la pregunta: ");
        Set<String> topics = new HashSet<>();
        topics.add(Esdia.readString("Introduzca un tema asociado a la pregunta: ").toUpperCase());
        while (showYesOrNo("¿Añadir otro tema?")) {
            topics.add(Esdia.readString("Introduzca otro tema: ").toUpperCase());
        }

        // Paso del DTO al modelo
        Question newQuestionDTO = new Question(statement, options, author, topics);
        try {
            controller.addQuestion(newQuestionDTO);
            showMessage("Pregunta añadida correctamente.");            
        } catch (Exception e) {
            // Excepción correcta?
            showErrorMessage("Error al añadir la pregunta: " + e.getMessage());
        }
    }

    // ¿Debería heredar?
    private void menuListar() {
        int entry;
        do {
            System.out.println("\n   --- Ver preguntas ---     ");
            System.out.println("1) Listar por fecha de creación");
            System.out.println("2) Listar por tema");
            System.out.println("0) Volver");
            entry = showChoiceMessage(2);

            switch (entry) {
                case 1:
                    listByDate();
                    if (controller.getQuestions().size() == 0) {
                        return;
                    }
                    break;
                case 2:
                    listByTopic();
                    if (controller.getQuestions().size() == 0) {
                        return;
                    }
                    break;
                case 0:
                    break;
                default:
                    showErrorMessage("Opción no válida.");
                    break;
            }
        } while (entry!=0);
    }

    public void listByDate() {
        System.out.println("\nPreguntas disponibles (por fecha de creación): ");
        List<Question> questions = controller.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println((i + 1) + ") " + q.getStatement() + " (" + q.getCreationDate() + ")");
        }

        // Escribir en el informe el intento de posible separación de estos 2 métodos en otra parte del menú
        
        // Implementar while?
        if (showYesOrNo("¿Desea ver el detalle de alguna pregunta?")) {
            int index = showChoiceMessage(questions.size()) - 1;
            Question questionForDetail = questions.get(index);
            seeDetail(questionForDetail);
        }
    }

    public void listByTopic() {
        Set<String> allTopics = controller.getAllTopics();
        System.out.println("\nTemas disponibles: ");
        for (String topic : allTopics) {
            System.out.println("-> " + topic);
        }
        
        String selectedTopic;
        do {
            selectedTopic = Esdia.readString("Introduzca el tema por el que desea filtrar: ").toUpperCase();
            if (!allTopics.contains(selectedTopic)) {
            showErrorMessage("El tema introducido no existe.");
            }
        } while (!allTopics.contains(selectedTopic));

        System.out.println("\nPreguntas disponibles (" + selectedTopic + "):");
        List<Question> questions = controller.getQuestions();
        List<Question> filteredQuestions = new ArrayList<>();
        for (Question q : questions) {
            if (q.getTopics().contains(selectedTopic)) {
                filteredQuestions.add(q);
            }
        }
        for (int i = 0; i < filteredQuestions.size(); i++) {
            Question qfil = filteredQuestions.get(i);
            System.out.println((i + 1) + ") " + qfil.getStatement());
        }
        if (showYesOrNo("¿Desea ver el detalle de alguna pregunta?")) {
            int index = showChoiceMessage(filteredQuestions.size()) - 1;
            Question questionForDetail = filteredQuestions.get(index);
            seeDetail(questionForDetail);
        }
    }
    
    public void seeDetail(Question q) {
        System.out.println("\n-> " + q.getStatement());
        List<Option> options = q.getOptions();
        for (int i = 0; i < options.size(); i++) {
            Option o = options.get(i);
            System.out.println("\n" + (i + 1) + ") " + o.getText() + (o.isCorrect() ? " (CORRECTA)" : ""));
            if (o.getRationale() != null) {
                System.out.println("(" + o.getRationale() + ")");
            }
        }
        System.out.println("\nAutor: " + q.getAuthor());
        System.out.println("Temas: " + String.join(", ", q.getTopics()));

        // Menú para modificar o eliminar
        int entry;
        do {
            System.out.println("\n1) Modificar pregunta");
            System.out.println("2) Eliminar pregunta");
            System.out.println("0) Volver");
            entry = showChoiceMessage(2);

            switch (entry) {
                case 1:
                    modify(q);
                    break;
                case 2:
                    if (remove(q)) {
                        return;
                    }
                    break;
                case 0:
                    break;
                default:
                    showErrorMessage("Opción no válida.");
                    break;
            }
        } while (entry!=0);
    }

    public void modify(Question q) {
        int entry;
        do {
            System.out.println("\nModificar:");
            System.out.println("1) Enunciado");
            System.out.println("2) Opciones");
            System.out.println("3) Autor");
            System.out.println("4) Temas");
            System.out.println("0) Volver");
            entry = showChoiceMessage(4);

            switch (entry) {
                case 1:
                    String newStatement = Esdia.readString("\nNuevo enunciado: ");
                    try {
                        controller.updateStatement(q, newStatement);
                        showMessage("\nEnunciado modificado correctamente.");
                    } catch (Exception e) {
                        showErrorMessage("\nError al modificar la pregunta: " + e.getMessage());
                    } 
                    break;
                case 2:
                    System.out.println("\nModificar opciones:");
                    System.out.println("1) Opción 1");
                    System.out.println("2) Opción 2");
                    System.out.println("3) Opción 3");
                    System.out.println("4) Opción 4");
                    System.out.println("0) Volver");
                    int optionNumber = showChoiceMessage(4);
                    
                    if (optionNumber == 0) {
                        break;
                    }

                    Option newOption;
                    String newText = Esdia.readString("\nNueva opción: ");

                    boolean wasCorrect = q.getOptions().get(optionNumber - 1).isCorrect();
                    boolean correct = showYesOrNo("¿Es la correcta?");
                    if (correct != wasCorrect) {
                        // Asegurarse de que hay una única opción correcta
                    }

                    if (showYesOrNo("¿Desea introducir un comentario justificativo?")) {
                        String rationale = Esdia.readString("Comentario: ");
                        newOption = new Option(newText, rationale, correct);
                        continue;
                    } else {
                        newOption = new Option(newText, correct);
                    }

                    try {
                        controller.updateOption(q, optionNumber, newOption);
                        showMessage("\nOpción modificada correctamente.");
                    } catch (Exception e) {
                        showErrorMessage("\nError al modificar la opción: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        String newAuthor = Esdia.readString("\nNuevo autor: ");
                        controller.updateAuthor(q, newAuthor);
                        showMessage("\nAutor modificado correctamente.");
                    } catch (Exception e) {
                        showErrorMessage("\nError al modificar el autor: " + e.getMessage());
                    }
                    break;
                case 4:
                    for (String topic : q.getTopics()) {
                        if (showYesOrNo("\n-> ¿Modificar tema '" + topic +"'?")) {
                            String newTopic = Esdia.readString("\nIntroduzca el nuevo tema: ").toUpperCase();
                            try {
                                controller.updateTopic(q, topic, newTopic);
                                controller.updateAllTopics(topic, newTopic);
                                showMessage("\nTema modificado correctamente.");
                            } catch (Exception e) {
                                showErrorMessage("\nError al modificar el tema: " + e.getMessage());
                            }
                        }
                    }
                    break;
                case 0:
                    return;
                default:
                    showErrorMessage("Opción no válida.");
                    break;
            }
        } while (entry!=0);
    }

    public boolean remove(Question q) {
        if (showYesOrNo("¿Está seguro de que desea eliminar esta pregunta?")) {
            try {
                controller.remove(q);
                controller.removeAllTopics(q.getTopics());
                showMessage("\nPregunta eliminada correctamente.");
                return true;
            } catch (Exception e) {
                showErrorMessage("\nError al eliminar la pregunta: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public void end() {
        controller.save();
        showMessage("Aplicación finalizada.");
    }

}
