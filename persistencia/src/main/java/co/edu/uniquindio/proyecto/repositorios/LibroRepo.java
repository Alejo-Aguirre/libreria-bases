package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.entidades.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepo extends JpaRepository<Libro,String>{

    @Query("select p.miUsuario.nombre from Libro p where p.codigo = :codigo")
    String obtenerNombreDelVendedor(String codigo);


    //left join nos muestra la relacion de el usuario y los comentario para saber que usuario tiene comentario o que ususario tiene los comentarios en null
    @Query("select p.nombre,c.mensaje from Libro p left join p.miComentario c")
    List<Object[]> listarUsuariosYProductos();


    //usuarios que han comentado un producto especifico
    //de un producto se trae la informacion del usuario pero del usuario necesita

    @Query("select p.miUsuario from Libro p  join p.miComentario c where p.codigo = :codigo ")
    List<Usuario> listarUsuariosQueComentaronProducto(String codigo);


    @Query("SELECT p FROM Libro p WHERE :categoria = p.miCategoria")
    List<Libro> listarPorCategoria(@Param("categoria") Categoria categoria);


    @Query("SELECT p.codigo, p.nombre, COUNT(c.mensaje) FROM Libro p LEFT JOIN p.miComentario c GROUP BY p.codigo, p.nombre ORDER BY COUNT(c.mensaje) DESC")
    List<Object[]> listarProductosConMasComentarios();


    @Query("select p.miUsuario from Libro p where p.codigo = :codigo")
    Optional<Usuario> obtenerPropietarioProducto(String codigo);

    @Query(value = "SELECT p.codigo, p.nombre, COUNT(f.codigo) AS cantidadFavoritos " +
            "FROM Libro p " +
            "LEFT JOIN p.usuariosFavoritos f " +
            "GROUP BY p.codigo, p.nombre " +
            "ORDER BY cantidadFavoritos DESC")
    List<Object[]> listarProductosMasAgregadosFavoritos();

    @Query("select u from Libro  p, IN (p.usuariosFavoritos) u where p.codigo = :codigo")
    List<Usuario> obtenerUsuariosFavoritosPorCodigo(String codigo);

    @Query("select p from Libro p where p.nombre like concat('%',:nombre,'%')")
    List<Libro> buscarProductoNombre(String nombre);


    @Query("SELECT p FROM Libro p WHERE p.miCategoria.nombre = :categoria")
    List<Libro> obtenerProductosPorCategoria(@Param("categoria") String categoria);






}
