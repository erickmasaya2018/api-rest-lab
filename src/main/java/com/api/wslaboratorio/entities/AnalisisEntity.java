package com.api.wslaboratorio.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Column(name = "nombre", nullable = false, length = 50)
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

    @Embedded
    private AuditoriaEntity auditoriaEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidadid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UnidadEntity unidadEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupoid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private GrupoEntity grupoEntity;

    @OneToMany(mappedBy = "analisisEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ResultadoEntity> resultadoEntities = new HashSet<>();

    @OneToMany(mappedBy = "analisisEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CarritoEntity> carritoEntities = new HashSet<>();

    @PrePersist
    public void prePersist() {
        this.auditoriaEntity.setFechaCreacion(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        this.auditoriaEntity.setFechaModificacion(new Date());
    }

}
