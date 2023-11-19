package co.edu.uniquindio.proyecto.entidades;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Usuario extends Persona implements Serializable {

    @Column(nullable = false,length = 150)
    private String direccion;

    @Column(length = 20)
    private String telefono;


     //entidad propietaria entre usuario producto
    @ManyToMany
    @ToString.Exclude
     private List<Libro> librosFavoritos;

     @ToString.Exclude
     @OneToMany(mappedBy = "miUsuario")
     private List<Comentario> misComentarios;


     @ToString.Exclude
     @OneToMany(mappedBy = "miUsuario")
     private List<Compra> misCompras;

    public Usuario(String codigo, @Length(max = 150) String cedula, @Length(max = 150) String nombre, @Email String email, String username, String password, String direccion, String telefono) {
        super(codigo, cedula, nombre, email, username, password);
        this.direccion = direccion;
        this.telefono = telefono;
    }


    //entidad inversa



}
