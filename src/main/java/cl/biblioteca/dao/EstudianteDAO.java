package cl.biblioteca.dao;

import cl.biblioteca.model.Estudiante;

import java.util.List;

public interface EstudianteDAO {
    /**
     * Inserta un registro nuevo.
     *
     * @param estudiante objeto a guardar
     * @return true si se guardó correctamente
     */
    boolean create(Estudiante estudiante);
    /**
     * Actualiza un registro existente.
     *
     * @param estudiante objeto con los datos nuevos
     * @return true si se actualizó correctamente
     */
    boolean update(Estudiante estudiante);
    /**
     * Elimina un registro por su id.
     *
     * @param idEstudiante id del registro a eliminar
     * @return true si se eliminó correctamente
     */
    boolean delete(int idEstudiante);
    /**
     * Obtiene todos los registros.
     *
     * @return lista con todos los registros
     */
    List<Estudiante> readAll();
}
