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
                name = "tt_cita"
        )
public class CitaEntity {
    @Id
    @SequenceGenerator
            (
                    name = "cita_sec",
                    sequenceName = "cita_sec",
                    allocationSize = 1
            )
    @GeneratedValue
            (
                    generator = "cita_sec",
                    strategy = GenerationType.SEQUENCE
            )
    @Column(name = "citaid")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private Long citaId;

    @Embedded
    private AuditoriaEntity auditoriaEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pacienteid", referencedColumnName = "pacienteid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private PacienteEntity pacienteEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laboratorioid", referencedColumnName = "laboratorioid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LaboratorioEntity laboratorioEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estadoid", referencedColumnName = "estadoid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private EstadoEntity estadoEntity;

    @OneToMany(mappedBy = "citaEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CarritoEntity> carritoEntities = new ArrayList<>();

    @OneToMany(mappedBy = "citaEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ResultadoEntity> resultadoEntities = new ArrayList<>();

    @OneToMany(mappedBy = "citaEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FacturaEntity> facturaEntities = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.auditoriaEntity.setFechaCreacion(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        this.auditoriaEntity.setFechaModificacion(new Date());
    }
}
