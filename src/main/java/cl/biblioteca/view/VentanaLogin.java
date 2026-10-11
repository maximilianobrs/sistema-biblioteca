package cl.biblioteca.view;

import cl.biblioteca.controller.UsuarioController;
import cl.biblioteca.model.Usuario;

import javax.swing.*;

public class VentanaLogin extends JDialog{
    private UsuarioController usuarioController;
    private Usuario usuario;

    private JTextField txtCorreo;
    private JTextField txtContrasena;
    private JButton btnInicialSesion;
    private JPanel panelPrincipal;

    public VentanaLogin(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;

        setContentPane(panelPrincipal);
        setTitle("Iniciar sesión");
        setModal(true);
        pack();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        getRootPane().setDefaultButton(btnInicialSesion);

        btnInicialSesion.addActionListener(e -> {
            ingresar();
        });
    }

    private void ingresar() {
        try {
            String correo = txtCorreo.getText().trim();

            if (correo.isEmpty() || !correo.contains("@")) {
                JOptionPane.showMessageDialog(this, "Ingresa un correo válido.");
                return;
            }

            Usuario encontrado = usuarioController.login(correo);

            if (encontrado == null) {
                JOptionPane.showMessageDialog(this, "Correo no registrado.");
                return;
            }

            usuario = encontrado;
            dispose();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
