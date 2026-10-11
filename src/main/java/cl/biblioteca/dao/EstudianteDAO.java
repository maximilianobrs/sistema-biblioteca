package cl.biblioteca.dao;

import cl.biblioteca.model.Estudiante;

import java.util.List;

public interface EstudianteDAO {
    boolean create(Estudiante estudiante);
    boolean update(Estudiante estudiante);
    boolean delete(int idEstudiante);
    List<Estudiante> readAll();
}
