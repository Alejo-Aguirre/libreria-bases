package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.entidades.*;
import co.edu.uniquindio.proyecto.repositorios.ComentarioRepo;
import co.edu.uniquindio.proyecto.repositorios.ProductoRepo;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServicioImpl implements ProductoServicio{

    private final ProductoRepo productoRepo;

    private final UsuarioRepo usuarioRepo;
    private final ComentarioRepo comentarioRepo;

    public ProductoServicioImpl(ProductoRepo productoRepo,ComentarioRepo comentarioRepo,UsuarioRepo usuarioRepo) {
        this.productoRepo = productoRepo;
        this.comentarioRepo=comentarioRepo;
        this.usuarioRepo=usuarioRepo;
    }

    @Override
    public Libro registrarProducto(Libro p) throws Exception {
        Optional<Libro> buscado= productoRepo.findById(p.getCodigo());
        if (buscado.isPresent()){
            throw new Exception("El codigo del producto ya existe");
        }

        return productoRepo.save(p);
    }





    @Override
    public void actualizarProducto(Libro p) throws Exception {

    }

    @Override
    public void eliminarProducto(String codigo) throws Exception {
        Optional<Libro> producto = productoRepo.findById(codigo);

        if (producto.isEmpty()){
            throw new Exception("No existe ningun producto con ese codigo");
        }

        productoRepo.deleteById(codigo);
    }

    @Override
    public Libro obtenerProducto(String codigo) throws Exception {
        return productoRepo.findById(codigo).orElseThrow(()-> new Exception("el codigo del producto no es valido") );
    }

    @Override
    public List<Libro> listarProductosPorCategoria(Categoria categoria) {
        return productoRepo.listarPorCategoria(categoria);
    }

    @Override
    public List<Libro> listarTodosProductos() throws Exception {
        return productoRepo.findAll();
    }

    @Override
    public void comentarProducto(String mensaje, Usuario usuario, Libro libro) throws Exception {
        LocalDate ld = LocalDate.now();
        Comentario comentario=new Comentario("32",mensaje,ld);
        comentario.setMiUsuario(usuario);
        comentario.setMiLibro(libro);
        try {
            comentarioRepo.save(comentario);
        }catch (Exception e ){
            e.getMessage();
        }





    }

    @Override
    public void guardarProductoEnFavoritos(Libro libro, Usuario usuario) throws Exception {

        if (libro ==null || usuario==null){
            throw new Exception("el producto o el usuario son nulos ");
        }

        libro.getUsuariosFavoritos().add(usuario);
        usuario.getProductosFavoritos().add(libro);

        productoRepo.save(libro);
        usuarioRepo.save(usuario);

    }

    @Override
    public void eliminarProductofavorito(Libro libro, Usuario usuario) throws Exception {
        if (libro ==null || usuario==null){
            throw new Exception("el producto o el usuario son nulos ");
        }

        libro.getUsuariosFavoritos().remove(usuario);
        usuario.getProductosFavoritos().remove(libro);

        productoRepo.save(libro);
        usuarioRepo.save(usuario);

    }

    @Override
    public void comprarProductos(DetalleCompra detalleCompra, Libro libro) {

    }

    @Override
    public List<Libro> buscarProductoPorNombre(String nombre, String[] producto) {

        return productoRepo.buscarProductoNombre(nombre);
    }

    @Override
    public List<Libro> buscarProducto(String nombre, String[] producto) {
        return null;
    }

    @Override
    public List<Libro> listarProductos(String codigoUsuario) throws Exception {
        return null;
    }

    @Override
    public List<Libro> obtenerProductosPorCategoria(String categoriaSeleccionada) {
        return productoRepo.obtenerProductosPorCategoria(categoriaSeleccionada);
    }
}
