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
                name = "tt_carrito"
        )
public class CarritoEntity {
    @Id
    @SequenceGenerator
            (
                    name = "carrito_sec",
                    sequenceName = "carrito_sec",
                    allocationSize = 1
            )
    @GeneratedValue
            (
                    generator = "carrito_sec",
                    strategy = GenerationType.SEQUENCE
            )
    @Column(name = "carritoid")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private Integer carritoId;

    @Embedded
    private AuditoriaEntity auditoriaEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analisisid", referencedColumnName = "analisisid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private AnalisisEntity analisisEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citaid", referencedColumnName = "citaid")
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
