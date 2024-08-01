package com.api.wslaboratorio.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
        (
                name = "tc_empleado"
        )
public class EmpleadoEntity {
    @Id
    @SequenceGenerator
            (
                    name = "empleado_sec",
                    sequenceName = "empleado_sec",
                    allocationSize = 1
            )
    @GeneratedValue
            (
                    generator = "empleado_sec",
                    strategy = GenerationType.SEQUENCE
            )
    @Column(name = "empleadoid")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private long empleadoId;

    @Column(name = "dni", nullable = false, length = 20)
    @Comment("CAMPO QUE ALMACENA EL DNI (DOCUMENTO NACIONAL DE IDENTIDAD) DEL EMPLEADO QUE LABORA.")
    private String dni;

    @Column(name = "primer_nombre", nullable = false, length = 20)
    @Comment("SE ALMACENA EL PRIMER NOMBRE DEL EMPLEADO A COMO SALE EN SU REGISTRO DE CONTRATACION.")
    private String primerNombre;

    @Column(name = "segundo_nombre", nullable = true, length = 20)
    @Comment("SE ALMACENA EL SEGUNDO NOMBRE DEL EMPLEADO A COMO SALE EN SU REGISTRO DE CONTRATACION.")
    private String segundoNombre;

    @Column(name = "tercer_nombre", nullable = true, length = 20)
    @Comment("SE ALMACENA EL TERCER NOMBRE DEL EMPLEADO A COMO SALE EN SU REGISTRO DE CONTRATACION.")
    private String tercerNombre;

    @Column(name = "primer_apellido", nullable = false, length = 20)
    @Comment("SE ALMACENA EL PRIMER APELLIDO DEL EMPLEADO A COMO SALE EN SU REGISTRO DE CONTRATACION.")
    private String primerApellido;

    @Column(name = "segundo_apellido", nullable = true, length = 30)
    @Comment("SE ALMACENA EL SEGUNDO APELLIDO DEL EMPLEADO A COMO SALE EN SU REGISTRO DE CONTRATACION.")
    private String segundoApellido;

    @Column(name = "direccion", nullable = false, length = 150)
    @Comment("CAMPO PARA ALMACENAR LA DIRECCION DE RESIDENCIA ACTUAL DEL EMPLEADO.")
    private String direccion;

    @Column(name = "ciudad", nullable = false, length = 20)
    @Comment("CAMPO PARA ALMACENAR LA CIUDAD DONDE RESIDE ACTUALMENTE EL EMPLEADO.")
    private String ciudad;

    @Column(name = "estado_provincia", nullable = false, length = 20)
    @Comment("CAMPO PARA ALMACENAR EL ESTADO PROVINCIA DONDE RESIDE ACTUALMENTE EL EMPLEADO.")
    private String estadoprovincia;

    @Column(name = "fecha_nacimiento", nullable = false)
    @Comment("CAMPO PARA ALMACENAR LA FECHA DE NACIMIENTO DEL EMPLEADO.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;

    @Column(name = "telefono", nullable = true, length = 20)
    @Comment("CAMPO PARA ALMACENAR EL NUMERO DE TELEFONO DEL EMPLEADO.")
    private String telefono;

    @Embedded
    private AuditoriaEntity auditoriaEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresaid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private EmpresaEntity empresaEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generoid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private GeneroEntity generoEntity;

    @OneToOne(mappedBy = "empleadoEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UsuarioEntity usuarioEntity;

    @PrePersist
    public void prePersist() {
        this.auditoriaEntity.setFechaCreacion(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        this.auditoriaEntity.setFechaModificacion(new Date());
    }
}
