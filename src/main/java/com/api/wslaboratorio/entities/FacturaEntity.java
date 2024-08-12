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
                name = "tt_factura"
        )
public class FacturaEntity {
    @Id
    @SequenceGenerator
            (
                    name = "factura_sec",
                    sequenceName = "factura_sec",
                    allocationSize = 1
            )
    @GeneratedValue
            (
                    generator = "factura_sec",
                    strategy = GenerationType.SEQUENCE
            )
    @Column(name = "facturaid")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private Long facturaId;

    @Column(name = "monto", nullable = false)
    @Comment("MONTO A PAGAR POR LOS ANALISIS ELEGIDOS POR EL PACIENTE.")
    private Double monto;

    @Column(name = "fecha_pago", nullable = false)
    @Comment("FECHA EN QUE SE PAGO LA FACTURA POR EL PACIENTE.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPago;

    @Embedded
    private AuditoriaEntity auditoriaEntity;

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
