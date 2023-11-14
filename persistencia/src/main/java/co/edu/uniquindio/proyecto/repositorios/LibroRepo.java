package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.entidades.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepo extends JpaRepository<Libro,String>{


    //left join nos muestra la relacion de el usuario y los comentario para saber que usuario tiene comentario o que ususario tiene los comentarios en null
    @Query("select l.nombre,c.mensaje from Libro l left join l.miComentario c")
    List<Object[]> listarUsuariosYLibros();


    //usuarios que han comentado un producto especifico
    //de un producto se trae la informacion del usuario pero del usuario necesita

    @Query("SELECT l FROM Libro l WHERE :categoria = l.miCategoria")
    List<Libro> listarPorCategoria(@Param("categoria") Categoria categoria);


    @Query("SELECT l.codigo, l.nombre, COUNT(c.mensaje) FROM Libro l LEFT JOIN l.miComentario c GROUP BY l.codigo, l.nombre ORDER BY COUNT(c.mensaje) DESC")
    List<Object[]> listarLibrosConMasComentarios();

    @Query(value = "SELECT l.codigo, l.nombre, COUNT(f.codigo) AS cantidadFavoritos " +
            "FROM Libro l " +
            "LEFT JOIN l.usuariosFavoritos f " +
            "GROUP BY l.codigo, l.nombre " +
            "ORDER BY cantidadFavoritos DESC")
    List<Object[]> listarLibrosMasAgregadosFavoritos();

    @Query("select u from Libro  l, IN (l.usuariosFavoritos) u where l.codigo = :codigo")
    List<Usuario> obtenerUsuariosFavoritosPorCodigo(String codigo);

    @Query("select l from Libro l where l.nombre like concat('%',:nombre,'%')")
    List<Libro> buscarLibroNombre(String nombre);


    @Query("SELECT l FROM Libro l WHERE l.miCategoria.nombre = :categoria")
    List<Libro> obtenerLibrosPorCategoria(@Param("categoria") String categoria);






}
