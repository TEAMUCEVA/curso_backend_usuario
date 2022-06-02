package co.edu.uceva.ejemplo01.model.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="usuarios")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message ="no puede estar vacio")
    @Size(min=2, max=20, message="el tamaño tiene que estar entre 2 y 20")
    @Column(nullable=false)
    private String nombre;

    @NotEmpty(message ="no puede estar vacio")
    private String apellido;

    @NotEmpty(message ="no puede estar vacio")
    @Email(message="no es una dirección de correo bien formada")
    @Column(nullable=false, unique=true)
    private String email;

    @Column(name="fecha")
    @Temporal(TemporalType.DATE)
    private Date creadoEn;

    @PrePersist
    public void prePersist() {
        creadoEn = new Date();
    }

    private static final Long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(Date creadoEn) {
        this.creadoEn = creadoEn;
    }
}
