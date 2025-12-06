package view;

import com.coti.tools.Esdia;

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

    // Gestionar MENÚ SIN QUESTIONCREATOR
    @Override
    public void init() {
        int option;
        do {
            System.out.println("1) Gestionar preguntas (CRUD)");
            System.out.println("2) Exportar/Importar preguntas");
            System.out.println("3) Crear pregunta aleatoria");
            System.out.println("4) Modo Examen");
            System.out.println("5) Guardar y salir");
            option = Esdia.readInt("Seleccione una opción: ", 1, 5);

            switch (option) {
                case 1:
                    menuCRUD();
                    break;
                case 2:
                    // Lógica para exportar/importar preguntas
                    break;
                case 3:
                    // Lógica para crear pregunta aleatoria
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
        } while (option!=5);
    }

    @Override
    public void menuCRUD() {
        int option;
        do {
            System.out.println("1) Crear pregunta nueva");
            System.out.println("2) Ver preguntas existentes");
            System.out.println("3) Volver");
            option = Esdia.readInt("Seleccione una opción: ", 1, 3);

            switch (option) {
                case 1:
                    // Lógica para crear pregunta nueva
                    break;
                case 2:
                    menuListar();
                    break;
                case 3:
                    // Volver al menú principal
                    break;
                default:
                    showErrorMessage("Opción no válida.");
                    break;
            }
        } while (option!=3);
    }

    // Gestionar VER DETALLE
    @Override
    public void menuListar() {
        int option;
        do {
            System.out.println("1) Listar por fecha de creación");
            System.out.println("2) Listar por tema");
            System.out.println("3) Volver");
            option = Esdia.readInt("Seleccione una opción: ", 1, 3);

            switch (option) {
                case 1:
                    // Lógica para listar por fecha
                    break;
                case 2:
                    // Lógica para listar preguntas por tema
                    break;
                case 3:
                    // Volver al menú CRUD
                    break;
                default:
                    showErrorMessage("Opción no válida.");
                    break;
            }
        } while (option!=3);
    }

    @Override
    public void end() {
        // Lógica de guardado

        showMessage("Aplicación finalizada.");
    }

}
