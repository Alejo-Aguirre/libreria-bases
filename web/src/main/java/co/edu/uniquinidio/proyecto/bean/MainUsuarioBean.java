package co.edu.uniquinidio.proyecto.bean;

import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void cargarUsuarios() {
        try {
            this.usuarios = usuarioServicio.listarUsuarios();
        } catch (Exception e) {
            // Manejar la excepción según tus necesidades
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar usuarios", null));
        }
    }

    public void actualizarUsuario(Usuario usuario) {
        try {
            usuarioServicio.actualizarUsuario(usuario);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario actualizado correctamente", null));
        } catch (Exception e) {
            // Manejar la excepción según tus necesidades
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar usuario", null));
        }
        cargarUsuarios();
    }

    public void eliminarUsuario(String codigo) {
        try {
            usuarioServicio.eliminarUsuario(codigo);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario eliminado correctamente", null));
            cargarUsuarios(); // Mover la carga de usuarios aquí
        } catch (Exception e) {
            // Manejar la excepción según tus necesidades
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar usuario", null));
        }
    }

    public String redirigirFormulario() {
        return "registrar_usuario.xhtml?faces-redirect=true";
    }

    public void generarReportePDF() {
        try {
            Document document = new Document();
            String filePath = "C:/Users/lenovo/Desktop/pdfs/usuarios.pdf"; // Establece la ruta y nombre del archivo PDF

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

                // Puedes personalizar el formato del informe según tus necesidades
            }

            document.close();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();

            externalContext.responseReset();
            externalContext.setResponseContentType("application/pdf");
            externalContext.setResponseHeader("Content-Disposition", "attachment;filename=usuarios.pdf");

            try {
                externalContext.getResponseOutputStream().write(filePath.getBytes());
                externalContext.getResponseOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            facesContext.responseComplete();

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al generar el informe PDF", null));
        }
    }

    // Otros métodos de tu bean
}
