package co.edu.uniquindio.proyecto.test;


import co.edu.uniquindio.proyecto.NegocioApplication;
import co.edu.uniquindio.proyecto.entidades.Producto;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.servicios.ProductoServicio;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@SpringBootTest(classes = NegocioApplication.class)
@Transactional
public class ProductoServicioTest {

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Test
    public void publicarProductoVentaTest(){
        Usuario  u = new Usuario("904","mario contreras","mario@gmail.com","3216758976","28#14-09","unimario","Heropro.12");

        LocalDate ldn = LocalDate.now();
        LocalDate ld = LocalDate.of(2020,1,8);
        Producto producto = new Producto("2","televisor","televisor smart tv",400000,ldn,ld,20);

        try {
            usuarioServicio.registrarUsuario(u);
            Producto salida =productoServicio.publicarProductoVender(producto,u);

            Assertions.assertNotNull(salida);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void registrarProducto(){
        LocalDate ldn = LocalDate.now();
        LocalDate ld = LocalDate.of(2024,1,8);
        Producto p = new Producto("2","televisor","televisor smart tv",400000,ldn,ld,20);

        try {
            productoServicio.registrarProducto(p);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void comentarProductoTest(){

        Usuario  u = new Usuario("905","juan alonso","jalonso@gmail.com","3216758976","28#14-09","unialonso","Heropro.12");

        LocalDate ldn = LocalDate.now();
        LocalDate ld = LocalDate.of(2024,1,8);
        Producto producto = new Producto("2","televisor","televisor smart tv",400000,ldn,ld,20);



        try {
            usuarioServicio.registrarUsuario(u);
            productoServicio.registrarProducto(producto);
            productoServicio.comentarProducto("precio minimo",u , producto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void guardarProductoEnFavoritosTest(){

        Usuario  u = new Usuario("904","mario contreras","mario@gmail.com","3216758976","28#14-09","unimario","Heropro.12");

        LocalDate ldn = LocalDate.now();
        LocalDate ld = LocalDate.of(2024,1,8);
        Producto p = new Producto("2","televisor","televisor smart tv",400000,ldn,ld,20);

        try {
            usuarioServicio.registrarUsuario(u);
            productoServicio.registrarProducto(p);

            productoServicio.guardarProductoEnFavoritos(p,u);
            //productoServicio.eliminarProductofavorito(p,u);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void buscarProductosPorNombreTest(){

        List<Producto> productos = productoServicio.buscarProductoPorNombre("lenovo",null);
        productos.forEach(p -> System.out.println(p));
    }


    @Test
    public void obtenerProductoTest(){

        try {
            Usuario vendedor = usuarioServicio.obtenerUsuario("909");
            LocalDate ldn = LocalDate.now();
            LocalDate ld = LocalDate.of(2020,1,8);
            Producto producto = new Producto("2",null,"televisor smart tv",400000,ldn,ld,null);
            // Producto publicado =productoServicio.publicarProducto(producto);

            // Assertions.assertNotNull(publicado);
        } catch (Exception e) {
            // throw new RuntimeException(e);
            Assertions.assertTrue(false, e.getMessage());
        }


    }

}
