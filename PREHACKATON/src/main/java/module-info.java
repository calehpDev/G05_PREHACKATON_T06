module vallegrande.edu.pe.prehackaton {
    requires javafx.controls;
    requires javafx.fxml;


    opens vallegrande.edu.pe.prehackaton to javafx.fxml;
    exports vallegrande.edu.pe.prehackaton;
}