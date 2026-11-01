package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.exception.CategoriaPeriodoException;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaPeriodoRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.entity.CategoriaEntity;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.repository.JpaCategoriaRepository;
import um.haberes.core.model.CategoriaPeriodoEntity;
import um.haberes.core.model.view.CategoriaByPeriodo;
import um.haberes.core.repository.JpaCategoriaPeriodoRepository;
import um.haberes.core.repository.view.JpaCategoriaByPeriodoRepository;

@Component
@RequiredArgsConstructor
public class JpaCategoriaPeriodoRepositoryAdapter implements CategoriaPeriodoRepository {

    private final JpaCategoriaPeriodoRepository jpaCategoriaPeriodoRepository;
    private final JpaCategoriaByPeriodoRepository jpaCategoriaByPeriodoRepository;
    private final JpaCategoriaRepository jpaCategoriaRepository;

    @Override
    public void upsert(Categoria categoria, Integer anho, Integer mes) {
        CategoriaPeriodoEntity categoriaPeriodo;
        try {
            categoriaPeriodo = jpaCategoriaPeriodoRepository
                    .findByCategoriaIdAndAnhoAndMes(categoria.getCategoriaId(), anho, mes)
                    .orElseThrow(() -> new CategoriaPeriodoException(categoria.getCategoriaId(), anho, mes));
        } catch (CategoriaPeriodoException e) {
            categoriaPeriodo = new CategoriaPeriodoEntity(null, categoria.getCategoriaId(), anho, mes, "",
                    BigDecimal.ZERO, (byte) 0, (byte) 0, (byte) 0, BigDecimal.ZERO);
        }
        categoriaPeriodo.setNombre(categoria.getNombre());
        categoriaPeriodo.setBasico(categoria.getBasico());
        categoriaPeriodo.setDocente(categoria.getDocente());
        categoriaPeriodo.setNoDocente(categoria.getNoDocente());
        categoriaPeriodo.setLiquidaPorHora(categoria.getLiquidaPorHora());
        categoriaPeriodo.setEstadoDocente(categoria.getEstadoDocente());
        jpaCategoriaPeriodoRepository.save(categoriaPeriodo);
    }

    @Override
    public void upsertAll(List<Categoria> categorias, Integer anho, Integer mes) {
        Map<String, CategoriaPeriodoEntity> mapPeriodos = new HashMap<>();
        for (CategoriaPeriodoEntity categoriaPeriodo : jpaCategoriaPeriodoRepository.findAllByAnhoAndMes(anho, mes)) {
            mapPeriodos.put(categoriaPeriodoKey(categoriaPeriodo.getCategoriaId(), categoriaPeriodo.getAnho(),
                    categoriaPeriodo.getMes()), categoriaPeriodo);
        }
        List<CategoriaPeriodoEntity> categoriaPeriodos = new ArrayList<>();
        for (Categoria categoria : categorias) {
            Long categoriaPeriodoId = null;
            String key = categoriaPeriodoKey(categoria.getCategoriaId(), anho, mes);
            if (mapPeriodos.containsKey(key)) {
                categoriaPeriodoId = mapPeriodos.get(key).getCategoriaPeriodoId();
            }
            CategoriaPeriodoEntity categoriaPeriodo = new CategoriaPeriodoEntity(categoriaPeriodoId,
                    categoria.getCategoriaId(), anho, mes, categoria.getNombre(), categoria.getBasico(),
                    categoria.getDocente(), categoria.getNoDocente(), categoria.getLiquidaPorHora(),
                    categoria.getEstadoDocente());
            categoriaPeriodos.add(categoriaPeriodo);
        }
        jpaCategoriaPeriodoRepository.saveAll(categoriaPeriodos);
    }

    @Override
    public List<Integer> findNoDocenteCategoriaIdsByPeriodo(Integer anho, Integer mes) {
        List<Integer> noDocenteIds = jpaCategoriaRepository
                .findAllByNoDocente((byte) 1, Sort.by("categoriaId").ascending()).stream()
                .map(CategoriaEntity::getCategoriaId)
                .collect(Collectors.toList());
        if (noDocenteIds.isEmpty()) {
            return List.of();
        }
        return jpaCategoriaByPeriodoRepository.findAllByAnhoAndMesAndCategoriaIdIn(anho, mes, noDocenteIds).stream()
                .map(CategoriaByPeriodo::getCategoriaId)
                .distinct()
                .collect(Collectors.toList());
    }

    private String categoriaPeriodoKey(Integer categoriaId, Integer anho, Integer mes) {
        return categoriaId + "." + anho + "." + mes;
    }
}
