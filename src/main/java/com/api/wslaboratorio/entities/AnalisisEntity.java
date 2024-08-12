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
                name = "tc_analisis"
        )
public class AnalisisEntity {
    @Id
    @SequenceGenerator
            (
                    name = "analisis_sec",
                    sequenceName = "analisis_sec",
                    allocationSize = 1
            )
    @GeneratedValue
            (
                    generator = "analisis_sec",
                    strategy = GenerationType.SEQUENCE
            )
    @Column(name = "analisisid")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private Long analisisId;

    @Column(name = "nombre", nullable = false, length = 100)
    @Comment("CAMPO QUE ALMACENA EL NOMBRE DEL ANALISIS QUE FORMA PARTE DE LA CARTERA DE ESTUDIOS QUE OFRECE EL LABORATORIO CLINICO.")
    private String nombre;

    @Column(name = "precio", nullable = false)
    @Comment("CAMPO PARA ALMACENAR EL PRECIO DEL ANALISIS.")
    private Double precio;

    @Column(name = "minimo", nullable = false)
    @Comment("CAMPO PARA ALMACENAR EL VALOR MINIMO QUE SE MARCAR COMO CORRECTO DEL ANALISIS.")
    private Double minimo;

    @Column(name = "maximo", nullable = false)
    @Comment("CAMPO PARA ALMACENAR EL VALOR MAXIMO QUE SE MARCAR COMO CORRECTO DEL ANALISIS.")
    private Double maximo;

    @Column(name = "descripcion", nullable = false)
    @Comment("CAMPO PARA ALMACENAR LA PROSA QUE DESCRIBE LAS CARACTERISTICAS DEL ANALISIS.")
    private String descripcion;

    @Embedded
    private AuditoriaEntity auditoriaEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "unidadid", referencedColumnName = "unidadid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UnidadEntity unidadEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "grupoid", referencedColumnName = "grupoid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private GrupoEntity grupoEntity;

    @OneToMany(mappedBy = "analisisEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ResultadoEntity> resultadoEntities = new ArrayList<>();

    @OneToMany(mappedBy = "analisisEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CarritoEntity> carritoEntities = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.auditoriaEntity.setFechaCreacion(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        this.auditoriaEntity.setFechaModificacion(new Date());
    }

}
