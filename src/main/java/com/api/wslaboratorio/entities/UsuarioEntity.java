package com.api.wslaboratorio.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
        (
                name = "tc_usuario",
                uniqueConstraints = @UniqueConstraint
                        (
                                name = "email_unique",
                                columnNames = "email"
                        )
        )
public class UsuarioEntity implements UserDetails {
    @Id
    @SequenceGenerator
            (
                    name = "usuario_sec",
                    sequenceName = "usuario_sec",
                    allocationSize = 1
            )
    @GeneratedValue
            (
                    generator = "usuario_sec",
                    strategy = GenerationType.SEQUENCE
            )
    @Column(name = "usuarioid")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private Long usuarioId;

    @Column(name = "email", nullable = false, length = 30)
    @NotEmpty(message = "El campo email es requerido y no puede ser vació.")
    @Email(
            regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Proporcione un formato correcto de correo electrónico."
    )
    @Comment("CAMPO PARA ALMACENAR EL EMAIL DE LA EMPRESA SEGUN EL REGISTRO DE CONSTITUCION.")
    private String email;

    @NotEmpty(message = "El campo contraseña es requerido y no puede ser vació.")
    @Column(name = "contrasena", nullable = false, length = 100)
    @Comment("CAMPO QUE ALMACENA LA CONTRASEÑA CON LA QUE EL USUARIO PODRA REALIZAR LA VALIDACION DE INGRESO AL SISTEMA.")
    private String contrasena;

    @Column(name = "clave_secreta_contrasena", nullable = false, length = 100)
    @Comment("CAMPO QUE ALMACENA LA CLAVE SECRETA PARA DESENCRIPTAR EL CONTENIDO CIFRADO DEL LA CONTRASEÑA.")
    private String claveSecretaContrasena;

    @Column(name = "permiso", nullable = false, length = 10)
    @Comment("CAMPO PARA ALMACENAR EL TIPO DE PERMISO QUE TIENE EN LA APLICACION EL USUARIO.")
    private String permiso;

    @Embedded
    private AuditoriaEntity auditoriaEntity;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "empleadoid", referencedColumnName = "empleadoid")
    private EmpleadoEntity empleadoEntity;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pacienteid", referencedColumnName = "pacienteid")
    private PacienteEntity pacienteEntity;

    @PrePersist
    public void prePersist() {
        this.auditoriaEntity.setFechaCreacion(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        this.auditoriaEntity.setFechaModificacion(new Date());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
