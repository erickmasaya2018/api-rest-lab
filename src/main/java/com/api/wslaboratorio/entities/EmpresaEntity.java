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
                name = "tc_empresa",
                uniqueConstraints = @UniqueConstraint
                        (
                                name = "email_unique",
                                columnNames = "email"
                        )
        )
public class EmpresaEntity {
    @Id
    @SequenceGenerator
            (
                    name = "empresa_sec",
                    sequenceName = "empresa_sec",
                    allocationSize = 1
            )
    @GeneratedValue
            (
                    generator = "empresa_sec",
                    strategy = GenerationType.SEQUENCE
            )
    @Column(name = "empresaid")
    @Comment("ID AUTOGENERADO PARA LOGRAR LA ATOMICIDAD DEL REGISTRO.")
    private Long empresaId;

    @Column(name = "nombre", nullable = false, length = 30)
    @Comment("CAMPO PARA ALMACENAR EL NOMBRE DONDE SE ENCUENTRA ESTABLECIDA LA EMPRESA SEGUN EL REGISTRO DE CONSTITUCION.")
    private String nombre;

    @Column(name = "direccion", nullable = false, length = 150)
    @Comment("CAMPO PARA ALMACENAR LA DIRECCION DONDE SE ENCUENTRA ESTABLECIDA LA EMPRESA SEGÚN EL REGISTRO DE CONSTITUCION.")
    private String direccion;

    @Column(name = "ciudad", nullable = false, length = 30)
    @Comment("CAMPO PARA ALMACENAR LA CIUDAD DONDE SE ENCUENTRA ESTABLECIDA LA EMPRESA SEGUN EL REGISTRO DE CONSTITUCION.")
    private String ciudad;

    @Column(name = "estado_provincia", nullable = false, length = 30)
    @Comment("CAMPO PARA ALMACENAR EL ESTADO PROVINCIA DONDE SE ENCUENTRA ESTABLECIDA LA EMPRESA SEGUN EL REGISTRO DE CONSTITUCION.")
    private String estadoProvincia;

    @Column(name = "pais", nullable = false, length = 30)
    @Comment("CAMPO PARA ALMACENAR LA CIUDAD DONDE SE ENCUENTRA ESTABLECIDA LA EMPRESA SEGUN EL REGISTRO DE CONSTITUCION.")
    private String pais;

    @Column(name = "codigo_postal", nullable = true, length = 30)
    @Comment("CAMPO PARA ALMACENAR EL CODIGO POSTAL DONDE SE ENCUENTRA ESTABLECIDA LA EMPRESA SEGUN EL REGISTRO DE CONSTITUCION.")
    private String codigoPostal;

    @Column(name = "telefono", nullable = true, length = 30)
    @Comment("CAMPO PARA ALMACENAR EL TELEFONO DE LA EMPRESA SEGUN EL REGISTRO DE CONSTITUCION.")
    private String telefono;

    @Column(name = "email", nullable = true, length = 30)
    @Comment("CAMPO PARA ALMACENAR EL EMAIL DE LA EMPRESA SEGÚN EL REGISTRO DE CONSTITUCION.")
    private String email;

    @Column(name = "sitio_web", nullable = true, length = 30)
    @Comment("CAMPO PARA ALMACENAR EL SITIO WEB SI LA EMPRESA CUENTA CON PRESENCIA EN INTERNET A TRAVES DE ALGÚN SITIO.")
    private String sitioWeb;

    @Embedded
    private AuditoriaEntity auditoriaEntity;

    @OneToMany(mappedBy = "empresaEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<LaboratorioEntity> laboratorioEntities = new HashSet<>();

    @OneToMany(mappedBy = "empresaEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<EmpleadoEntity> empleadoEntities = new HashSet<>();

    @PrePersist
    public void prePersist() {
        this.auditoriaEntity.setFechaCreacion(new Date());
    }

    @PreUpdate
    public void preUpdate() {
        this.auditoriaEntity.setFechaModificacion(new Date());
    }
}
