package cl.biblioteca.model;

/**
 * Estudiante que puede solicitar préstamos de libros.
 * Hereda los datos básicos de {@link Persona} y agrega el curso.
 */
public class Estudiante extends Persona{
    private String curso;

    public Estudiante() {}

    public Estudiante(String curso) {
        this.curso = curso;
    }

    public Estudiante(String correo, String rut, String nombre, String curso) {
        super(correo, rut, nombre);
        this.curso = curso;
    }

    public Estudiante(int id, String nombre, String rut, String correo, String curso) {
        super(id, nombre, rut, correo);
        this.curso = curso;
    }

    @Override
    public String getDescripcion() {
        return "Estudiante: " + nombre + " - Curso: " + curso;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}
