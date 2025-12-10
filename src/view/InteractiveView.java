package view;

import com.coti.tools.Esdia;
import model.Question;
import java.util.ArrayList;
import java.util.List;
import model.Option;
import java.util.Set;
import java.util.HashSet;
import model.FeedbackDTO;
import model.Exam;

public class InteractiveView extends BaseView {
    // Atributos
    private static final String ALL_TOPICS = "TODOS";

    //Métodos
    // FUNCIONES AUXILIARES DE MENSAJE ÚNICO
    @Override
    public void showMessage(String msg) {
        System.out.println(msg);
    }

    // ¿En cuáles hago @Override y en cuales pongo private??
    @Override
    public void showErrorMessage(String msg) {
        System.err.println(msg);
    }
    
    // Escribir en el informe: motivo-> limpieza por pantalla

    @Override
    public void showQuestion(Question q) {
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
    }

    @Override
    public void askQuestion(Question q) {
        System.out.println("\n-> " + q.getStatement());
        List<Option> options = q.getOptions();
        for (int i = 0; i < options.size(); i++) {
            Option o = options.get(i);
            System.out.println("\n" + (i + 1) + ") " + o.getText());
        }
    }

    @Override
    // poner excepcion de letra
    public int showChoiceMessage(int choiceSize) {
        int choice = Esdia.readInt("Seleccione una opción: ");
        while (choice < 1 || choice > choiceSize) {
            showErrorMessage("Opción no válida.");
            choice = Esdia.readInt("Seleccione una opción: ");
        }
        return choice;
    }

    @Override
    public boolean showYesOrNo(String msg) {
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
    public void askInput(String msg) {
        System.out.print(msg);
        Esdia.readString("");
    }

    // INICIAR
    @Override
    public void init() {
        int entry;
        do {
            System.out.println("\n        --- Menú  ---         ");
            System.out.println("1) Gestionar preguntas");
            System.out.println("2) Exportar/Importar preguntas");
            System.out.println("3) Modo Examen");
            // Cambiar a 0?
            System.out.println("4) Guardar y salir");
            System.out.flush();
            entry = showChoiceMessage(4);

            switch (entry) {
                case 1:
                    menuCRUD();
                    break;
                case 2:
                    menuBackup();
                    break;
                case 3:
                    if (controller.hasQuestions()) {
                        controller.examMode();
                    } else {
                        showErrorMessage("\nNo hay preguntas disponibles.");
                    }
                    break;
                case 4:
                    end();
                    break;
                default:
                    showErrorMessage("Opción no válida.");
                    break;
            }
        } while (entry!=4);
        // Todos los while son excepciones a controlar?
    }

    // MENÚ CRUD
    private void menuCRUD() {
        int entry;
        do {
            System.out.println("\n  --- Gestión preguntas ---    ");
            System.out.println("1) Crear pregunta nueva");
            System.out.println("2) Ver preguntas existentes");
            System.out.println("3) Volver");
            System.out.flush();
            entry = showChoiceMessage(3);

            switch (entry) {
                case 1:
                    create();
                    break;
                case 2:
                    if (controller.hasQuestions()) {
                        menuListar();
                    } else {
                        showErrorMessage("\nNo hay preguntas disponibles.");
                    }
                    break;
                case 3:
                    break;
                default:
                    showErrorMessage("Opción no válida.");
                    break;
            }
        } while (entry!=3);
    }

    private void create() {
        // Lógica de creación por E/S
        // Crear factory method para Question?
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

        // Paso al modelo por DTO -> para ser DTO debe ser si o si una copia??
        Question newQuestionDTO = new Question(statement, options, author, topics);
        // Añadir excepciones por E/S de este tipo, no?
        try {
            controller.addQuestion(newQuestionDTO);
            showMessage("Pregunta añadida correctamente.");            
        } catch (Exception e) {
            // Excepción correcta?
            showErrorMessage("Error al añadir la pregunta: " + e.getMessage());
        }
    }

    private void menuListar() {
        int entry;
        do {
            System.out.println("\n   --- Ver preguntas ---     ");
            System.out.println("1) Listar por fecha de creación");
            System.out.println("2) Listar por tema");
            System.out.println("3) Volver");
            System.out.flush();
            entry = showChoiceMessage(3);

            switch (entry) {
                case 1:
                    listByDate();
                    break;
                case 2:
                    listByTopic();
                    break;
                case 3:
                    break;
                default:
                    showErrorMessage("Opción no válida.");
                    break;
            }
            
            if (!controller.hasQuestions()) {
                return;
            }
        } while (entry!=3);
    }

    private void listByDate() {
        System.out.println("\nPreguntas disponibles (por fecha de creación): ");
        List<Question> questions = controller.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println((i + 1) + ") " + q.getStatement() + " (" + q.getCreationDate() + ")");
        }

        controller.showDetail(questions);
    }

    private void listByTopic() {
        String selectedTopic = selectTopic();

        System.out.println("\nPreguntas disponibles (" + selectedTopic + "):");
        List<Question> filteredQuestions = controller.getFilteredQuestions(selectedTopic);
        int i = 1;
        for (Question qfil : filteredQuestions) {
            // comprobar
            System.out.println((i++) + ") " + qfil.getStatement());
        }

        controller.showDetail(filteredQuestions);
    }
    
    @Override
    public void showDetail(Question q) {
        int entry;
        do {
            showQuestion(q);
            System.out.println("\n1) Modificar pregunta");
            System.out.println("2) Eliminar pregunta");
            System.out.println("3) Volver");
            entry = showChoiceMessage(3);
            switch (entry) {
                case 1:
                    modify(q);
                    break;
                case 2:
                    if (remove(q)) {
                        return;
                    }
                    break;
                case 3:
                    break;
                default:
                    showErrorMessage("Opción no válida.");
                    break;
            }
        } while (entry!=3);
    }

    private void modify(Question q) {
        int entry;
        do {
            System.out.println("\nModificar:");
            System.out.println("1) Enunciado");
            System.out.println("2) Opciones");
            System.out.println("3) Autor");
            System.out.println("4) Temas");
            System.out.println("5) Volver");
            entry = showChoiceMessage(5);

            // Se podría aquí también aplicar un factory method o algo parecido?
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
                    System.out.println("5) Volver");
                    int optionNumber = showChoiceMessage(5);
                    
                    if (optionNumber == 5) {
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
                case 5:
                    break;
                default:
                    showErrorMessage("Opción no válida.");
                    break;
            }
        } while (entry!=5);
    }

    private boolean remove(Question q) {
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

    // MENÚ BACKUP
    private void menuBackup() {
        int entry;
        do {
            System.out.println("\n --- Exportar/Importar preguntas ---     ");
            System.out.println("1) Exportar preguntas");
            System.out.println("2) Importar preguntas");
            System.out.println("3) Volver");
            entry = showChoiceMessage(3);

            switch (entry) {
                case 1:
                    exportQuestions();
                    break;
                case 2:
                    importQuestions();
                    break;
                case 3:
                    break;
                default:
                    showErrorMessage("Opción no válida.");
                    break;
            }
        } while (entry!=3);
    }

    private void exportQuestions() {
        try {
            String fileName = Esdia.readString("\nIntroduce el nombre del archivo en el 'home' de tu usuario (con extensión), donde se exportarán las preguntas: \n");
            controller.exportQuestions(fileName);
            showMessage("\nPreguntas exportadas correctamente.");
        } catch (Exception e) {
            showErrorMessage("\nError al exportar las preguntas: " + e.getMessage());
        }
    }

    private void importQuestions() {
        try {
            String fileName = Esdia.readString("\nIntroduce el nombre del archivo en el 'home' de tu usuario (con extensión), desde donde se importarán las preguntas: \n");
            controller.importQuestions(fileName);
            showMessage("\nPreguntas importadas correctamente.");
        } catch (Exception e) {
            showErrorMessage("\nError al importar las preguntas: " + e.getMessage());
        }
    }

    // FUNCIONES AUXILIARES
    @Override
    public String selectTopic() {
        Set<String> allTopics = controller.getAllTopics();
        System.out.println("\nTemas disponibles: ");
        for (String topic : allTopics) {
            System.out.println("-> " + topic);
        }
        
        String selectedTopic;
        boolean validTopic;

        do {
            selectedTopic = Esdia.readString("Introduzca el tema por el que desea filtrar: ").toUpperCase();
            validTopic = allTopics.contains(selectedTopic);
            if (!validTopic) {
            showErrorMessage("El tema introducido no existe.");
            }
        } while (!validTopic);
        return selectedTopic;
    }

    @Override
    public String selectExamTopic() {
        controller.addAllTopics(ALL_TOPICS);
        String selectedTopic = selectTopic();

        // comprobar que no escriben TODOS como tema con ALL_TOPICS
        // excepción si no existe "TODOS"?
        controller.removeAllTopics(ALL_TOPICS);
        return selectedTopic;
    }

    @Override
    public int askNumQuestions(int maxQuestions) {
        int numQuestions;
        do {
            numQuestions = Esdia.readInt("Introduzca el número de preguntas: (1 - " + maxQuestions + "): ");
            if (numQuestions <= 0 || numQuestions > maxQuestions) {
                showErrorMessage("Número de preguntas no válido.");
            }
        } while (numQuestions <= 0 || numQuestions > maxQuestions);
        return numQuestions;
    }

    @Override
    public Integer answerQuestion() {
        if (showYesOrNo("¿Responder?")) {
            return showChoiceMessage(4);
        } else {
            return null;
        }
    }

    @Override
    public void feedbackAnswer(FeedbackDTO feedback) {
        if (feedback.isAnswered()) {
            if (feedback.isCorrect()) {
                showMessage("\nRespuesta correcta!");
            } else {
                showMessage("\nRespuesta incorrecta.");
            }
            if (feedback.getRationale() != null && !feedback.getRationale().isEmpty()) {
                showMessage("(" + feedback.getRationale() + ")");
            }
        } else {
            showMessage("\nPregunta no respondida.");
        }
    }

    @Override
    public void showResults(Exam exam) {
        showMessage("\n  --- RESULTADOS ---");
        showMessage("Nota: " + exam.getResult());
        showMessage("Tema: " + exam.getTopic());
        showMessage("Número de preguntas: " + exam.getNumQuestions());
        showMessage("Respuestas correctas: " + exam.getCorrectAnswers());
        showMessage("Respuestas incorrectas: " + exam.getIncorrectAnswers());
        showMessage("Preguntas sin responder: " + exam.getUnanswered());
    }

    // CERRAR
    @Override
    public void end() {
        try {
            controller.saveQuestions();
        } catch (Exception e) {
            showErrorMessage("Error al guardar las preguntas: " + e.getMessage());
        }
        showMessage("Aplicación finalizada.");
    }

}
