package co.edu.uniquinidio.proyecto.bean;

import co.edu.uniquindio.proyecto.entidades.Libro;
import co.edu.uniquindio.proyecto.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    @PostConstruct
    public void inicializar() {
        cargarLibros();
    }
    public void cargarLibros() {
        try {
            this.libros = libroServicio.listarTodosLibros();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar usuarios", null));
        }
    }

    public String redirigirFormulario() {
        // Lógica para redirigir al formulario de creación
    }

    public void actualizarLibro(Libro libro) {
        // Lógica para actualizar el libro seleccionado
    }

    public void eliminarLibro(String codigo) {
        try {
            // Lógica para eliminar usuario
            libroServicio.eliminarLibro(codigo);
            // Mostrar mensaje de éxito
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario eliminado correctamente", null);
            FacesContext.getCurrentInstance().addMessage("messages", msg);
        } catch (Exception e) {
            // Mostrar mensaje de error
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar usuario: " + e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage("messages", msg);
        }
    }


}
