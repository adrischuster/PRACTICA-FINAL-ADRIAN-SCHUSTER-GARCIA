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

    @Override
    public void init() {
        // Comentar en informe: 'entry'
        int entry;
        do {
            System.out.println("1) Gestionar preguntas (CRUD)");
            System.out.println("2) Exportar/Importar preguntas");
            System.out.println("3) Modo Examen");
            /* Implementar gestión para QuestionCreator
            if () {
                System.out.println("4) Crear pregunta aleatoria");
            } */
            System.out.println("0) Guardar y salir");
            entry = Esdia.readInt("Seleccione una opción: ", 0, 3);

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
                case 5:
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
            System.out.println("1) Crear pregunta nueva");
            System.out.println("2) Ver preguntas existentes");
            System.out.println("0) Volver");
            entry = Esdia.readInt("Seleccione una opción: ", 0, 2);

            switch (entry) {
                case 1:
                    create();
                    break;
                case 2:
                    menuListar();
                    break;
                case 3:
                    break;
                default:
                    showErrorMessage("Opción no válida.");
                    break;
            }
        } while (entry!=0);
    }

    private void create() {
        // Lógica de creación por E/S
        String statement = Esdia.readString("Introduzca el enunciado de la pregunta: ");
        List<Option> options = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Option option;
            String text = Esdia.readString("Introduzca la opción " + (i + 1) + ": ");
            boolean correct = Esdia.siOno("¿Opción correcta? (s/n): ");
            if (Esdia.siOno("¿Desea introducir un comentario justificativo? (s/n): ")) {
                String rationale = Esdia.readString("Comentario: ");
                option = new Option(text, rationale, correct);
                continue;
            } else {
                option = new Option(text, correct);
            }
            options.add(option);
        }
        String author = Esdia.readString("Introduzca el autor de la pregunta: ");
        Set<String> topics = new HashSet<>();
        topics.add(Esdia.readString("Introduzca un tema asociado a la pregunta: ").toUpperCase());
        do {
            if (Esdia.siOno("¿Añadir otro tema? (s/n): ")) {
                topics.add(Esdia.readString("Introduzca otro tema: ").toUpperCase());
            } else {
                break;
            }
        } while (true);

        // Paso del DTO al modelo
        Question newQuestionDTO = new Question(statement, options, author, topics);
        try {
            controller.addQuestion(newQuestionDTO);
            controller.addAllTopics(topics);
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
            System.out.println("1) Listar por fecha de creación");
            System.out.println("2) Listar por tema");
            System.out.println("0) Volver");
            entry = Esdia.readInt("Seleccione una opción: ", 0, 2);

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
        } while (entry!=0);
    }

    public void listByDate() {
        List<Question> questions = controller.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println((i + 1) + ") " + q.getStatement() + " (" + q.getCreationDate() + ")");
        }

        // Escribir en el informe el intento de posible separación de estos 2 métodos en otra parte del menú
        
        // Implementar while?
        if (Esdia.siOno("Desea ver el detalle de una pregunta? (s/n): ")) {
            int index = Esdia.readInt("Introduzca el número de la pregunta: ", 1, questions.size()) - 1;
            Question questionForDetail = questions.get(index);
            seeDetail(questionForDetail);
        }
    }

    public void listByTopic() {
        Set<String> allTopics = controller.getAllTopics();
        // Escribir en el informe el descuido al leer esta parte (igual era innecesario un listado de temas, o igual no)
        System.out.println("Listado de temas disponibles: ");
        for (String topic : allTopics) {
            System.out.println("-> " + topic);
        }
        
        String selectedTopic;
        do {
            selectedTopic = Esdia.readString("Introduzca el tema por el que desea filtrar: ").toUpperCase();
            if (allTopics.contains(selectedTopic)) {
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
                if (Esdia.siOno("Desea ver el detalle de una pregunta? (s/n): ")) {
                    int index = Esdia.readInt("Introduzca el número de la pregunta: ", 1, filteredQuestions.size()) - 1;
                    Question questionForDetail = filteredQuestions.get(index);
                    seeDetail(questionForDetail);
                }
            } else {
                showErrorMessage("El tema introducido no existe.");
            }
        } while (!allTopics.contains(selectedTopic));
    }
    
    public void seeDetail(Question q) {
        System.out.println("->" + q.getStatement());
        List<Option> options = q.getOptions();
        for (int i = 0; i < options.size(); i++) {
            Option o = options.get(i);
            System.out.println((i + 1) + ") " + o.getText() + (o.isCorrect() ? " (Correcta)" : ""));
            if (o.getRationale() != null) {
                System.out.println("(" + o.getRationale() + ")");
            }
        }
        System.out.println("Autor: " + q.getAuthor());
        System.out.println("Temas: " + String.join(", ", q.getTopics()));

        // Menú para modificar o eliminar
        int entry;
        do {
            System.out.println("1) Modificar pregunta");
            System.out.println("2) Eliminar pregunta");
            System.out.println("0) Volver");
            entry = Esdia.readInt("Seleccione una opción: ", 0, 2);

            switch (entry) {
                case 1:
                    modify(q);
                    break;
                case 2:
                    remove(q);
                    break;
                case 3:
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
            System.out.println("Modificar:");
            System.out.println("1) Enunciado");
            System.out.println("2) Opciones");
            System.out.println("3) Autor");
            System.out.println("4) Temas");
            System.out.println("0) Volver");
            entry = Esdia.readInt("Seleccione una opción: ", 0, 4);

            switch (entry) {
                case 1:
                    String newStatement = Esdia.readString("Nuevo enunciado: ");
                    try {
                        controller.updateStatement(q, newStatement);
                        showMessage("Enunciado modificado correctamente.");
                    } catch (Exception e) {
                        showErrorMessage("Error al modificar la pregunta: " + e.getMessage());
                    } 
                    break;
                case 2:
                    int optionNumber;
                    do {
                        System.out.println("Modificar:");
                        System.out.println("1) Opción 1");
                        System.out.println("2) Opción 2");
                        System.out.println("3) Opción 3");
                        System.out.println("4) Opción 4");
                        System.out.println("0) Volver");
                        optionNumber = Esdia.readInt("Seleccione una opción: ", 0, 4);

                        if (optionNumber < 0 || optionNumber > 4) {
                            showErrorMessage("Opción no válida.");
                            
                        }
                    } while (optionNumber!=0);

                    Option newOption;
                    String newText = Esdia.readString("Nueva opción: ");
                    boolean correct = Esdia.siOno("¿Es correcta? (s/n): ");
                    if (Esdia.siOno("¿Desea introducir un comentario justificativo? (s/n): ")) {
                        String rationale = Esdia.readString("Comentario: ");
                        newOption = new Option(newText, rationale, correct);
                        continue;
                    } else {
                        newOption = new Option(newText, correct);
                    }

                    try {
                        controller.updateOption(q, optionNumber, newOption);
                        showMessage("Enunciado modificado correctamente.");
                    } catch (Exception e) {
                        showErrorMessage("Error al modificar la opción: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        String newAuthor = Esdia.readString("Nuevo autor: ");
                        controller.updateAuthor(q, newAuthor);
                        showMessage("Autor modificado correctamente.");
                    } catch (Exception e) {
                        showErrorMessage("Error al modificar el autor: " + e.getMessage());
                    }
                    break;
                case 4:
                    for (String topic : q.getTopics()) {
                        if (Esdia.siOno("¿Modificar tema '" + topic +"'? (s/n): ")) {
                            String newTopic = Esdia.readString("Introduzca el nuevo tema: ").toUpperCase();
                            try {
                                controller.updateTopic(q, topic, newTopic);
                                controller.updateAllTopics(topic, newTopic);
                            } catch (Exception e) {
                                showErrorMessage("Error al modificar el tema: " + e.getMessage());
                            }
                        }
                        break;
                    }
                case 5:
                    return;
                default:
                    showErrorMessage("Opción no válida.");
                    break;
            }
        } while (entry!=0);
    }

    public void remove(Question q) {
        if (Esdia.siOno("¿Está seguro de que desea eliminar esta pregunta? (s/n): ")) {
            try {
                controller.remove(q);
                controller.removeAllTopics(q.getTopics());
                showMessage("Pregunta eliminada correctamente.");
            } catch (Exception e) {
                showErrorMessage("Error al eliminar la pregunta: " + e.getMessage());
            }
        }
    }

    @Override
    public void end() {
        // Lógica de guardado

        showMessage("Aplicación finalizada.");
    }

}
