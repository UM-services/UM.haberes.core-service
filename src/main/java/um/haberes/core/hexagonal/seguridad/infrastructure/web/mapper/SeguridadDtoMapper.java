package um.haberes.core.hexagonal.seguridad.infrastructure.web.mapper;

import org.springframework.stereotype.Component;
import um.haberes.core.hexagonal.seguridad.domain.model.Permiso;
import um.haberes.core.hexagonal.seguridad.domain.model.PermisoEfectivo;
import um.haberes.core.hexagonal.seguridad.domain.model.Rol;
import um.haberes.core.hexagonal.seguridad.domain.model.RolPermiso;
import um.haberes.core.hexagonal.seguridad.domain.model.SeguridadConstantes;
import um.haberes.core.hexagonal.seguridad.domain.model.SyncResultado;
import um.haberes.core.hexagonal.seguridad.domain.model.UsuarioPermiso;
import um.haberes.core.hexagonal.seguridad.domain.model.UsuarioRol;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.PermisoEfectivoResponse;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.PermisoRequest;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.PermisoResponse;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.RolPermisoResponse;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.RolRequest;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.RolResponse;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.SyncResponse;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.UsuarioPermisoResponse;
import um.haberes.core.hexagonal.seguridad.infrastructure.web.dto.UsuarioRolResponse;

@Component
public class SeguridadDtoMapper {

    public Permiso toDomain(PermisoRequest request) {
        if (request == null) {
            return null;
        }
        return Permiso.builder()
                .clave(request.getClave())
                .descripcion(request.getDescripcion())
                .modulo(request.getModulo())
                .aplicacion(request.getAplicacion())
                .activo(request.getActivo())
                .build();
    }

    public PermisoResponse toResponse(Permiso permiso) {
        return PermisoResponse.builder()
                .permisoId(permiso.getPermisoId())
                .clave(permiso.getClave())
                .descripcion(permiso.getDescripcion())
                .modulo(permiso.getModulo())
                .aplicacion(permiso.getAplicacion())
                .activo(permiso.getActivo())
                .build();
    }

    public Rol toDomain(RolRequest request) {
        if (request == null) {
            return null;
        }
        return Rol.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .aplicacion(request.getAplicacion())
                .activo(request.getActivo())
                .build();
    }

    public RolResponse toResponse(Rol rol) {
        return RolResponse.builder()
                .rolId(rol.getRolId())
                .nombre(rol.getNombre())
                .descripcion(rol.getDescripcion())
                .aplicacion(rol.getAplicacion())
                .activo(rol.getActivo())
                .protegido(SeguridadConstantes.ROL_ADMIN.equalsIgnoreCase(rol.getNombre()))
                .build();
    }

    public RolPermisoResponse toResponse(RolPermiso rolPermiso) {
        return RolPermisoResponse.builder()
                .rolPermisoId(rolPermiso.getRolPermisoId())
                .rolId(rolPermiso.getRolId())
                .permisoId(rolPermiso.getPermisoId())
                .build();
    }

    public UsuarioRolResponse toResponse(UsuarioRol usuarioRol) {
        return UsuarioRolResponse.builder()
                .usuarioRolId(usuarioRol.getUsuarioRolId())
                .legajoId(usuarioRol.getLegajoId())
                .rolId(usuarioRol.getRolId())
                .build();
    }

    public UsuarioPermisoResponse toResponse(UsuarioPermiso usuarioPermiso) {
        return UsuarioPermisoResponse.builder()
                .usuarioPermisoId(usuarioPermiso.getUsuarioPermisoId())
                .legajoId(usuarioPermiso.getLegajoId())
                .permisoId(usuarioPermiso.getPermisoId())
                .otorgado(usuarioPermiso.getOtorgado())
                .build();
    }

    public PermisoEfectivoResponse toResponse(PermisoEfectivo permisoEfectivo) {
        return PermisoEfectivoResponse.builder()
                .legajoId(permisoEfectivo.getLegajoId())
                .permisos(permisoEfectivo.getPermisos())
                .administrador(permisoEfectivo.isAdministrador())
                .build();
    }

    public SyncResponse toResponse(SyncResultado resultado) {
        return SyncResponse.builder()
                .creados(resultado.getCreados())
                .actualizados(resultado.getActualizados())
                .retirados(resultado.getRetirados())
                .asignacionesAgregadas(resultado.getAsignacionesAgregadas())
                .asignacionesQuitadas(resultado.getAsignacionesQuitadas())
                .warnings(resultado.getWarnings())
                .totalCambios(resultado.totalCambios())
                .build();
    }
}
