package com.api.wslaboratorio.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
        (
                name = "tt_resultado"
        )
public class ResultadoEntity {
    @Id
    @SequenceGenerator
            (
                    name = "resultado_sec",
                    sequenceName = "resultado_sec",
                    allocationSize = 1
            )
    @GeneratedValue
            (
                    generator = "resultado_sec",
                    strategy = GenerationType.SEQUENCE
            )
    @Column(name = "resultadoid")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private Integer resultadoId;

    @Column(name = "prosa_resultado", nullable = false, length = 30)
    @Comment("CAMPO QUE ALMACENA EL RESULTADO DEL ANALISIS REALIZADO AL PACIENTE SEGÚN LA CITA.")
    private String prosaResultado;

    @Embedded
    private AuditoriaEntity auditoriaEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analisisid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private AnalisisEntity analisisEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citaid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private CitaEntity citaEntity;

    @PrePersist
    public void prePersist() {
        this.auditoriaEntity.setFechaCreacion(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        this.auditoriaEntity.setFechaModificacion(new Date());
    }

}
