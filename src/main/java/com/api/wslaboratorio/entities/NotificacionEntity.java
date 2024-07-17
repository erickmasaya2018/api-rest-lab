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
                name = "tt_notificacion"
        )
public class NotificacionEntity {

    @Id
    @SequenceGenerator
            (
                    name = "notificacion_sec",
                    sequenceName = "notificacion_sec",
                    allocationSize = 1
            )
    @GeneratedValue
            (
                    generator = "notificacion_sec",
                    strategy = GenerationType.SEQUENCE
            )
    @Column(name = "notificacionid")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private Long notificacionId;

    @Column(name = "mensaje", nullable = false, length = 100)
    @Comment("CAMPO PARA GUARDAR EL MENSAJE QUE SE ENVIARA AL PACIENTE COMO PARTE DEL SEGUIMIENTO DEL ESTADO DE LA ENTREGA  DEL RESULTADO DE SUS ANALISIS.")
    private String mensaje;

    @Embedded
    private AuditoriaEntity auditoriaEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pacienteid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private PacienteEntity pacienteEntity;

    @PrePersist
    public void prePersist() {
        this.auditoriaEntity.setFechaCreacion(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        this.auditoriaEntity.setFechaModificacion(new Date());
    }
}
