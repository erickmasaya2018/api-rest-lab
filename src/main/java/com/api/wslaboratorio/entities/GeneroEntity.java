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
                name = "tc_genero"
        )
public class GeneroEntity {
    @Id
    @SequenceGenerator
            (
                    name = "genero_sec",
                    sequenceName = "genero_sec",
                    allocationSize = 1
            )
    @GeneratedValue
            (
                    generator = "genero_sec",
                    strategy = GenerationType.SEQUENCE
            )
    @Column(name = "generoid")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private Long generoId;

    @Column(name = "descripcion", nullable = false, length = 30)
    @Comment("CAMPO QUE ALMACENA LA DESCRIPCIÓN DEL ITEM QUE ES PARTE DEL CATÁLOGO.")
    private String descripcion;

    @Column(name = "abreviatura", nullable = true, length = 20)
    @Comment("CAMPO PARA ALMACENAR EL GENERO DEL PACIENTE.")
    private String abreviatura;

    @Embedded
    private AuditoriaEntity auditoriaEntity;

    @OneToMany(mappedBy = "generoEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<EmpleadoEntity> empleadoEntities = new HashSet<>();

    @OneToMany(mappedBy = "generoEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PacienteEntity> pacienteEntities = new HashSet<>();

    @PrePersist
    public void prePersist() {
        this.auditoriaEntity.setFechaCreacion(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        this.auditoriaEntity.setFechaModificacion(new Date());
    }


}
