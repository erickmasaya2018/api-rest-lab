package com.api.wslaboratorio.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Column(name = "dni", nullable = false, length = 20)
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private String dni;

    @Column(name = "fecha_nacimiento", nullable = false)
    @Comment("CAMPO PARA ALMACENAR LA FECHA DE NACIMIENTO DEL EMPLEADO.")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date fechaNacimiento;

    @Column(name = "primer_nombre", nullable = false, length = 20)
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private String primerNombre;

    @Column(name = "segundo_nombre", nullable = true, length = 20)
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private String segundoNombre;

    @Column(name = "tercer_nombre", nullable = true, length = 20)
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private String tercerNombre;

    @Column(name = "primer_apellido", nullable = false, length = 20)
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private String primerApellido;

    @Column(name = "segundo_apellido", nullable = true, length = 20)
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private String segundoApellido;

    @Column(name = "direccion", nullable = false, length = 150)
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private String direccion;

    @Column(name = "ciudad", nullable = false, length = 20)
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private String ciudad;

    @Column(name = "estado_provincia", nullable = false, length = 20)
    @Comment("CAMPO PARA ALMACENAR EL ESTADO PROVINCIA DONDE RESIDE ACTUALMENTE EL EMPLEADO.")
    private String estadoprovincia;

    @Column(name = "telefono", nullable = true, length = 20)
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private String telefono;

    @Embedded
    private AuditoriaEntity auditoriaEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generoid", referencedColumnName = "generoid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private GeneroEntity generoEntity;

    @OneToOne(mappedBy = "pacienteEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UsuarioEntity usuarioEntity;

    @OneToMany(mappedBy = "pacienteEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CitaEntity> citaEntities = new ArrayList<>();

    @OneToMany(mappedBy = "pacienteEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NotificacionEntity> notificacionEntities = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.auditoriaEntity.setFechaCreacion(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        this.auditoriaEntity.setFechaModificacion(new Date());
    }

}
