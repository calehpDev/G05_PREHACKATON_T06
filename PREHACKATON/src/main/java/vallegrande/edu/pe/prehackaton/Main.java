package vallegrande.edu.pe.prehackaton;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vallegrande.edu.pe.prehackaton.Controller.MainController;
import vallegrande.edu.pe.prehackaton.View.MainView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Inicializa directamente la vista principal
            MainView mainView = new MainView();
            new MainController(mainView, primaryStage, "Usuario Demo");

            Scene scene = new Scene(mainView, 1280, 720);
            primaryStage.setTitle("Sistema de Gestión de Eventos e Inscripciones");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}