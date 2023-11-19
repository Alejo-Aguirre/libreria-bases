package co.edu.uniquindio.proyecto.entidades;

import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Moderador extends Persona implements Serializable {

    @OneToMany(mappedBy ="miModerador")
   private List<LibroModerador> misProductosModerador;

    public Moderador(String codigo, @Length(max = 150) String cedula, @Length(max = 150) String nombre, @Email String email, String username, String password) {
        super(codigo, cedula, nombre, email, username, password);
    }

    //se debe crear la relación entre moderador y producto
}
