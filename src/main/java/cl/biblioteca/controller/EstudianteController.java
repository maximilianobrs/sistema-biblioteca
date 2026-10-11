package cl.biblioteca.controller;

import cl.biblioteca.dao.EstudianteDAO;
import cl.biblioteca.dao.impl.EstudianteDAOImpl;
import cl.biblioteca.model.Estudiante;

import java.util.List;

public class EstudianteController {
    private EstudianteDAO estudianteDAO = new EstudianteDAOImpl();

    /**
     * Crea un estudiante.
     *
     * @param nombre nombre completo
     * @param rut RUT
     * @param curso curso del estudiante
     * @param correo correo electrónico
     * @return true si se creó; false si los datos no son válidos o falló
     */
    public boolean crear(String nombre, String rut, String curso, String correo){
        if (nombre.isEmpty() || rut.isEmpty()) {
            return false;
        }
        Estudiante estudiante = new Estudiante(correo, rut, nombre, curso);
        return estudianteDAO.create(estudiante);
    }

    /**
     * Actualiza los datos de un estudiante.
     *
     * @param id id del estudiante
     * @param nombre nombre completo (obligatorio)
     * @param rut RUT (obligatorio)
     * @param curso curso del estudiante
     * @param correo correo electrónico
     * @return true si se actualizó
     */
    public boolean actualizar(int id, String nombre, String rut, String curso, String correo){
        if (nombre.isEmpty() || rut.isEmpty()) {
            return false;
        }
        Estudiante estudiante = new Estudiante(id, nombre, rut, correo, curso);
        return estudianteDAO.update(estudiante);
    }

    /**
     * Elimina un estudiante.
     *
     * @param idEstudiante id del estudiante
     * @return true si se eliminó
     */
    public boolean eliminar(int idEstudiante){
        return estudianteDAO.delete(idEstudiante);
    }

    /**
     * Lista todos los estudiantes.
     *
     * @return lista de estudiantes
     */
    public List<Estudiante> listarTodo(){
        return estudianteDAO.readAll();
    }
}
