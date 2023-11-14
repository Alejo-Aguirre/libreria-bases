package co.edu.uniquinidio.proyecto.bean;

import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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
import java.util.List;

@Component
@ManagedBean(name = "mainUsuarioBean")
@ViewScoped
public class MainUsuarioBean implements Serializable {

    @Autowired
    private UsuarioServicio usuarioServicio;

    private List<Usuario> usuarios;

    @PostConstruct
    public void inicializar() {
        cargarUsuarios();
    }

    private Usuario usuarioSeleccionado;

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void cargarUsuarios() {
        try {
            this.usuarios = usuarioServicio.listarUsuarios();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar usuarios", null));
        }
    }

    public void onRowEdit(RowEditEvent<Usuario> event) {
        try {
            Usuario usuarioEditado = event.getObject();

            // Lógica de edición - actualiza el usuario en la base de datos
            usuarioServicio.actualizarUsuario(usuarioEditado);

            // Mostrar mensaje de éxito
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario editado correctamente", null);
            FacesContext.getCurrentInstance().addMessage("messages", msg);
        } catch (Exception e) {
            // Mostrar mensaje de error
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al editar usuario: " + e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage("messages", msg);
        }
    }

    public void onRowCancel(RowEditEvent<Usuario> event) {
        FacesMessage msg = new FacesMessage("Edición cancelada", String.valueOf(event.getObject().getCodigo()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }


// Otros imports...

    public void eliminarUsuario(String codigo) {
        try {
            // Lógica para eliminar usuario
            usuarioServicio.eliminarUsuario(codigo);
            // Mostrar mensaje de éxito
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario eliminado correctamente", null);
            FacesContext.getCurrentInstance().addMessage("messages", msg);
        } catch (Exception e) {
            // Mostrar mensaje de error
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar usuario: " + e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage("messages", msg);
        }
    }


    public String redirigirFormulario() {
        return "registrar_usuario.xhtml?faces-redirect=true";
    }

    public void generarReportePDF() {
        try {
            // Lógica para generar el informe PDF
            Document document = new Document();
            String filePath = "C:/Users/lenovo/Desktop/pdfs/usuarios.pdf";

            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            document.add(new Paragraph("Reporte de Usuarios"));

            for (Usuario usuario : usuarios) {
                document.add(new Paragraph("Código: " + usuario.getCodigo()));
                document.add(new Paragraph("Nombre: " + usuario.getNombre()));
                document.add(new Paragraph("Correo: " + usuario.getEmail()));
                document.add(new Paragraph("Dirección: " + usuario.getDireccion()));
                document.add(new Paragraph("Username: " + usuario.getUsername()));
                document.add(new Paragraph("Password: " + usuario.getPassword()));
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
