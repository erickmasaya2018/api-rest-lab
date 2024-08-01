package com.api.wslaboratorio.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
        (
                name = "tc_unidad"

        )
public class UnidadEntity {

    @Id
    @SequenceGenerator
            (
                    name = "unidad_sec",
                    sequenceName = "unidad_sec",
                    allocationSize = 1
            )
    @GeneratedValue
            (
                    generator = "unidad_sec",
                    strategy = GenerationType.SEQUENCE
            )
    @Column(name = "unidadid")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private Long unidadId;

    @Column(name = "nombre", nullable = false, length = 30)
    @Comment("CAMPO PARA ALMACENAR EL NOMBRE DE LA UNIDAD.")
    private String nombre;

    @Column(name = "observacion", nullable = true, length = 150)
    @Comment("CAMPO PARA ALMACENAR LA OBSERVACIÓN DE LA UNIDAD.")
    private String observacion;

    @Embedded
    private AuditoriaEntity auditoriaEntity;

    @OneToMany(mappedBy = "unidadEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AnalisisEntity> analisisEntities = new HashSet<>();

    @PrePersist
    public void prePersist() {
        this.auditoriaEntity.setFechaCreacion(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        this.auditoriaEntity.setFechaModificacion(new Date());
    }
}
