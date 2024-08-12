package com.api.wslaboratorio.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
        (
                name = "tt_horario_laboratorio"
        )
public class HorarioLaborarioEntity {
    @Id
    @SequenceGenerator
            (
                    name = "horario_laboratorio_sec",
                    sequenceName = "horario_laboratorio_sec",
                    allocationSize = 1
            )
    @GeneratedValue
            (
                    generator = "horario_laboratorio_sec",
                    strategy = GenerationType.SEQUENCE
            )
    @Column(name = "horarioLaboratorioid")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private Long horarioLaboratorioId;

    @Column(name = "dia_semana", nullable = false, length = 20)
    @Comment("DÍA DE LA SEMANA.")
    private String diaSemana;

    @Column(name = "abierto", nullable = false)
    @Comment("VALIDAR SI ESTA ABIERTO.")
    private int abierto;

    @Column(name = "hora_abre", nullable = false)
    @Temporal(TemporalType.TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaAbre;

    @Column(name = "hora_cierre", nullable = false)
    @Temporal(TemporalType.TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaCierra;

    @Embedded
    private AuditoriaEntity auditoriaEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laboratorioid", referencedColumnName = "laboratorioid")
    private LaboratorioEntity laboratorioEntity;

    @PrePersist
    public void prePersist() {
        this.auditoriaEntity.setFechaCreacion(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        this.auditoriaEntity.setFechaModificacion(new Date());
    }
}
