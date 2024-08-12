package com.api.wslaboratorio.entities;

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
                name = "tc_estado"
        )
public class EstadoEntity {

    @Id
    @SequenceGenerator
            (
                    name = "estado_sec",
                    sequenceName = "estado_sec",
                    allocationSize = 1
            )
    @GeneratedValue
            (
                    generator = "estado_sec",
                    strategy = GenerationType.SEQUENCE
            )
    @Column(name = "estadoid")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private Long estadoId;

    @Column(name = "descripcion", nullable = false, length = 30)
    @Comment("CAMPO QUE ALMACENA LA DESCRIPCION DEL ITEM QUE ES PARTE DEL CATALOGO.")
    private String descripcion;

    @Embedded
    private AuditoriaEntity auditoriaEntity;

    @OneToMany(mappedBy = "estadoEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CitaEntity> citaEntities = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.auditoriaEntity.setFechaCreacion(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        this.auditoriaEntity.setFechaModificacion(new Date());
    }
}
