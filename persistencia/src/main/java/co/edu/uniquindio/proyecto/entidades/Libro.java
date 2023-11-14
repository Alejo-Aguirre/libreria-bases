package co.edu.uniquindio.proyecto.entidades;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Producto implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String codigo;


    @NotBlank(message = "El nombre del producto es obligatorio")
    @Column(nullable = false,length = 100)
    private String nombre;

    @NotBlank
    @Column(nullable = false)
    private String descripcion;

    @Positive
    @Column(nullable = false)
    private float precio;

    @Column(nullable = false)
    private LocalDate fechaCreacion;

    @Future
    @Column(nullable = false)
    private LocalDate fechaLimite;

    @PositiveOrZero
    @Column(nullable = false)
    private Integer unidades;

    @ToString.Exclude
    @ManyToOne
    private Usuario miUsuario;
    //entidad propietaria

    @ElementCollection
    private List<String> imagenes;

    @ToString.Exclude
    @ManyToOne
    private Categoria miCategoria;



    //datos que se necesitan para crear un producto
    public Producto(String codigo, String nombre, String descripcion, float precio, LocalDate fechaCreacion, LocalDate fechaLimite, Integer unidades) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.fechaCreacion = fechaCreacion;
        this.fechaLimite = fechaLimite;
        this.unidades = unidades;
    }
    public String getImagenPrincipal(){
        if(imagenes != null && !imagenes.isEmpty()){
            return imagenes.get(0);
        }
        return  "default.png";
    }

    //entidad Propietaria es el producto porque para hacer un producto necesitamos una categoria

    @ToString.Exclude
    @OneToMany(mappedBy = "miProducto")
    private List<Comentario> miComentario;

    @ToString.Exclude
    @OneToMany(mappedBy = "miProducto")
    private List<DetalleCompra> misDetalleCompras;


    @ToString.Exclude
    @OneToMany(mappedBy = "miProducto")
    private List<DetalleDeProducto> misDetalleProductos;

    @ToString.Exclude
    @OneToMany(mappedBy = "miProducto")
    private List<ProductoModerador> misProductosModerador;


    //estos son los usuarios que tienen el producto en favoritos
    //entidad inversa entre usuario y producto
    @ToString.Exclude
    @ManyToMany(mappedBy = "productosFavoritos")
    private List<Usuario> usuariosFavoritos;

//se debe crear la relacion con el moderador

}
