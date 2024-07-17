package com.api.wslaboratorio.util;

import java.util.Arrays;
import java.util.List;


public enum Rol {
    EMPLEADO(Arrays.asList(
            PermisosRol.LEER_TODOS_USUARIOS
    )),
    ADMISTRADOR(Arrays.asList(
            PermisosRol.LEER_TODOS_USUARIOS,
            PermisosRol.LEER_UN_USUARIO,
            PermisosRol.CREAR_UN_USUARIO,
            PermisosRol.MODIFICAR_UN_USUARIO,
            PermisosRol.ELIMINAR_UN_USUARIO
    )),
    ADMISTRADOR_LABORATORIO(Arrays.asList(
            PermisosRol.LEER_TODOS_USUARIOS,
            PermisosRol.LEER_UN_USUARIO,
            PermisosRol.CREAR_UN_USUARIO,
            PermisosRol.MODIFICAR_UN_USUARIO,
            PermisosRol.ELIMINAR_UN_USUARIO
    ));

    private List<PermisosRol> permisos;

    public List<PermisosRol> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<PermisosRol> permisos) {
        this.permisos = permisos;
    }

    Rol(List<PermisosRol> permisos) {
        this.permisos = permisos;
    }
}





