package co.edu.uniquinidio.proyecto.bean;

import co.edu.uniquindio.proyecto.entidades.Libro;
import co.edu.uniquindio.proyecto.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "mainLibroBean")
@ViewScoped
public class MainLibroBean implements Serializable {

    @Autowired
    private LibroServicio libroServicio;

    private List<Libro> libros;

    public List<Libro> getLibros() {
        return libros;
    }

    public void cargarLibros() {
        try {
            this.libros = libroServicio.listarTodosLibros();
        } catch (Exception e) {
            // Manejar la excepción según tus necesidades
        }
    }

    public void redirigirFormulario() {
        // Lógica para redirigir al formulario de creación
    }

    public void actualizarLibro(Libro libro) {
        // Lógica para actualizar el libro seleccionado
    }

    public void eliminarLibro(Libro libro) {
        // Lógica para eliminar el libro seleccionado
    }


}
