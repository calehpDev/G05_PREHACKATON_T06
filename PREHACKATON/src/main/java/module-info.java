module vallegrande.edu.pe.prehackaton {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens vallegrande.edu.pe.prehackaton to javafx.fxml;
    opens vallegrande.edu.pe.prehackaton.Model to javafx.base, javafx.fxml;

    exports vallegrande.edu.pe.prehackaton;
    exports vallegrande.edu.pe.prehackaton.Controller;
    exports vallegrande.edu.pe.prehackaton.Model;
    exports vallegrande.edu.pe.prehackaton.View;
}