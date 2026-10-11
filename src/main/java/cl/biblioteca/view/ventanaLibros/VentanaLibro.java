package cl.biblioteca.view.ventanaLibros;

import cl.biblioteca.controller.CategoriaController;
import cl.biblioteca.controller.LibroController;
import cl.biblioteca.model.Categoria;
import cl.biblioteca.model.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaLibro extends JPanel {
    private LibroController libroController;
    private CategoriaController categoriaController;

    private JPanel panelPrincipal;
    private JTable tablaLibro;
    private DefaultTableModel modeloTabla;
    private int idSeleccionado = 0;
    private List<Categoria> listaCategorias;

    private JTextField txtTitulo;
    private JTextField txtEditorial;
    private JTextField txtAutor;
    private JTextField txtISBN;
    private JTextField txtStock;

    private JComboBox cmbCategoria;

    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JTextField txtCategoria;
    private JButton btnGuardarCategoria;
    private JButton btnLimpiarCategoria;


    public VentanaLibro(LibroController libroController, CategoriaController categoriaController){
        this.libroController = libroController;
        this.categoriaController = categoriaController;

        configuracion();

    }

    private void configuracion(){
        setLayout(new BorderLayout());
        add(panelPrincipal, BorderLayout.CENTER);

        cargarDatos();

        btnGuardar.addActionListener(e -> {
            guardarLibro();
        });
        btnLimpiar.addActionListener(e -> {
            limpiarFormulario();
        });
        btnEditar.addActionListener(e -> {
            editarLibro();
        });
        btnEliminar.addActionListener(e -> {
            eliminarLibro();
        });
        btnActualizar.addActionListener(e -> {
            actualizarTabla();
        });
        btnGuardarCategoria.addActionListener(e -> {
            guardarCategoria();
        });
        btnLimpiarCategoria.addActionListener(e -> {
            limpiarCategoria();
        });
    }

    private void cargarDatos(){
        String[] columnas = {"ID", "Título", "Autor", "Editorial", "ISBN", "Stock", "Categoría"};
        modeloTabla = new DefaultTableModel(columnas, 0);

        tablaLibro.setModel(modeloTabla);
        tablaLibro.setDefaultEditor(Object.class, null);

        try {
            listaCategorias = categoriaController.listarTodo();

            cmbCategoria.removeAllItems();
            for (Categoria categoria : listaCategorias) {
                cmbCategoria.addItem(categoria.getNombreCategoria());
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }

        actualizarTabla();
    }

    private void guardarLibro(){


        String titulo = txtTitulo.getText().trim();
        String autor = txtAutor.getText().trim();
        String editorial = txtEditorial.getText().trim();
        String isbn = txtISBN.getText().trim();

        int posicion = cmbCategoria.getSelectedIndex();
        if (posicion == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una categoría.");
            return;
        }

        int idCategoria = listaCategorias.get(posicion).getIdCategoria();

        try {
            int stock = Integer.parseInt(txtStock.getText().trim());
            boolean guardado;

            if (idSeleccionado == 0) {
                guardado = libroController.crear(titulo, autor, isbn, editorial, stock, idCategoria);
            } else {
                guardado = libroController.actualizar(idSeleccionado, titulo, autor, isbn, editorial, stock, idCategoria);
            }

            if (guardado) {
                JOptionPane.showMessageDialog(this, "Libro guardado.");
                limpiarFormulario();
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar. Revisa título y stock.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El stock debe ser un número entero.");
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void limpiarFormulario(){
        txtTitulo.setText("");
        txtAutor.setText("");
        txtEditorial.setText("");
        txtISBN.setText("");
        txtStock.setText("");

        if (cmbCategoria.getItemCount() > 0) {
            cmbCategoria.setSelectedIndex(0);
        }

        idSeleccionado = 0;
        tablaLibro.clearSelection();
    }

    private void editarLibro(){
        int fila = tablaLibro.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un libro de la tabla.");
            return;
        }

        idSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
        txtTitulo.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtAutor.setText(modeloTabla.getValueAt(fila, 2).toString());
        txtEditorial.setText(modeloTabla.getValueAt(fila, 3).toString());
        txtISBN.setText(modeloTabla.getValueAt(fila, 4).toString());
        txtStock.setText(modeloTabla.getValueAt(fila, 5).toString());
        cmbCategoria.setSelectedItem(modeloTabla.getValueAt(fila, 6).toString());
    }

    private void eliminarLibro(){
        int fila = tablaLibro.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un libro de la tabla.");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this, "¿Eliminar libro?");
        if (confirmar != JOptionPane.YES_OPTION) {
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        try {
            libroController.eliminar(id);
            limpiarFormulario();
            actualizarTabla();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void guardarCategoria(){
        String nombre = txtCategoria.getText().trim();

        try {
            boolean guardado = categoriaController.crear(nombre);

            if (guardado) {
                JOptionPane.showMessageDialog(this, "Categoría guardada.");
                limpiarCategoria();
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "Escribe el nombre de la categoría.");
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void limpiarCategoria(){
        txtCategoria.setText("");
    }

    private void actualizarTabla(){
        modeloTabla.setRowCount(0);

        try {
            for (Libro libro : libroController.listarTodo()) {
                String nombreCategoria = "";

                for (Categoria categoria : listaCategorias) {
                    if (categoria.getIdCategoria() == libro.getIdCategoria()) {
                        nombreCategoria = categoria.getNombreCategoria();
                    }
                }

                modeloTabla.addRow(new Object[]{
                        libro.getIdLibro(),
                        libro.getTitulo(),
                        libro.getAutor(),
                        libro.getEditorial(),
                        libro.getIsbn(),
                        libro.getStock(),
                        nombreCategoria
                });
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
