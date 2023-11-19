package co.edu.uniquinidio.proyecto.bean;

import co.edu.uniquindio.proyecto.entidades.Moderador;
import co.edu.uniquindio.proyecto.servicios.ModeradorServicio;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@ManagedBean(name = "mainEmpleadosBean")
@ViewScoped
public class MainEmpleadoBean implements Serializable {

    @Getter @Setter
    private String codigoBusqueda;
    @Autowired
    private ModeradorServicio empleadoServicio;

    private List<Moderador> empleados;

    @PostConstruct
    public void inicializar() {
        cargarEmpleados();
    }

    private Moderador empleadoSeleccionado;

    public Moderador getEmpleadoSeleccionado() {
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Moderador empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    public List<Moderador> getEmpleados() {
        return empleados;
    }

    public void cargarEmpleados() {
        try {
            this.empleados = empleadoServicio.listarModeradores();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar empleados", null));
        }
    }

    public void onRowEdit(RowEditEvent<Moderador> event) {
        try {
            Moderador empleadoEditado = event.getObject();

            // Lógica de edición - actualiza el empleado en la base de datos
            empleadoServicio.actualizarModerador(empleadoEditado);

            // Mostrar mensaje de éxito
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Empleado editado correctamente", null);
            FacesContext.getCurrentInstance().addMessage("messages", msg);
        } catch (Exception e) {
            // Mostrar mensaje de error
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al editar empleado: " + e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage("messages", msg);
        }
    }

    public void onRowCancel(RowEditEvent<Moderador> event) {
        FacesMessage msg = new FacesMessage("Edición cancelada", String.valueOf(event.getObject().getCodigo()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void eliminarEmpleado(String codigo) {
        try {
            // Lógica para eliminar empleado
            empleadoServicio.eliminarModerador(codigo);
            // Mostrar mensaje de éxito
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Empleado eliminado correctamente", null);
            FacesContext.getCurrentInstance().addMessage("messages", msg);
        } catch (Exception e) {
            // Mostrar mensaje de error
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar empleado: " + e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage("messages", msg);
        }
    }

// Getter y setter para codigoBusqueda


    public void buscarEmpleadoPorCodigo() {
        try {
            System.out.println("Iniciando búsqueda de empleado por código: " + codigoBusqueda);

            if (codigoBusqueda != null && !codigoBusqueda.trim().isEmpty()) {
                // Lógica para buscar empleados por código
                Moderador empleadoEncontrado = empleadoServicio.obtenerModerador(codigoBusqueda);

                if (empleadoEncontrado != null) {
                    // Mostrar solo el empleado encontrado en la tabla
                    this.empleados = Collections.singletonList(empleadoEncontrado);
                    System.out.println("Empleado encontrado: " + empleadoEncontrado.getCodigo());
                } else {
                    // Mostrar mensaje si no se encuentra el empleado
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Empleado no encontrado", null);
                    FacesContext.getCurrentInstance().addMessage("messages", msg);
                    cargarEmpleados(); // Recargar la lista completa de empleados
                    System.out.println("Empleado no encontrado. Recargando la lista completa de empleados.");
                }
            } else {
                // Mostrar mensaje si el código de búsqueda está vacío
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Ingrese un código de búsqueda válido", null);
                FacesContext.getCurrentInstance().addMessage("messages", msg);
                cargarEmpleados(); // Recargar la lista completa de empleados
                System.out.println("Código de búsqueda vacío. Recargando la lista completa de empleados.");
            }

        } catch (Exception e) {
            // Mostrar mensaje de error
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al buscar empleado por código: " + e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage("messages", msg);
            System.out.println("Error durante la búsqueda de empleado por código: " + e.getMessage());
        }
    }

    public String redirigirFormulario() {
        return "crear_Empleado.xhtml?faces-redirect=true";
    }

    public void generarReportePDF() {
        try {
            // Lógica para generar el informe PDF
            Document document = new Document();
            String filePath = "C:/Users/lenovo/Desktop/pdfs/empleados.pdf";

            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            document.add(new Paragraph("Reporte de Empleados"));

            for (Moderador empleado : empleados) {
                document.add(new Paragraph("Código: " + empleado.getCodigo()));
                document.add(new Paragraph("Nombre: " + empleado.getNombre()));
                document.add(new Paragraph("Correo: " + empleado.getEmail()));
                document.add(new Paragraph("Username: " + empleado.getUsername()));
                document.add(new Paragraph("Password: " + empleado.getPassword()));
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
