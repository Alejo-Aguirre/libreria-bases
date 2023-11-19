package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.entidades.Comentario;
import co.edu.uniquindio.proyecto.entidades.Libro;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario,String> {

//se definen todas las consultas para acceder a los datos

//-----------------------------------Consultas propias-------------------------------------
    //se pueden contruir consultas propias mediante el lenguaje JPQL como la siguiente
    @Query("select u from Usuario u where u.nombre = :nombre")
    List<Usuario> obtenerUsuariosPorNombre(String nombre);



    //seleccionar productos p de un usuario u combina las filas de para poder ver los productos


    @Query("select c from Usuario u join u.misComentarios c where u.codigo =:codigo")
    List<Comentario> obtenerComentarioDeUsuarioPorCodigo(String codigo);





    //---------------------------------Consultas inferidas por SpringBoot----------------------------------------


    //con inferencia de datos gracias a el framework de spring que es JPArepository
    List<Usuario>findAllByNombreContains(String nombre);
    List<Usuario>findAllByTelefono(String telefono);

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmailAndCodigo(String email,String password);

    Optional<Usuario> findByUsernameAndPassword(String username,String password);


    Optional<Usuario> findByNombre(String nombre);

    Page<Usuario> findAllBy(Pageable paginador);


    @Query("select u from Usuario u where u.codigo = :codigo")
    Usuario obtenerUsuarioPorCodigo(String codigo);

    @Query(value = "SELECT MAX(codigo) FROM Usuario", nativeQuery = true)
    Integer obtenerUltimoNumero();


}
