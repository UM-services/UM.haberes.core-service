package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.entity.CategoriaEntity;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.mapper.CategoriaMapper;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.repository.JpaCategoriaRepository;
import um.haberes.core.model.CategoriaPeriodoEntity;
import um.haberes.core.repository.JpaCategoriaPeriodoRepository;

@Component
@RequiredArgsConstructor
public class JpaCategoriaRepositoryAdapter implements CategoriaRepository {

    private final JpaCategoriaRepository jpaCategoriaRepository;
    private final JpaCategoriaPeriodoRepository jpaCategoriaPeriodoRepository;
    private final CategoriaMapper categoriaMapper;
    private final CategoriaPeriodoMaterializer categoriaPeriodoMaterializer;

    @Override
    public List<Categoria> findAll() {
        return toDomainList(jpaCategoriaRepository.findAll(Sort.by("categoriaId").ascending()));
    }

    @Override
    public List<Categoria> findAllByPeriodo(Integer anho, Integer mes) {
        Map<Integer, CategoriaPeriodoEntity> periodos = jpaCategoriaPeriodoRepository
                .findAllByAnhoAndMes(anho, mes).stream()
                .collect(Collectors.toMap(CategoriaPeriodoEntity::getCategoriaId, Function.identity()));
        List<CategoriaPeriodoEntity> pendientes = new ArrayList<>();
        List<Categoria> categorias = jpaCategoriaRepository.findAll(Sort.by("categoriaId").ascending()).stream()
                .map(entity -> {
                    Categoria categoria = categoriaMapper.toDomain(entity);
                    resolverPeriodo(categoria, periodos.get(entity.getCategoriaId()), anho, mes, pendientes);
                    return categoria;
                })
                .collect(Collectors.toList());
        categoriaPeriodoMaterializer.guardar(pendientes);
        return categorias;
    }

    @Override
    public List<Categoria> findAllByPuntosGreaterThan(BigDecimal puntos) {
        return toDomainList(jpaCategoriaRepository.findAllByPuntosGreaterThan(puntos, Sort.by("categoriaId").ascending()));
    }

    @Override
    public List<Categoria> findAllByEtecHoras(Byte etecHoras) {
        return toDomainList(jpaCategoriaRepository.findAllByEtecHoras(etecHoras, Sort.by("categoriaId").ascending()));
    }

    @Override
    public List<Categoria> findAllByCategoriaIdIn(List<Integer> categoriaIds) {
        if (categoriaIds == null || categoriaIds.isEmpty()) {
            return List.of();
        }
        List<CategoriaEntity> entities = jpaCategoriaRepository.findAllByCategoriaIdIn(categoriaIds);
        entities.sort(Comparator.comparing(CategoriaEntity::getCategoriaId));
        return toDomainList(entities);
    }

    @Override
    public List<Categoria> findAllByCategoriaIdNotIn(List<Integer> categoriaIds) {
        return toDomainList(jpaCategoriaRepository.findAllByCategoriaIdNotIn(categoriaIds));
    }

    @Override
    public List<Categoria> findAllByDocente(Byte docente) {
        return toDomainList(jpaCategoriaRepository.findAllByDocente(docente, Sort.by("categoriaId").ascending()));
    }

    @Override
    public List<Categoria> findAllByNoDocente(Byte noDocente) {
        return toDomainList(jpaCategoriaRepository.findAllByNoDocente(noDocente, Sort.by("categoriaId").ascending()));
    }

    @Override
    public Optional<Categoria> findByCategoriaId(Integer categoriaId) {
        return jpaCategoriaRepository.findByCategoriaId(categoriaId).map(categoriaMapper::toDomain);
    }

    @Override
    public Optional<Categoria> findByCategoriaIdAndPeriodo(Integer categoriaId, Integer anho, Integer mes) {
        return jpaCategoriaRepository.findByCategoriaId(categoriaId).map(entity -> {
            CategoriaPeriodoEntity periodo = jpaCategoriaPeriodoRepository
                    .findByCategoriaIdAndAnhoAndMes(categoriaId, anho, mes).orElse(null);
            Categoria categoria = categoriaMapper.toDomain(entity);
            List<CategoriaPeriodoEntity> pendientes = new ArrayList<>();
            resolverPeriodo(categoria, periodo, anho, mes, pendientes);
            categoriaPeriodoMaterializer.guardar(pendientes);
            return categoria;
        });
    }

    @Override
    public Optional<Categoria> findLast() {
        return jpaCategoriaRepository.findTopByOrderByCategoriaIdDesc().map(categoriaMapper::toDomain);
    }

    @Override
    public Categoria save(Categoria categoria) {
        return categoriaMapper.toDomain(jpaCategoriaRepository.save(categoriaMapper.toEntity(categoria)));
    }

    @Override
    public List<Categoria> saveAll(List<Categoria> categorias) {
        List<CategoriaEntity> entities = categorias.stream()
                .map(categoriaMapper::toEntity)
                .collect(Collectors.toList());
        return toDomainList(jpaCategoriaRepository.saveAll(entities));
    }

    @Override
    public void deleteByCategoriaId(Integer categoriaId) {
        jpaCategoriaRepository.deleteById(categoriaId);
    }

    private List<Categoria> toDomainList(List<CategoriaEntity> entities) {
        return entities.stream()
                .map(categoriaMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Aplica los valores ETEC del período sobre la plantilla de la categoría.
     *
     * <p>La fila de {@code categoria_periodo} manda cuando tiene puntaje propio (o marca
     * horas ETEC). Si no existe, o existe todavía en cero, el valor sale de
     * {@code categoria} y la fila queda pendiente de registrar para que el dato quede
     * versionado en el período.</p>
     */
    private void resolverPeriodo(Categoria categoria, CategoriaPeriodoEntity periodo, Integer anho, Integer mes,
            List<CategoriaPeriodoEntity> pendientes) {
        if (categoria == null || anho == null || mes == null || anho <= 0 || mes <= 0) {
            return;
        }
        if (periodo == null) {
            if (tieneDatosEtec(categoria)) {
                pendientes.add(filaDesdePlantilla(categoria, anho, mes));
            }
            return;
        }
        boolean registrar = false;
        if (tienePuntos(periodo.getPuntos())) {
            categoria.setPuntos(periodo.getPuntos());
        } else if (tienePuntos(categoria.getPuntos())) {
            periodo.setPuntos(categoria.getPuntos());
            registrar = true;
        }
        if (liquidaHoras(periodo.getEtecHoras())) {
            categoria.setEtecHoras(periodo.getEtecHoras());
        } else if (liquidaHoras(categoria.getEtecHoras())) {
            periodo.setEtecHoras(categoria.getEtecHoras());
            registrar = true;
        }
        if (registrar) {
            pendientes.add(periodo);
        }
    }

    private boolean tieneDatosEtec(Categoria categoria) {
        return tienePuntos(categoria.getPuntos()) || liquidaHoras(categoria.getEtecHoras());
    }

    private boolean tienePuntos(BigDecimal puntos) {
        return puntos != null && puntos.compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean liquidaHoras(Byte etecHoras) {
        return etecHoras != null && etecHoras.intValue() == 1;
    }

    private CategoriaPeriodoEntity filaDesdePlantilla(Categoria categoria, Integer anho, Integer mes) {
        CategoriaPeriodoEntity fila = new CategoriaPeriodoEntity();
        fila.setCategoriaId(categoria.getCategoriaId());
        fila.setAnho(anho);
        fila.setMes(mes);
        fila.setNombre(categoria.getNombre() == null ? "" : categoria.getNombre());
        fila.setBasico(categoria.getBasico() == null ? BigDecimal.ZERO : categoria.getBasico());
        fila.setDocente(categoria.getDocente() == null ? (byte) 0 : categoria.getDocente());
        fila.setNoDocente(categoria.getNoDocente() == null ? (byte) 0 : categoria.getNoDocente());
        fila.setLiquidaPorHora(categoria.getLiquidaPorHora() == null ? (byte) 0 : categoria.getLiquidaPorHora());
        fila.setEstadoDocente(categoria.getEstadoDocente() == null ? BigDecimal.ZERO : categoria.getEstadoDocente());
        fila.setPuntos(categoria.getPuntos() == null ? BigDecimal.ZERO : categoria.getPuntos());
        fila.setEtecHoras(categoria.getEtecHoras() == null ? (byte) 0 : categoria.getEtecHoras());
        return fila;
    }
}
