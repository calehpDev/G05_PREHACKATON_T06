package vallegrande.edu.pe.prehackaton.Controller;

import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import vallegrande.edu.pe.prehackaton.Model.Inscripcion;
import vallegrande.edu.pe.prehackaton.Model.InscripcionDAO;
import vallegrande.edu.pe.prehackaton.Model.Participante;
import vallegrande.edu.pe.prehackaton.Model.ParticipanteDAO;
import vallegrande.edu.pe.prehackaton.View.MainView;

public class MainController {

    private final MainView view;
    private final Stage stage;
    private String usuarioLogueado;

    private final InscripcionDAO inscripcionDAO;
    private final ParticipanteDAO participanteDAO;

    public MainController(MainView view, Stage stage, String usuarioLogueado) {
        this.view = view;
        this.stage = stage;
        this.usuarioLogueado = usuarioLogueado;

        this.inscripcionDAO = new InscripcionDAO();
        this.participanteDAO = new ParticipanteDAO();

        configurarEventos();
    }

    private void configurarEventos() {

        if (view.getBtnInicio() != null) {
            view.getBtnInicio().setOnAction(e -> view.mostrarInicio());
        }

        if (view.getBtnEventos() != null) {
            view.getBtnEventos().setOnAction(e -> view.mostrarEventos());
        }

        if (view.getBtnLogout() != null) {
            view.getBtnLogout().setOnAction(e -> cerrarSesion());
        }

        // --- MÓDULO PARTICIPANTES ---
        if (view.getBtnParticipantes() != null) {
            view.getBtnParticipantes().setOnAction(e -> {
                view.mostrarParticipantes();
                cargarParticipantes();
            });
        }

        if (view.getTxtBuscarParticipantes() != null) {
            view.getTxtBuscarParticipantes().setOnKeyReleased(e -> buscarParticipantes());
        }

        if (view.getBtnParticipanteRegistrar() != null) {
            view.getBtnParticipanteRegistrar().setOnAction(e -> registrarParticipante());
        }

        if (view.getBtnParticipanteActualizar() != null) {
            view.getBtnParticipanteActualizar().setOnAction(e -> actualizarParticipante());
        }

        if (view.getBtnParticipanteEliminar() != null) {
            view.getBtnParticipanteEliminar().setOnAction(e -> eliminarParticipante());
        }

        if (view.getTablaParticipantes() != null) {
            view.getTablaParticipantes().setOnMouseClicked(e -> {
                Participante seleccion = view.getParticipanteSeleccionado();
                if (seleccion != null) {
                    view.cargarParticipanteEnFormulario(seleccion);
                }
            });
        }

        // --- MÓDULO INSCRIPCIONES ---
        if (view.getBtnInscripciones() != null) {
            view.getBtnInscripciones().setOnAction(e -> {
                view.mostrarInscripciones();
                cargarInscripciones();
            });
        }

        if (view.getTxtBuscarInscripciones() != null) {
            view.getTxtBuscarInscripciones().setOnKeyReleased(e -> buscarInscripciones());
        }

        if (view.getBtnInscripcionRegistrar() != null) {
            view.getBtnInscripcionRegistrar().setOnAction(e -> registrarInscripcion());
        }

        if (view.getBtnInscripcionActualizar() != null) {
            view.getBtnInscripcionActualizar().setOnAction(e -> actualizarInscripcion());
        }

        if (view.getBtnInscripcionEliminar() != null) {
            view.getBtnInscripcionEliminar().setOnAction(e -> eliminarInscripcion());
        }

        if (view.getTablaInscripciones() != null) {
            view.getTablaInscripciones().setOnMouseClicked(e -> {
                Inscripcion seleccion = view.getInscripcionSeleccionada();
                if (seleccion != null) {
                    view.cargarInscripcionEnFormulario(seleccion);
                }
            });
        }
    }

    // --- MÉTODOS PARTICIPANTES ---
    private void cargarParticipantes() {
        try {
            List<Participante> lista = participanteDAO.listarTodos();
            view.mostrarDatosParticipantes(lista);
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al cargar participantes: " + e.getMessage());
        }
    }

    private void buscarParticipantes() {
        try {
            String texto = view.getTxtBuscarParticipantes().getText().trim();
            List<Participante> lista = participanteDAO.listarPorInstitucion(texto);
            view.mostrarDatosParticipantes(lista);
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al buscar participantes: " + e.getMessage());
        }
    }

    private void registrarParticipante() {
        try {
            String nombres = view.getTxtParticipanteNombres().getText().trim();
            String apellidos = view.getTxtParticipanteApellidos().getText().trim();
            String dni = view.getTxtParticipanteDni().getText().trim();
            String correo = view.getTxtParticipanteCorreo().getText().trim();

            if (nombres.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || correo.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Validación", "Complete los campos obligatorios (Nombres, Apellidos, DNI, Correo).");
                return;
            }

            if (!dni.matches("\\d+")) {
                mostrarAlerta(Alert.AlertType.WARNING, "Validación", "El DNI solo debe contener números.");
                return;
            }

            Participante p = new Participante(
                    nombres,
                    apellidos,
                    dni,
                    correo,
                    view.getTxtParticipanteTelefono().getText().trim(),
                    view.getTxtParticipanteInstitucion().getText().trim()
            );

            if (participanteDAO.insertar(p)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Participante registrado correctamente.");
                view.limpiarFormularioParticipantes();
                cargarParticipantes();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar el participante en la base de datos.");
            }

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al registrar participante: " + e.getMessage());
        }
    }

    private void actualizarParticipante() {
        try {
            Participante seleccion = view.getParticipanteSeleccionado();
            if (seleccion == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Seleccione un participante de la tabla para actualizar.");
                return;
            }

            String nombres = view.getTxtParticipanteNombres().getText().trim();
            String apellidos = view.getTxtParticipanteApellidos().getText().trim();
            String dni = view.getTxtParticipanteDni().getText().trim();
            String correo = view.getTxtParticipanteCorreo().getText().trim();

            if (nombres.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || correo.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Validación", "Los campos principales no pueden estar vacíos.");
                return;
            }

            seleccion.setNombres(nombres);
            seleccion.setApellidos(apellidos);
            seleccion.setDni(dni);
            seleccion.setCorreo(correo);
            seleccion.setTelefono(view.getTxtParticipanteTelefono().getText().trim());
            seleccion.setInstitucion(view.getTxtParticipanteInstitucion().getText().trim());

            if (participanteDAO.actualizar(seleccion)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Participante actualizado correctamente.");
                view.limpiarFormularioParticipantes();
                cargarParticipantes();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el participante.");
            }

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al actualizar participante: " + e.getMessage());
        }
    }

    private void eliminarParticipante() {
        try {
            Participante seleccion = view.getParticipanteSeleccionado();
            if (seleccion == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Seleccione un participante para eliminar.");
                return;
            }

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmación de Borrado");
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("¿Está seguro de eliminar a " + seleccion.getNombres() + " " + seleccion.getApellidos() + "?");

            Optional<ButtonType> resultado = confirmacion.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                if (participanteDAO.eliminar(seleccion.getIdParticipante())) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Participante eliminado correctamente.");
                    view.limpiarFormularioParticipantes();
                    cargarParticipantes();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el participante.");
                }
            }

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al eliminar participante: " + e.getMessage());
        }
    }

    // --- MÉTODOS INSCRIPCIONES ---
    private void cargarInscripciones() {
        try {
            List<Inscripcion> lista = inscripcionDAO.listar();
            view.mostrarDatosInscripciones(lista);
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al cargar inscripciones: " + e.getMessage());
        }
    }

    private void buscarInscripciones() {
        try {
            String texto = view.getTxtBuscarInscripciones().getText().trim();
            List<Inscripcion> lista = inscripcionDAO.buscar(texto);
            view.mostrarDatosInscripciones(lista);
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al buscar inscripciones: " + e.getMessage());
        }
    }

    private void registrarInscripcion() {
        try {
            if (view.getInscripcionIdEvento().trim().isEmpty() || view.getInscripcionIdParticipante().trim().isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Validación", "Ingresa el ID del Evento y el ID del Participante.");
                return;
            }

            Inscripcion inscripcion = new Inscripcion();
            inscripcion.setIdEvento(Integer.parseInt(view.getInscripcionIdEvento().trim()));
            inscripcion.setIdParticipante(Integer.parseInt(view.getInscripcionIdParticipante().trim()));

            String estado = view.getInscripcionEstado();
            inscripcion.setEstado((estado != null && !estado.isEmpty()) ? estado : "ACTIVO");

            inscripcionDAO.insertar(inscripcion);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Inscripción registrada correctamente.");

            view.limpiarFormularioInscripciones();
            cargarInscripciones();

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", "Los IDs deben ser números enteros.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al registrar la inscripción: " + e.getMessage());
        }
    }

    private void actualizarInscripcion() {
        try {
            Inscripcion seleccion = view.getInscripcionSeleccionada();
            if (seleccion == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Selecciona una inscripción de la tabla para actualizar.");
                return;
            }

            if (view.getInscripcionIdEvento().trim().isEmpty() || view.getInscripcionIdParticipante().trim().isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Validación", "Los campos ID Evento e ID Participante no pueden estar vacíos.");
                return;
            }

            seleccion.setIdEvento(Integer.parseInt(view.getInscripcionIdEvento().trim()));
            seleccion.setIdParticipante(Integer.parseInt(view.getInscripcionIdParticipante().trim()));
            seleccion.setEstado(view.getInscripcionEstado());

            inscripcionDAO.actualizar(seleccion);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Inscripción actualizada correctamente.");

            view.limpiarFormularioInscripciones();
            cargarInscripciones();

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", "Verifica que los IDs ingresados sean valores numéricos.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al actualizar la inscripción: " + e.getMessage());
        }
    }

    private void eliminarInscripcion() {
        try {
            Inscripcion seleccion = view.getInscripcionSeleccionada();
            if (seleccion == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Selecciona una inscripción para eliminar.");
                return;
            }

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmación de Borrado");
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("¿Está seguro de eliminar la inscripción ID #" + seleccion.getIdInscripcion() + "?");

            Optional<ButtonType> resultado = confirmacion.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                inscripcionDAO.eliminar(seleccion.getIdInscripcion());
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Inscripción eliminada correctamente.");

                view.limpiarFormularioInscripciones();
                cargarInscripciones();
            }

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al eliminar la inscripción: " + e.getMessage());
        }
    }

    private void cerrarSesion() {
        stage.close();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();

    }
}