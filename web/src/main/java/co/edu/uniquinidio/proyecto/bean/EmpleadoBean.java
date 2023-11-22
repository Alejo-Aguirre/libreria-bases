package co.edu.uniquinidio.proyecto.bean;

import co.edu.uniquindio.proyecto.entidades.Moderador;
import co.edu.uniquindio.proyecto.servicios.ModeradorServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;


@Component
@ViewScoped
public class EmpleadoBean  implements Serializable {

    @Getter
    @Setter
    private Moderador empleado;
    @Autowired
    private ModeradorServicio empleadoServicio;


// ciudades,ciudad seleccionada y pal depa lo mismo y en el resgitro agregar tambien
    @Getter @Setter
    private Departamento departamentoSeleccionado;

    private List<Departamento> departamentos;

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public List<Departamento> getDepartamentos() {
        return Arrays.asList(Departamento.values());
    }


    @Getter @Setter
    private Ciudad ciudadSeleccionada;

    private List<Ciudad> ciudades;


    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public List<Ciudad> getCiudades() {
        return Arrays.asList(Ciudad.values());
    }



    //cuando se instancia la clase automaticamente se llama a la siguiente funcion
    @PostConstruct
    public void inicializar(){
        empleado=new Moderador();
    }
    public void registrarEmpleado(){
        try {
            empleado.setCodigo(generarNuevoCodigo());
            empleadoServicio.registrarModerador(empleado);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Alerta","Registro exitoso");
            FacesContext.getCurrentInstance().addMessage(null,msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Alerta",e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,msg);
        }
    }
}