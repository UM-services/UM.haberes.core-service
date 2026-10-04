package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.adapter;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.entity.CategoriaEntity;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.mapper.CategoriaMapper;
import um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.persistence.repository.JpaCategoriaRepository;

@Component
@RequiredArgsConstructor
public class JpaCategoriaRepositoryAdapter implements CategoriaRepository {

    private final JpaCategoriaRepository jpaCategoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Override
    public List<Categoria> findAll() {
        return toDomainList(jpaCategoriaRepository.findAll(Sort.by("categoriaId").ascending()));
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
}
