package com.api.wslaboratorio.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
        (
                name = "tc_paciente"
        )
public class PacienteEntity {
    @Id
    @SequenceGenerator
            (
                    name = "paciente_sec",
                    sequenceName = "paciente_sec",
                    allocationSize = 1
            )
    @GeneratedValue
            (
                    generator = "paciente_sec",
                    strategy = GenerationType.SEQUENCE
            )
    @Column(name = "pacienteid")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private Long pacienteId;

    @Column(name = "dni")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private String dni;

    @Column(name = "primer_nombre")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private String primerNombre;

    @Column(name = "segundo_nombre")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private String segundoNombre;

    @Column(name = "tercer_nombre")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private String tercerNombre;

    @Column(name = "primer_apellido")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private String primerApellido;

    @Column(name = "segundo_apellido")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private String segundoApellido;

    @Column(name = "direccion")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private String direccion;

    @Column(name = "ciudad")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private String ciudad;

    @Column(name = "telefono")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private String telefono;

    @Embedded
    private AuditoriaEntity auditoriaEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generoid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private GeneroEntity generoEntity;

    @OneToOne(mappedBy = "pacienteEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UsuarioEntity usuarioEntity;

    @OneToMany(mappedBy = "pacienteEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<CitaEntity> citaEntities = new HashSet<>();

    @OneToMany(mappedBy = "pacienteEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<NotificacionEntity> notificacionEntities = new HashSet<>();

    @PrePersist
    public void prePersist() {
        this.auditoriaEntity.setFechaCreacion(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        this.auditoriaEntity.setFechaModificacion(new Date());
    }

}
