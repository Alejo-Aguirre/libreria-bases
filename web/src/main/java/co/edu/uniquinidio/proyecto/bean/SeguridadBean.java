package co.edu.uniquinidio.proyecto.bean;


import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

@Scope("session")
@Component
public class SeguridadBean {



    @Getter @Setter
    private boolean autenticado;


    @Getter @Setter
    Usuario usuarioSesion;

    @Getter @Setter
    private String username,password;

    @Autowired
    private UsuarioServicio usuarioServicio;

    public String iniciarSesion(){

        if (!username.isEmpty()&& !password.isEmpty()){
            try{

                usuarioSesion=usuarioServicio.iniciarSesion(username,password);
                autenticado =true;
                return "index?faces-redirect=true";
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
