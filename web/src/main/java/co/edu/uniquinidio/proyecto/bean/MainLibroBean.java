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


    private String codigo;

// ... Otros métodos y propiedades ...

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    @Autowired
    private LibroServicio libroServicio;

    private List<Libro> libros;

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }


    @PostConstruct
    public void inicializar() {
        cargarLibros();
    }
    // Método para cargar los libros
    public void cargarLibros() {
        try {
            // Asegúrate de que libroServicio.listarTodosLibros() devuelve datos
            this.libros = libroServicio.listarTodosLibros();
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la excepción para obtener más detalles en la consola
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar libros", null));
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
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "libro eliminado correctamente", null);
            FacesContext.getCurrentInstance().addMessage("messages", msg);
        } catch (Exception e) {
            // Mostrar mensaje de error
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar libro: " + e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage("messages", msg);
        }
    }

    public void buscarLibros(String codigo) {
        try {
            // Lógica para buscar libros por código
            List<Libro> librosEncontrados = libroServicio.buscarLibrosCodigo(codigo);

            // Asignar los resultados a la propiedad en el bean
            setLibros(librosEncontrados);

            // Mostrar mensaje de éxito
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Libro(s) buscado(s) correctamente", null);
            FacesContext.getCurrentInstance().addMessage("messages", msg);
        } catch (Exception e) {
            // Mostrar mensaje de error
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al buscar libro: " + e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage("messages", msg);
        }
    }



    public void generarReportePDF() {
        try {
            // Lógica para generar el informe PDF
            Document document = new Document();
            String filePath = "C:/Users/lenovo/Desktop/pdfs/libros.pdf";

            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            document.add(new Paragraph("Reporte de Libros"));

            for (Libro libro : libros) {
                document.add(new Paragraph("Código: " + libro.getCodigo()));
                document.add(new Paragraph("Autor: " + libro.getAutor()));
                document.add(new Paragraph("Descripción: " + libro.getDescripcion()));
                document.add(new Paragraph("Fecha de Creación: " + libro.getFechaCreacion()));
                document.add(new Paragraph("Nombre: " + libro.getNombre()));
                document.add(new Paragraph("Precio: " + libro.getPrecio()));
                document.add(new Paragraph("Unidades: " + libro.getUnidades()));
                document.add(new Paragraph("Categoría Código: " + libro.getMiCategoria().getCodigo()));
                document.add(new Paragraph(""));
            }

            document.close();

            // Mostrar mensaje de éxito
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informe PDF generado correctamente", null);
            FacesContext.getCurrentInstance().addMessage("messages", msg);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();

            // Mostrar mensaje de error
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al generar el informe PDF: " + e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage("messages", msg);
        }
    }


}
