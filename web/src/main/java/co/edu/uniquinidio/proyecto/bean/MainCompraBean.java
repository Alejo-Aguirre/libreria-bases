package co.edu.uniquinidio.proyecto.bean;

import co.edu.uniquindio.proyecto.entidades.*;
import co.edu.uniquindio.proyecto.servicios.CompraServicio;
import co.edu.uniquindio.proyecto.servicios.DetalleCompraServicio;
import co.edu.uniquindio.proyecto.servicios.LibroServicio;
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
import java.time.LocalDate;
import java.util.List;

@Component
@ViewScoped
public class MainCompraBean implements Serializable {


    @Autowired
    private CompraServicio compraServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private DetalleCompraServicio detalleCompraServicio;

    @Autowired
    private LibroServicio libroServicio;
    private  Compra compra;

    private DetalleCompra detalleCompra;
    private List<Compra> compras;

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    @PostConstruct
    public void inicializar(){
        compra =new Compra();
        detalleCompra= new DetalleCompra();
        cargarCompras();
    }

    public void cargarCompras() {
        try {
            // Asegúrate de que libroServicio.listarTodosLibros() devuelve datos
            this.compras = compraServicio.listarCompras();
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la excepción para obtener más detalles en la consola
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar libros", null));
        }
    }


    public void crearCompra() {
        try {

            //seteo de la compra
            compra.setCodigo("006");
            compra.setMiUsuario(usuarioServicio.obtenerUsuario("910"));
            compra.setFechaCreacion(LocalDate.now());
            compra.setValorTotal(200F);
            compra.setMedioDePago(MedioDePago.MASTERCARD);
            compraServicio.registrarCompra(compra);

            //Seteo del detalle compra
            detalleCompra.setCodigo("1234");
            detalleCompra.setMiCompra(compraServicio.obtenerCompra("005"));
            detalleCompra.setUnidades(3);
            detalleCompra.setPrecioProducto(500F);
            detalleCompra.setMiLibro(libroServicio.obtenerLibro("123"));
            detalleCompraServicio.registrarDetalleCompra(detalleCompra);
            // Mostrar mensaje de éxito
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Compra creada exitosamente", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Exception e) {
            e.printStackTrace();

            // Mostrar mensaje de error
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al crear la compra: " + e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }


    public void eliminarCompra(String codigo) {
        try {
            // Lógica para eliminar usuario
            compraServicio.eliminarCompra(codigo);
            // Mostrar mensaje de éxito
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "compra eliminada correctamente", null);
            FacesContext.getCurrentInstance().addMessage("messages", msg);
        } catch (Exception e) {
            // Mostrar mensaje de error
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar libro: " + e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage("messages", msg);
        }
    }
}
