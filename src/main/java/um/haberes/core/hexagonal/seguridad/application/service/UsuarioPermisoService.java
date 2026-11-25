package um.haberes.core.hexagonal.seguridad.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import um.haberes.core.hexagonal.seguridad.application.exception.SeguridadException;
import um.haberes.core.hexagonal.seguridad.application.exception.SeguridadNotFoundException;
import um.haberes.core.hexagonal.seguridad.domain.model.UsuarioPermiso;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.PermisoRepository;
import um.haberes.core.hexagonal.seguridad.domain.ports.out.UsuarioPermisoRepository;
import um.haberes.core.repository.JpaUsuarioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioPermisoService {

    private final UsuarioPermisoRepository repository;
    private final PermisoRepository permisoRepository;
    private final JpaUsuarioRepository usuarioRepository;
    private final PermisoEfectivoService permisoEfectivoService;

    public List<UsuarioPermiso> listarPorLegajo(Long legajoId) {
        usuarioRepository.findByLegajoId(legajoId)
                .orElseThrow(() -> new SeguridadNotFoundException("No existe el usuario " + legajoId));
        return repository.findAllByLegajoId(legajoId);
    }

    /**
     * Upsert del override individual. `otorgado` 1 otorga, 0 revoca. Un valor
     * nulo elimina la fila (el usuario vuelve a heredar de sus roles).
     */
    @Transactional
    public UsuarioPermiso set(Long legajoId, Long permisoId, Byte otorgado) {
        usuarioRepository.findByLegajoId(legajoId)
                .orElseThrow(() -> new SeguridadNotFoundException("No existe el usuario " + legajoId));
        permisoRepository.findById(permisoId)
                .orElseThrow(() -> new SeguridadNotFoundException("No existe el permiso " + permisoId));
        if (otorgado == null) {
            repository.deleteByLegajoIdAndPermisoId(legajoId, permisoId);
            permisoEfectivoService.invalidar();
            return null;
        }
        if (otorgado != 0 && otorgado != 1) {
            throw new SeguridadException("El valor de otorgado debe ser 0, 1 o nulo");
        }
        UsuarioPermiso guardado = repository.findByLegajoIdAndPermisoId(legajoId, permisoId)
                .map(actual -> repository.save(UsuarioPermiso.builder()
                        .usuarioPermisoId(actual.getUsuarioPermisoId())
                        .legajoId(legajoId)
                        .permisoId(permisoId)
                        .otorgado(otorgado)
                        .build()))
                .orElseGet(() -> repository.save(UsuarioPermiso.builder()
                        .legajoId(legajoId)
                        .permisoId(permisoId)
                        .otorgado(otorgado)
                        .build()));
        permisoEfectivoService.invalidar();
        return guardado;
    }

    @Transactional
    public void eliminar(Long legajoId, Long permisoId) {
        repository.findByLegajoIdAndPermisoId(legajoId, permisoId).ifPresent(actual -> {
            repository.deleteById(actual.getUsuarioPermisoId());
            permisoEfectivoService.invalidar();
        });
    }
}
