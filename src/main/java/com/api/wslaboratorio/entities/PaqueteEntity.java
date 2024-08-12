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
                name = "tc_paquete"
        )
public class PaqueteEntity {
    @Id
    @SequenceGenerator
            (
                    name = "paquete_sec",
                    sequenceName = "paquete_sec",


                    allocationSize = 1
            )
    @GeneratedValue
            (
                    generator = "paquete_sec",
                    strategy = GenerationType.SEQUENCE
            )
    @Column(name = "paqueteid")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private Long paqueteId;

    @Column(name = "nombre", nullable = false, length = 50)
    @Comment("CAMPO QUE ALMACENA EL NOMBRE DEL PAQUETE PARA CONFIGURAR LOS DESCUENTOS.")
    private String nombre;

    @Column(name = "observacion", nullable = true, length = 150)
    @Comment("CAMPO QUE ALMACENA LA OBSERVACIÓN DEL PAQUETE.")
    private String observacion;

    @Column(name = "porcentaje", nullable = false)
    @Comment("CAMPO PARA ALMACENAR EL PRECIO DEL ANALISIS.")
    private Double porcentaje;

    @Embedded
    private AuditoriaEntity auditoriaEntity;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "tc_paquete_grupo",
            joinColumns = @JoinColumn(name = "paqueteid", referencedColumnName = "paqueteid"),
            inverseJoinColumns = @JoinColumn(name = "grupoid", referencedColumnName = "grupoid")
    )
    private Set<GrupoEntity> grupos = new HashSet<>();
    @PrePersist
    public void prePersist() {
        this.auditoriaEntity.setFechaCreacion(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        this.auditoriaEntity.setFechaModificacion(new Date());
    }

}
