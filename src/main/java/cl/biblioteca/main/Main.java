package cl.biblioteca.main;

import cl.biblioteca.controller.*;
import cl.biblioteca.model.Usuario;
import cl.biblioteca.view.VentanaLogin;
import cl.biblioteca.view.VentanaPrincipal;

import javax.swing.*;

public class Main {
    public static void main(String[] args){

        CategoriaController categoriaController = new CategoriaController();
        EstudianteController estudianteController = new EstudianteController();
        LibroController libroController = new LibroController();
        UsuarioController usuarioController = new UsuarioController();
        PrestamoController prestamoController = new PrestamoController();

        SwingUtilities.invokeLater(() -> {
            try {
                VentanaLogin login = new VentanaLogin(usuarioController);
                login.setVisible(true);

                Usuario usuario = login.getUsuario();
                if (usuario == null) {
                    System.out.println("Login cerrado sin ingresar.");
                    return;
                }

                VentanaPrincipal ventana = new VentanaPrincipal(usuario, categoriaController, estudianteController, libroController, usuarioController, prestamoController);
                ventana.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al abrir la ventana principal: " + ex);
            }
        });
    }
}
