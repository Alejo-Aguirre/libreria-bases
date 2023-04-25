package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.entidades.Compra;
import co.edu.uniquindio.proyecto.entidades.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraRepo extends JpaRepository<Compra,String> {
    //consulta que dado el codigo de una ciudad retorne la lista de los detalles de esa compra

    @Query("select d from Compra c INNER JOIN c.misDetalleCompras d where c.codigo = :codigo  ")
    List<DetalleCompra> detallesCompraPorCodigoDeCompra(String codigo);

    @Query("SELECT c.miUsuario.codigo, COUNT(c) FROM Compra c GROUP BY c.miUsuario.codigo ORDER BY COUNT(c) DESC")
    List<Object[]> listarUsuarioConMasCompras();


    @Query("select c from Compra c where c.codigo = :codigo")
    Compra obtenerCompraPorCodigo(String codigo);
}
