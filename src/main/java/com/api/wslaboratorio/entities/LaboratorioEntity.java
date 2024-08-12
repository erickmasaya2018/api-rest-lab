package com.api.wslaboratorio.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
        (
                name = "tc_laboratorio",
                uniqueConstraints = @UniqueConstraint
                        (
                                name = "email_unique",
                                columnNames = "email"
                        )
        )
public class LaboratorioEntity {

    @Id
    @SequenceGenerator
            (
                    name = "laboratorio_sec",
                    sequenceName = "laboratorio_sec",
                    allocationSize = 1
            )
    @GeneratedValue
            (
                    generator = "laboratorio_sec",
                    strategy = GenerationType.SEQUENCE
            )
    @Column(name = "laboratorioid")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private Long laboratorioId;

    @Column(name = "nombre", nullable = false, length = 30)
    @Comment("CAMPO PARA ALMACENAR EL NOMBRE QUE TIENE ACTUALMENTE EL LABORATORIO SEGUN SU ACTA O MATRICULA DE CONSTITUCION.")
    private String nombre;

    @Column(name = "direccion", nullable = false, length = 150)
    @Comment("CAMPO PARA ALMACENAR LA DIRECCION QUE TIENE ACTUALMENTE EL LABORATORIO SEGUN SU ACTA O MATRICULA DE CONSTITUCION.")
    private String direccion;

    @Column(name = "telefono", nullable = true, length = 20)
    @Comment("CAMPO PARA ALMACENAR EL TELEFONO QUE TIENE ACTUALMENTE EL LABORATORIO.")
    private String telefono;

    @Column(name = "email", nullable = true, length = 20)
    @Comment("CAMPO PARA ALMACENAR EL EMAIL DEL LABORATORIO.")
    private String email;

    @Embedded
    private AuditoriaEntity auditoriaEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresaid", referencedColumnName = "empresaid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private EmpresaEntity empresaEntity;

    @OneToMany(mappedBy = "laboratorioEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CitaEntity> citaEntities = new ArrayList<>();

    @OneToMany(mappedBy = "laboratorioEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HorarioLaborarioEntity> horarioLaborarioEntities = new ArrayList<>();

    @OneToMany(mappedBy = "laboratorioEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmpleadoEntity> empleadoEntities = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.auditoriaEntity.setFechaCreacion(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        this.auditoriaEntity.setFechaModificacion(new Date());
    }
}
