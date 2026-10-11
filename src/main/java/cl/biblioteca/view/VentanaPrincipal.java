package cl.biblioteca.view;

import cl.biblioteca.controller.*;
import cl.biblioteca.model.Persona;
import cl.biblioteca.model.Usuario;
import cl.biblioteca.view.ventanaEstudiantes.VentanaEstudiante;
import cl.biblioteca.view.ventanaLibros.VentanaLibro;
import cl.biblioteca.view.ventanaPrestamos.VentanaPrestamo;
import cl.biblioteca.view.ventanaReportes.VentanaReporte;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame{

    private Usuario usuario;
    private CategoriaController categoriaController;
    private EstudianteController estudianteController;
    private LibroController libroController;
    private UsuarioController usuarioController;
    private PrestamoController prestamoController;

    private CardLayout cardLayout;

    private JPanel panelVentanaPrincipal;
    private JButton btnLibro;
    private JButton btnEstudiante;
    private JButton btnPrestamoDevoluciones;
    private JButton btnReporte;
    private JPanel panelContenido;

    public VentanaPrincipal(Usuario usuario, CategoriaController categoriaController,EstudianteController estudianteController,LibroController libroController, UsuarioController usuarioController,PrestamoController prestamoController) {
        this.usuario = usuario;
        this.categoriaController = categoriaController;
        this.estudianteController = estudianteController;
        this.libroController = libroController;
        this.usuarioController = usuarioController;
        this.prestamoController = prestamoController;

        Configuracion();
    }

    private void Configuracion() {
        Persona persona = usuario;

        cardLayout = new CardLayout();
        panelContenido.setLayout(cardLayout);

        VentanaEstudiante ventanaEstudiante = new VentanaEstudiante(estudianteController);
        VentanaLibro ventanaLibro = new VentanaLibro(libroController,categoriaController);
        VentanaPrestamo ventanaPrestamo = new VentanaPrestamo(prestamoController,estudianteController,libroController);
        VentanaReporte ventanaReporte = new VentanaReporte(prestamoController,estudianteController);

        panelContenido.add(ventanaLibro,"LIBRO");
        panelContenido.add(ventanaEstudiante,"ESTUDIANTE");
        panelContenido.add(ventanaPrestamo, "PRESTAMO");
        panelContenido.add(ventanaReporte, "REPORTE");

        setContentPane(panelVentanaPrincipal);
        setTitle("Sistema gestion biblioteca - " + persona.getDescripcion());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        btnLibro.addActionListener(e-> {
            cardLayout.show(panelContenido,"LIBRO");
        });

        btnEstudiante.addActionListener( e ->{
            cardLayout.show(panelContenido,"ESTUDIANTE");
        });

        btnPrestamoDevoluciones.addActionListener(e ->{
                cardLayout.show(panelContenido, "PRESTAMO");
        });

        btnReporte.addActionListener(e -> {
            cardLayout.show(panelContenido, "REPORTE");
        });

        if (!"bibliotecario".equalsIgnoreCase(usuario.getRol())) {
            btnLibro.setEnabled(false);
            btnEstudiante.setEnabled(false);
            ventanaPrestamo.restringirParaEstudiante();
            cardLayout.show(panelContenido, "PRESTAMO");
        }
    }

    private void abrirVentana(JFrame ventanaHija) {
        ventanaHija.setLocationRelativeTo(this);
        ventanaHija.setVisible(true);
    }
}
