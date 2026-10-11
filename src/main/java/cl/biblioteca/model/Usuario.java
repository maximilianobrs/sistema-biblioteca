package cl.biblioteca.model;

public class Usuario extends Persona{
    private String contrasena;
    private String rol;

    public Usuario() {}

    public Usuario(String rol) {
        this.rol = rol;
    }

    public Usuario(String correo, String rut, String nombre, String rol) {
        super(correo, rut, nombre);
        this.rol = rol;
    }

    public Usuario(int id, String nombre, String rut, String correo, String rol) {
        super(id, nombre, rut, correo);
        this.rol = rol;
    }

    public Usuario(int id, String nombre, String rut, String correo, String contrasena, String rol) {
        super(id, nombre, rut, correo);
        this.contrasena = contrasena;
        this.rol = rol;
    }

    @Override
    public String getDescripcion() {
        return "Usuario: " + nombre + " - Rol: " + rol;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
