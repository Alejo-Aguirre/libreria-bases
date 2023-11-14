package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.entidades.*;

import java.util.List;

public interface ProductoServicio {

    Libro registrarProducto(Libro p) throws Exception;
    void actualizarProducto(Libro p) throws Exception;

    void eliminarProducto(String codigo) throws Exception;

    Libro obtenerProducto(String codigo) throws Exception;
    List<Libro> listarProductosPorCategoria(Categoria categoria);
    List<Libro> listarTodosProductos()throws Exception;

    void comentarProducto(String mensaje, Usuario usuario, Libro libro) throws Exception;

    void guardarProductoEnFavoritos(Libro libro, Usuario usuario) throws Exception;
    void eliminarProductofavorito(Libro libro, Usuario usuario)throws Exception;
    void comprarProductos(DetalleCompra detalleCompra, Libro libro);

    List<Libro> buscarProductoPorNombre(String nombre, String [] producto);

    List<Libro> buscarProducto(String nombre, String[] producto);

    List<Libro> listarProductos(String codigoUsuario)throws Exception;


    List<Libro> obtenerProductosPorCategoria(String categoriaSeleccionada);
}
