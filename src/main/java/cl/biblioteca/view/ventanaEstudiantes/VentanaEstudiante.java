package cl.biblioteca.view.ventanaEstudiantes;

import cl.biblioteca.controller.EstudianteController;
import cl.biblioteca.model.Estudiante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaEstudiante extends JPanel {

    private EstudianteController estudianteController;

    private JPanel panelPrincipal;

    private DefaultTableModel modeloTabla;
    private int idSeleccionado = 0;
    private JTable tablaEstudiante;

    private JTextField txtNombre;
    private JTextField txtRut;
    private JTextField txtCurso;
    private JTextField txtCorreo;

    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnActualizar;



    public VentanaEstudiante(EstudianteController estudianteController){
        this.estudianteController = estudianteController;
        configuracion();
    }

    private void configuracion(){
        setLayout(new BorderLayout());
        add(panelPrincipal, BorderLayout.CENTER);

        cargarDatos();

        btnGuardar.addActionListener(e -> {
            guardarEstudiante();
        });
        btnLimpiar.addActionListener(e -> {
            limpiarFormulario();
        });
        btnEditar.addActionListener(e -> {
            editarEstudiante();
        });
        btnEliminar.addActionListener(e -> {
            eliminarEstudiante();
        });
        btnActualizar.addActionListener(e -> {
            actualizarTabla();
        });
    }

    private void cargarDatos(){
        String[] columnas = {"ID", "Nombre", "RUT", "Curso", "Correo"};
        modeloTabla = new DefaultTableModel(columnas, 0);

        tablaEstudiante.setModel(modeloTabla);
        tablaEstudiante.setDefaultEditor(Object.class, null);

        actualizarTabla();
    }

    private void guardarEstudiante(){
        String nombre = txtNombre.getText().trim();
        String rut = txtRut.getText().trim();
        String curso = txtCurso.getText().trim();
        String correo = txtCorreo.getText().trim();

        try {
            boolean guardado;

            if (idSeleccionado == 0) {
                guardado = estudianteController.crear(nombre, rut, curso, correo);
            } else {
                guardado = estudianteController.actualizar(idSeleccionado, nombre, rut, curso, correo);
            }

            if (guardado) {
                JOptionPane.showMessageDialog(this, "Estudiante guardado.");
                limpiarFormulario();
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar. Revisa nombre y RUT.");
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void limpiarFormulario(){
        txtNombre.setText("");
        txtRut.setText("");
        txtCurso.setText("");
        txtCorreo.setText("");

        idSeleccionado = 0;
        tablaEstudiante.clearSelection();
    }

    private void editarEstudiante(){
        int fila = tablaEstudiante.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un estudiante de la tabla.");
            return;
        }

        idSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
        txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtRut.setText(modeloTabla.getValueAt(fila, 2).toString());
        txtCurso.setText(modeloTabla.getValueAt(fila, 3).toString());
        txtCorreo.setText(modeloTabla.getValueAt(fila, 4).toString());

    }

    private void eliminarEstudiante(){
        int fila = tablaEstudiante.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un estudiante de la tabla.");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this, "¿Eliminar estudiante?");
        if (confirmar != JOptionPane.YES_OPTION) {
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        try {
            estudianteController.eliminar(id);
            limpiarFormulario();
            actualizarTabla();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void actualizarTabla(){
        modeloTabla.setRowCount(0);

        try {

            for (Estudiante estudiante : estudianteController.listarTodo()) {
                modeloTabla.addRow(new Object[]{
                        estudiante.getId(),
                        estudiante.getNombre(),
                        estudiante.getRut(),
                        estudiante.getCurso(),
                        estudiante.getCorreo()
                });
            }

        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

}
