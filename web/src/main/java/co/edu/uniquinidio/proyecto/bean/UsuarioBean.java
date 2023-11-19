package co.edu.uniquinidio.proyecto.bean;

import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.text.DecimalFormat;

@Component
@ViewScoped
public class UsuarioBean implements Serializable {

    @Getter @Setter
    private Usuario usuario;
    @Autowired
    private UsuarioServicio usuarioServicio;


    //cuando se instancia la clase automaticamente se llama a la siguiente funcion
    @PostConstruct
    public void inicializar(){
        usuario=new Usuario();
    }


    /**
     * Genera un nuevo código para un usuario.
     * El código sigue el formato "000001", "000002", etc.
     * @return Nuevo código generado
     */
    public String generarNuevoCodigo() {
        // Obtener el último código existente o establecerlo en "000000" si no hay usuarios
        String ultimoCodigo = String.valueOf(usuarioServicio.obtenerUltimoNumero());
        int nuevoNumero = 1;

        if (ultimoCodigo != null && !ultimoCodigo.isEmpty()) {
            // Obtener el número actual y aumentarlo en 1
            int numeroActual = Integer.parseInt(ultimoCodigo);
            nuevoNumero = numeroActual ;
        }

        // Formatear el nuevo número con ceros a la izquierda
        DecimalFormat formato = new DecimalFormat("000");
        String nuevoCodigo = formato.format(nuevoNumero);

        return nuevoCodigo;
    }
    public void registrarUsuario(){
        try {

            usuario.setCodigo(generarNuevoCodigo());
            usuarioServicio.registrarUsuario(usuario);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Alerta","Registro exitoso");
            FacesContext.getCurrentInstance().addMessage(null,msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Alerta",e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,msg);
        }
    }
}
