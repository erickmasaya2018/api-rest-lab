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
                name = "tc_grupo"
        )
public class GrupoEntity {

    @Id
    @SequenceGenerator
            (
                    name = "grupo_sec",
                    sequenceName = "grupo_sec",
                    allocationSize = 1
            )
    @GeneratedValue
            (
                    generator = "grupo_sec",
                    strategy = GenerationType.SEQUENCE
            )
    @Column(name = "grupoid")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private Long grupoId;

    @Column(name = "nombre", nullable = false, length = 30)
    @Comment("CAMPO PARA ALMACENAR EL NOMBRE DEL GRUPO AL QUE VA A PERTENECER EL ANÁLISIS.")
    private String nombre;

    @Column(name = "observacion", nullable = true, length = 150)
    @Comment("CAMPO PARA ALMACENAR LA OBSERVACIÓN RESPECTO AL GRUPO QUE SE ESTA GENERANDO.")
    private String observacion;

    @Embedded
    private AuditoriaEntity auditoriaEntity;

    @OneToMany(mappedBy = "grupoEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AnalisisEntity> analisisEntities = new ArrayList<>();

    @ManyToMany(mappedBy = "grupos", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaqueteEntity> paquetes = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.auditoriaEntity.setFechaCreacion(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        this.auditoriaEntity.setFechaModificacion(new Date());
    }
}
