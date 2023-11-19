package co.edu.uniquindio.proyecto.repositorios;

import ch.qos.logback.core.util.Loader;
import co.edu.uniquindio.proyecto.entidades.Moderador;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ModeradorRepo extends JpaRepository<Moderador,String> {
    List<Moderador> findAllByNombreContains(String nombre);
    Optional<Moderador> findByEmail(String email);
    Optional<Moderador> findByNombre(String nombre);
    Optional<Moderador> findByEmailAndCodigo(String email, String codigo);
    Optional<Moderador> findByNombreAndPassword(String username, String password);
    Page<Moderador> findAllBy(Pageable pageable);
    @Query("select m from Moderador m where m.codigo = :codigo")
    Moderador obtenerModeradorPorCodigo(String codigo);

    Optional<Moderador> findByUsernameAndPassword(String username, String password);
}
