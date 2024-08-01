package com.api.wslaboratorio.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "El campo nombre de usuario es requerido y no puede ser nulo.")
    @NotBlank(message = "El campo npoombre de usuario es requerido y no puede ser vació.")
    @Column(name = "nombre_usuario", nullable = false, length = 100)
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private String nombreUsuario;

    @Column(name = "email", nullable = true, length = 30)
    @Email()
    @Comment("CAMPO PARA ALMACENAR EL EMAIL DE LA EMPRESA SEGUN EL REGISTRO DE CONSTITUCION.")
    private String email;

    @NotNull(message = "El campo contraseña es requerido y no puede ser nulo.")
    @NotBlank(message = "El campo contraseña es requerido y no puede ser vació.")
    @Column(name = "contrasena", nullable = false, length = 100)
    @Comment("CAMPO QUE ALMACENA LA CONTRASEÑA CON LA QUE EL USUARIO PODRA REALIZAR LA VALIDACION DE INGRESO AL SISTEMA.")
    private String contrasena;

    @Column(name = "clave_secreta_contrasena", nullable = false, length = 100)
    @Comment("CAMPO QUE ALMACENA LA CLAVE SECRETA PARA DESENCRIPTAR EL CONTENIDO CIFRADO DEL LA CONTRASEÑA.")
    private String claveSecretaContrasena;

    @Embedded
    private AuditoriaEntity auditoriaEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleadoid")
    private EmpleadoEntity empleadoEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pacienteid")
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
        return nombreUsuario;
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
