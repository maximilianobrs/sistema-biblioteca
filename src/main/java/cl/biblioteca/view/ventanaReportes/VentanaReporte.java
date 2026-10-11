package cl.biblioteca.view.ventanaReportes;

import cl.biblioteca.controller.EstudianteController;
import cl.biblioteca.controller.PrestamoController;
import cl.biblioteca.model.Estudiante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaReporte extends JPanel {

    private PrestamoController prestamoController;
    private EstudianteController estudianteController;
    private List<Estudiante> listaEstudiantes;

    private JPanel panelPrincipal;
    private JTable tablaReporte;
    private JButton btnLibrosMasPrestados;
    private JButton btnHistorialEstudiante;
    private JButton btnPrestamosActivos;
    private JComboBox cmbEstudiante;

    public VentanaReporte(PrestamoController prestamoController, EstudianteController estudianteController){
        this.prestamoController = prestamoController;
        this.estudianteController = estudianteController;

        setLayout(new BorderLayout());
        add(panelPrincipal, BorderLayout.CENTER);

        cargarEstudiantes();

        btnLibrosMasPrestados.addActionListener(e -> {
            try {
                mostrar(new String[]{"Libro", "Veces prestado"}, prestamoController.reporteLibrosMasPrestados());
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        btnHistorialEstudiante.addActionListener(e -> {
            int pos = cmbEstudiante.getSelectedIndex();
            if (pos == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un estudiante.");
                return;
            }
            try {
                int idEstudiante = listaEstudiantes.get(pos).getId();
                mostrar(new String[]{"Libro", "Fecha préstamo", "Fecha devolución", "Estado"},
                        prestamoController.reporteHistorialEstudiante(idEstudiante));
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        btnPrestamosActivos.addActionListener(e -> {
            try {
                mostrar(new String[]{"Estudiante", "Libro", "Fecha préstamo", "Fecha devolución", "Estado"},
                        prestamoController.reportePrestamosActivos());
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        cmbEstudiante.addActionListener(e -> btnHistorialEstudiante.doClick());
    }

    private void cargarEstudiantes(){
        try {
            listaEstudiantes = estudianteController.listarTodo();
            cmbEstudiante.removeAllItems();
            for (Estudiante estudiante : listaEstudiantes) {
                cmbEstudiante.addItem(estudiante.getNombre());
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void mostrar(String[] columnas, List<Object[]> filas){
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        for (Object[] fila : filas) {
            modelo.addRow(fila);
        }
        tablaReporte.setModel(modelo);
        tablaReporte.setDefaultEditor(Object.class, null);
    }
}
