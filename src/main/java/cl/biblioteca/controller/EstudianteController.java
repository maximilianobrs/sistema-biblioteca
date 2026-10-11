package cl.biblioteca.controller;

import cl.biblioteca.dao.EstudianteDAO;
import cl.biblioteca.dao.impl.EstudianteDAOImpl;
import cl.biblioteca.model.Estudiante;

import java.util.List;

public class EstudianteController {
    private EstudianteDAO estudianteDAO = new EstudianteDAOImpl();

    public boolean crear(String nombre, String rut, String curso, String correo){
        if (nombre.isEmpty() || rut.isEmpty()) {
            return false;
        }
        Estudiante estudiante = new Estudiante(correo, rut, nombre, curso);
        return estudianteDAO.create(estudiante);
    }

    public boolean actualizar(int id, String nombre, String rut, String curso, String correo){
        if (nombre.isEmpty() || rut.isEmpty()) {
            return false;
        }
        Estudiante estudiante = new Estudiante(id, nombre, rut, correo, curso);
        return estudianteDAO.update(estudiante);
    }

    public boolean eliminar(int idEstudiante){
        return estudianteDAO.delete(idEstudiante);
    }

    public List<Estudiante> listarTodo(){
        return estudianteDAO.readAll();
    }
}
