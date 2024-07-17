package com.api.wslaboratorio.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class AuditoriaEntity {
    @Column(name = "usuario_creacion", nullable = false, length = 20)
    @Comment("CAMPO DE AUDITORIA PARA QUE ALMACENA EL NOMBRE DEL USUARIO QUE INTERACTUA CON EL REGISTRO.")
    @OrderColumn()
    private String usuarioCreacion;

    @Column(name = "fecha_creacion", nullable = false)
    @Comment("CAMPO DE AUDITORIA PARA ALMACENAR LA FECHA EN LA QUE SE CREA EL REGISTRO EN LA TABLA.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Column(name = "usuario_modificacion", nullable = true, length = 20)
    @Comment("CAMPO DE AUDITORIA PARA QUE ALMACENA EL ULTIMO NOMBRE DE USUARIO QUE REALICE MODIFICACION AL REGISTRO.")
    private String usuarioModificacion;

    @Column(name = "fecha_modificacion", nullable = true)
    @Comment("CAMPO DE AUDITORIA QUE ALMACENA LA FECHA DEL ULTIMO CAMBIO QUE SE HA REALIZADO AL REGISTRO.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;

    @Column(name = "registro_activo", nullable = true, columnDefinition = "int default 1")
    @Comment("CAMPO PARA REGISTRAR EL ELIMINADO LOGICO DE LOS REGISTROS PARA QUE PODER DESARROLLAR LA TRAZABILIDAD DEL MISMO.")
    private int registroActivo;
}
