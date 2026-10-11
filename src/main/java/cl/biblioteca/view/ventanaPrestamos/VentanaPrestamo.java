package cl.biblioteca.view.ventanaPrestamos;

import cl.biblioteca.controller.EstudianteController;
import cl.biblioteca.controller.LibroController;
import cl.biblioteca.controller.PrestamoController;
import cl.biblioteca.model.Estudiante;
import cl.biblioteca.model.Libro;
import cl.biblioteca.model.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class VentanaPrestamo extends JPanel {
    private PrestamoController prestamoController;
    private EstudianteController estudianteController;
    private LibroController libroController;

    private JPanel panelPrincipal;

    private JTable tablaPrestamo;
    private DefaultTableModel modeloTabla;
    private int idSeleccionado = 0;
    private boolean devueltoSeleccionado = false;
    private List<Estudiante> listaEstudiantes;
    private List<Libro> listaLibros;

    private JTextField txtFechaPrestamo;
    private JTextField txtFechaDevolucion;

    private JComboBox cmbEstudiante;
    private JComboBox cmbLibro;

    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JButton btnDevolver;

    public VentanaPrestamo(PrestamoController prestamoController, EstudianteController estudianteController,
                           LibroController libroController){
        this.prestamoController = prestamoController;
        this.estudianteController = estudianteController;
        this.libroController = libroController;

        configuracion();
    }

    private void configuracion(){
        setLayout(new BorderLayout());
        add(panelPrincipal, BorderLayout.CENTER);

        cargarDatos();

        btnGuardar.addActionListener(e -> {
            guardarPrestamo();
        });
        btnLimpiar.addActionListener(e -> {
            limpiarFormulario();
        });
        btnEditar.addActionListener(e -> {
            editarPrestamo();
        });
        btnEliminar.addActionListener(e -> {
            eliminarPrestamo();
        });
        btnActualizar.addActionListener(e -> {
            actualizarTabla();
        });
        btnDevolver.addActionListener(e -> {
            devolverPrestamo();
        });
    }

    private void cargarDatos(){
        String[] columnas = {"ID", "Estudiante", "Libro", "Fecha préstamo", "Fecha devolución", "Devuelto"};
        modeloTabla = new DefaultTableModel(columnas, 0);

        tablaPrestamo.setModel(modeloTabla);
        tablaPrestamo.setDefaultEditor(Object.class, null);

        txtFechaPrestamo.setEditable(false);
        txtFechaDevolucion.setEditable(false);

        try {
            listaEstudiantes = estudianteController.listarTodo();
            cmbEstudiante.removeAllItems();
            for (Estudiante estudiante : listaEstudiantes) {
                cmbEstudiante.addItem(estudiante.getNombre());
            }

            listaLibros = libroController.listarTodo();
            cmbLibro.removeAllItems();
            for (Libro libro : listaLibros) {
                cmbLibro.addItem(libro.getTitulo());
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }

        actualizarTabla();
        limpiarFormulario();
    }

    private void guardarPrestamo(){
        int posEstudiante = cmbEstudiante.getSelectedIndex();
        int posLibro = cmbLibro.getSelectedIndex();

        if (posEstudiante == -1 || posLibro == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un estudiante y un libro.");
            return;
        }

        int idEstudiante = listaEstudiantes.get(posEstudiante).getId();
        int idLibro = listaLibros.get(posLibro).getIdLibro();

        if (idSeleccionado == 0) {
            btnGuardar.setEnabled(false);

            Thread hilo = new Thread(() -> {
                try {
                    boolean guardado = prestamoController.crear(idEstudiante, idLibro);

                    SwingUtilities.invokeLater(() -> {
                        btnGuardar.setEnabled(true);
                        if (guardado) {
                            JOptionPane.showMessageDialog(this, "Préstamo guardado.");
                            limpiarFormulario();
                            actualizarTabla();
                        } else {
                            JOptionPane.showMessageDialog(this, "No se pudo guardar. Puede que no haya stock.");
                        }
                    });
                } catch (RuntimeException ex) {
                    SwingUtilities.invokeLater(() -> {
                        btnGuardar.setEnabled(true);
                        JOptionPane.showMessageDialog(this, ex.getMessage());
                    });
                }
            });
            hilo.start();
            return;
        }

        try {
            LocalDate fechaPrestamo = LocalDate.parse(txtFechaPrestamo.getText().trim());
            LocalDate fechaDevolucion = LocalDate.parse(txtFechaDevolucion.getText().trim());
            boolean guardado = prestamoController.actualizar(idSeleccionado, idEstudiante, idLibro,
                    fechaPrestamo, fechaDevolucion, devueltoSeleccionado);

            if (guardado) {
                JOptionPane.showMessageDialog(this, "Préstamo guardado.");
                limpiarFormulario();
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar el préstamo.");
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Las fechas deben tener formato AAAA-MM-DD.");
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void devolverPrestamo(){
        int fila = tablaPrestamo.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un préstamo de la tabla.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        try {
            if (prestamoController.devolver(id)) {
                JOptionPane.showMessageDialog(this, "Devolución registrada.");
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Ese préstamo ya fue devuelto.");
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void limpiarFormulario(){
        if (cmbEstudiante.getItemCount() > 0) {
            cmbEstudiante.setSelectedIndex(0);
        }
        if (cmbLibro.getItemCount() > 0) {
            cmbLibro.setSelectedIndex(0);
        }

        txtFechaPrestamo.setText(LocalDate.now().toString());
        txtFechaDevolucion.setText(LocalDate.now().plusDays(7).toString());

        idSeleccionado = 0;
        devueltoSeleccionado = false;
        tablaPrestamo.clearSelection();
    }

    private void editarPrestamo(){
        int fila = tablaPrestamo.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un préstamo de la tabla.");
            return;
        }

        idSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
        cmbEstudiante.setSelectedItem(modeloTabla.getValueAt(fila, 1).toString());
        cmbLibro.setSelectedItem(modeloTabla.getValueAt(fila, 2).toString());
        txtFechaPrestamo.setText(modeloTabla.getValueAt(fila, 3).toString());
        txtFechaDevolucion.setText(modeloTabla.getValueAt(fila, 4).toString());
        devueltoSeleccionado = (boolean) modeloTabla.getValueAt(fila, 5);
    }

    private void eliminarPrestamo(){
        int fila = tablaPrestamo.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un préstamo de la tabla.");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this, "¿Eliminar préstamo?");
        if (confirmar != JOptionPane.YES_OPTION) {
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        try {
            prestamoController.eliminar(id);
            limpiarFormulario();
            actualizarTabla();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void actualizarTabla(){
        modeloTabla.setRowCount(0);

        try {
            for (Prestamo prestamo : prestamoController.listarTodo()) {
                modeloTabla.addRow(new Object[]{
                        prestamo.getIdPrestamo(),
                        buscarNombreEstudiante(prestamo.getIdEstudiante()),
                        buscarTituloLibro(prestamo.getIdLibro()),
                        prestamo.getFechaPrestamo(),
                        prestamo.getFechaDevolucion(),
                        prestamo.isDevuelto()
                });
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private String buscarNombreEstudiante(int idEstudiante){
        for (Estudiante estudiante : listaEstudiantes) {
            if (estudiante.getId() == idEstudiante) {
                return estudiante.getNombre();
            }
        }
        return "";
    }

    private String buscarTituloLibro(int idLibro){
        for (Libro libro : listaLibros) {
            if (libro.getIdLibro() == idLibro) {
                return libro.getTitulo();
            }
        }
        return "";
    }

    public void restringirParaEstudiante(){
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }
}
