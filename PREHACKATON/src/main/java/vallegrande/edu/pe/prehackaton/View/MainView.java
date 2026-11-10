package vallegrande.edu.pe.prehackaton.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import vallegrande.edu.pe.prehackaton.Model.Inscripcion;
import vallegrande.edu.pe.prehackaton.Model.Participante;

import java.sql.Timestamp;
import java.util.List;

public class MainView extends BorderPane {

    // Botones de navegación lateral
    private final Button btnInicio = new Button("Inicio");
    private final Button btnEventos = new Button("Eventos");
    private final Button btnParticipantes = new Button("Participantes");
    private final Button btnInscripciones = new Button("Inscripciones");
    private final Button btnLogout = new Button("Cerrar Sesión");

    // Paneles contenedores
    private final StackPane contenidoCentral = new StackPane();
    private final VBox panelInicio = new VBox();
    private final VBox panelEventos = new VBox();
    private final VBox panelParticipantes = new VBox();
    private final VBox panelInscripciones = new VBox();

    // Controles del Módulo Inscripciones
    private final TextField txtInscripcionIdEvento = new TextField();
    private final TextField txtInscripcionIdParticipante = new TextField();
    private final ComboBox<String> cmbInscripcionEstado = new ComboBox<>();

    private final Button btnInscripcionRegistrar = new Button("Registrar");
    private final Button btnInscripcionActualizar = new Button("Actualizar");
    private final Button btnInscripcionEliminar = new Button("Eliminar");
    private final TextField txtBuscarInscripciones = new TextField();

    private final TableView<Inscripcion> tablaInscripciones = new TableView<>();
    private final ObservableList<Inscripcion> listaInscripciones = FXCollections.observableArrayList();

    // --- CONTROLES DEL MÓDULO PARTICIPANTES ---
    private final TextField txtParticipanteNombres = new TextField();
    private final TextField txtParticipanteApellidos = new TextField();
    private final TextField txtParticipanteDni = new TextField();
    private final TextField txtParticipanteCorreo = new TextField();
    private final TextField txtParticipanteTelefono = new TextField();
    private final TextField txtParticipanteInstitucion = new TextField();

    private final Button btnParticipanteRegistrar = new Button("Registrar");
    private final Button btnParticipanteActualizar = new Button("Actualizar");
    private final Button btnParticipanteEliminar = new Button("Eliminar");
    private final TextField txtBuscarParticipantes = new TextField();

    private final TableView<Participante> tablaParticipantes = new TableView<>();
    private final ObservableList<Participante> listaParticipantes = FXCollections.observableArrayList();

    public MainView() {
        initUI();
    }

    private void initUI() {
        // --- MENÚ LATERAL (SIDEBAR) ---
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #2c3e50;");
        sidebar.setPrefWidth(220);

        Label lblTitulo = new Label("SISTEMA EVENTOS");
        lblTitulo.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;");

        String btnStyle = "-fx-background-color: transparent; -fx-text-fill: white; -fx-alignment: CENTER-LEFT; -fx-font-size: 14px;";
        btnInicio.setStyle(btnStyle);
        btnEventos.setStyle(btnStyle);
        btnParticipantes.setStyle(btnStyle);
        btnInscripciones.setStyle(btnStyle);
        btnLogout.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");

        btnInicio.setMaxWidth(Double.MAX_VALUE);
        btnEventos.setMaxWidth(Double.MAX_VALUE);
        btnParticipantes.setMaxWidth(Double.MAX_VALUE);
        btnInscripciones.setMaxWidth(Double.MAX_VALUE);
        btnLogout.setMaxWidth(Double.MAX_VALUE);

        sidebar.getChildren().addAll(lblTitulo, new Separator(), btnInicio, btnEventos, btnParticipantes, btnInscripciones, new Region(), btnLogout);
        VBox.setVgrow(sidebar.getChildren().get(6), Priority.ALWAYS);

        this.setLeft(sidebar);

        // --- CONSTRUCCIÓN DE VISTAS ---
        construirPanelInicio();
        construirPanelEventos();
        construirPanelParticipantes();
        construirPanelInscripciones();

        contenidoCentral.setPadding(new Insets(20));
        contenidoCentral.getChildren().addAll(panelInicio, panelEventos, panelParticipantes, panelInscripciones);
        this.setCenter(contenidoCentral);

        mostrarInicio();
    }

    private void construirPanelInicio() {
        panelInicio.setAlignment(Pos.CENTER);
        Label lblBienvenida = new Label("Bienvenido al Sistema de Gestión de Eventos e Inscripciones");
        lblBienvenida.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        panelInicio.getChildren().add(lblBienvenida);
    }

    private void construirPanelEventos() {
        panelEventos.setAlignment(Pos.CENTER);
        panelEventos.getChildren().add(new Label("Módulo de Eventos"));
    }

    @SuppressWarnings("unchecked")
    private void construirPanelParticipantes() {
        panelParticipantes.setSpacing(15);

        Label lblTitulo = new Label("Gestión de Participantes");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Formulario de Entradas
        GridPane gridForm = new GridPane();
        gridForm.setHgap(10);
        gridForm.setVgap(10);
        gridForm.setPadding(new Insets(10));
        gridForm.setStyle("-fx-background-color: #ecf0f1; -fx-background-radius: 5px;");

        txtParticipanteNombres.setPromptText("Nombres");
        txtParticipanteApellidos.setPromptText("Apellidos");
        txtParticipanteDni.setPromptText("DNI");
        txtParticipanteCorreo.setPromptText("Correo Electrónico");
        txtParticipanteTelefono.setPromptText("Teléfono");
        txtParticipanteInstitucion.setPromptText("Institución");

        gridForm.add(new Label("Nombres:"), 0, 0);
        gridForm.add(txtParticipanteNombres, 1, 0);
        gridForm.add(new Label("Apellidos:"), 2, 0);
        gridForm.add(txtParticipanteApellidos, 3, 0);

        gridForm.add(new Label("DNI:"), 0, 1);
        gridForm.add(txtParticipanteDni, 1, 1);
        gridForm.add(new Label("Correo:"), 2, 1);
        gridForm.add(txtParticipanteCorreo, 3, 1);

        gridForm.add(new Label("Teléfono:"), 0, 2);
        gridForm.add(txtParticipanteTelefono, 1, 2);
        gridForm.add(new Label("Institución:"), 2, 2);
        gridForm.add(txtParticipanteInstitucion, 3, 2);

        // Botones CRUD
        HBox boxBotones = new HBox(10);
        btnParticipanteRegistrar.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        btnParticipanteActualizar.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        btnParticipanteEliminar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        boxBotones.getChildren().addAll(btnParticipanteRegistrar, btnParticipanteActualizar, btnParticipanteEliminar);

        // Buscador Dinámico / Reporte por Institución
        txtBuscarParticipantes.setPromptText("Buscar por institución o datos...");
        HBox boxBuscador = new HBox(10, new Label("Buscar:"), txtBuscarParticipantes);
        boxBuscador.setAlignment(Pos.CENTER_LEFT);

        // Tabla de Datos
        TableColumn<Participante, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idParticipante"));
        colId.setPrefWidth(50);

        TableColumn<Participante, String> colNombres = new TableColumn<>("Nombres");
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colNombres.setPrefWidth(120);

        TableColumn<Participante, String> colApellidos = new TableColumn<>("Apellidos");
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colApellidos.setPrefWidth(120);

        TableColumn<Participante, String> colDni = new TableColumn<>("DNI");
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colDni.setPrefWidth(90);

        TableColumn<Participante, String> colCorreo = new TableColumn<>("Correo");
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colCorreo.setPrefWidth(160);

        TableColumn<Participante, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colTelefono.setPrefWidth(90);

        TableColumn<Participante, String> colInstitucion = new TableColumn<>("Institución");
        colInstitucion.setCellValueFactory(new PropertyValueFactory<>("institucion"));
        colInstitucion.setPrefWidth(140);

        tablaParticipantes.getColumns().addAll(colId, colNombres, colApellidos, colDni, colCorreo, colTelefono, colInstitucion);
        tablaParticipantes.setItems(listaParticipantes);
        VBox.setVgrow(tablaParticipantes, Priority.ALWAYS);

        panelParticipantes.getChildren().addAll(lblTitulo, gridForm, boxBotones, new Separator(), boxBuscador, tablaParticipantes);
    }

    @SuppressWarnings("unchecked")
    private void construirPanelInscripciones() {
        panelInscripciones.setSpacing(15);

        Label lblTitulo = new Label("Gestión de Inscripciones");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Formulario de Entradas
        GridPane gridForm = new GridPane();
        gridForm.setHgap(10);
        gridForm.setVgap(10);
        gridForm.setPadding(new Insets(10));
        gridForm.setStyle("-fx-background-color: #ecf0f1; -fx-background-radius: 5px;");

        txtInscripcionIdEvento.setPromptText("ID Evento");
        txtInscripcionIdParticipante.setPromptText("ID Participante");
        cmbInscripcionEstado.getItems().addAll("ACTIVO", "INACTIVO", "PENDIENTE");
        cmbInscripcionEstado.setValue("ACTIVO");

        gridForm.add(new Label("ID Evento:"), 0, 0);
        gridForm.add(txtInscripcionIdEvento, 1, 0);
        gridForm.add(new Label("ID Participante:"), 0, 1);
        gridForm.add(txtInscripcionIdParticipante, 1, 1);
        gridForm.add(new Label("Estado:"), 0, 2);
        gridForm.add(cmbInscripcionEstado, 1, 2);

        // Botones de Acción CRUD
        HBox boxBotones = new HBox(10);
        btnInscripcionRegistrar.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        btnInscripcionActualizar.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        btnInscripcionEliminar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        boxBotones.getChildren().addAll(btnInscripcionRegistrar, btnInscripcionActualizar, btnInscripcionEliminar);

        // Buscador Dinámico
        txtBuscarInscripciones.setPromptText("Buscar inscripción...");
        HBox boxBuscador = new HBox(10, new Label("Buscar:"), txtBuscarInscripciones);
        boxBuscador.setAlignment(Pos.CENTER_LEFT);

        // Tabla de Datos
        TableColumn<Inscripcion, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idInscripcion"));
        colId.setPrefWidth(60);

        TableColumn<Inscripcion, Integer> colEvento = new TableColumn<>("ID Evento");
        colEvento.setCellValueFactory(new PropertyValueFactory<>("idEvento"));
        colEvento.setPrefWidth(100);

        TableColumn<Inscripcion, Integer> colParticipante = new TableColumn<>("ID Participante");
        colParticipante.setCellValueFactory(new PropertyValueFactory<>("idParticipante"));
        colParticipante.setPrefWidth(120);

        TableColumn<Inscripcion, Timestamp> colFecha = new TableColumn<>("Fecha Inscripción");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaInscripcion"));
        colFecha.setPrefWidth(180);

        TableColumn<Inscripcion, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colEstado.setPrefWidth(100);

        tablaInscripciones.getColumns().addAll(colId, colEvento, colParticipante, colFecha, colEstado);
        tablaInscripciones.setItems(listaInscripciones);
        VBox.setVgrow(tablaInscripciones, Priority.ALWAYS);

        panelInscripciones.getChildren().addAll(lblTitulo, gridForm, boxBotones, new Separator(), boxBuscador, tablaInscripciones);
    }

    // --- MÉTODOS DE NAVEGACIÓN ENTRE PANELES ---
    public void mostrarInicio() {
        ocultarPaneles();
        panelInicio.setVisible(true);
    }

    public void mostrarEventos() {
        ocultarPaneles();
        panelEventos.setVisible(true);
    }

    public void mostrarParticipantes() {
        ocultarPaneles();
        panelParticipantes.setVisible(true);
    }

    public void mostrarInscripciones() {
        ocultarPaneles();
        panelInscripciones.setVisible(true);
    }

    private void ocultarPaneles() {
        panelInicio.setVisible(false);
        panelEventos.setVisible(false);
        panelParticipantes.setVisible(false);
        panelInscripciones.setVisible(false);
    }

    // --- GETTERS Y MÉTODOS REQUERIDOS POR EL CONTROLADOR ---
    public Button getBtnInicio() { return btnInicio; }
    public Button getBtnEventos() { return btnEventos; }
    public Button getBtnParticipantes() { return btnParticipantes; }
    public Button getBtnInscripciones() { return btnInscripciones; }
    public Button getBtnLogout() { return btnLogout; }

    // GETTERS PARTICIPANTES
    public TextField getTxtParticipanteNombres() { return txtParticipanteNombres; }
    public TextField getTxtParticipanteApellidos() { return txtParticipanteApellidos; }
    public TextField getTxtParticipanteDni() { return txtParticipanteDni; }
    public TextField getTxtParticipanteCorreo() { return txtParticipanteCorreo; }
    public TextField getTxtParticipanteTelefono() { return txtParticipanteTelefono; }
    public TextField getTxtParticipanteInstitucion() { return txtParticipanteInstitucion; }
    public TextField getTxtBuscarParticipantes() { return txtBuscarParticipantes; }
    public Button getBtnParticipanteRegistrar() { return btnParticipanteRegistrar; }
    public Button getBtnParticipanteActualizar() { return btnParticipanteActualizar; }
    public Button getBtnParticipanteEliminar() { return btnParticipanteEliminar; }
    public TableView<Participante> getTablaParticipantes() { return tablaParticipantes; }

    public Participante getParticipanteSeleccionado() {
        return tablaParticipantes.getSelectionModel().getSelectedItem();
    }

    public void cargarParticipanteEnFormulario(Participante p) {
        if (p != null) {
            txtParticipanteNombres.setText(p.getNombres());
            txtParticipanteApellidos.setText(p.getApellidos());
            txtParticipanteDni.setText(p.getDni());
            txtParticipanteCorreo.setText(p.getCorreo());
            txtParticipanteTelefono.setText(p.getTelefono());
            txtParticipanteInstitucion.setText(p.getInstitucion());
        }
    }

    public void mostrarDatosParticipantes(List<Participante> lista) {
        listaParticipantes.setAll(lista);
    }

    public void limpiarFormularioParticipantes() {
        txtParticipanteNombres.clear();
        txtParticipanteApellidos.clear();
        txtParticipanteDni.clear();
        txtParticipanteCorreo.clear();
        txtParticipanteTelefono.clear();
        txtParticipanteInstitucion.clear();
        tablaParticipantes.getSelectionModel().clearSelection();
    }

    // GETTERS INSCRIPCIONES
    public TextField getTxtBuscarInscripciones() { return txtBuscarInscripciones; }
    public Button getBtnInscripcionRegistrar() { return btnInscripcionRegistrar; }
    public Button getBtnInscripcionActualizar() { return btnInscripcionActualizar; }
    public Button getBtnInscripcionEliminar() { return btnInscripcionEliminar; }
    public TableView<Inscripcion> getTablaInscripciones() { return tablaInscripciones; }

    public String getInscripcionIdEvento() { return txtInscripcionIdEvento.getText(); }
    public String getInscripcionIdParticipante() { return txtInscripcionIdParticipante.getText(); }
    public String getInscripcionEstado() { return cmbInscripcionEstado.getValue(); }

    public Inscripcion getInscripcionSeleccionada() {
        return tablaInscripciones.getSelectionModel().getSelectedItem();
    }

    public void cargarInscripcionEnFormulario(Inscripcion i) {
        if (i != null) {
            txtInscripcionIdEvento.setText(String.valueOf(i.getIdEvento()));
            txtInscripcionIdParticipante.setText(String.valueOf(i.getIdParticipante()));
            cmbInscripcionEstado.setValue(i.getEstado());
        }
    }

    public void mostrarDatosInscripciones(List<Inscripcion> lista) {
        listaInscripciones.setAll(lista);
    }

    public void limpiarFormularioInscripciones() {
        txtInscripcionIdEvento.clear();
        txtInscripcionIdParticipante.clear();
        cmbInscripcionEstado.setValue("ACTIVO");
        tablaInscripciones.getSelectionModel().clearSelection();
    }
}