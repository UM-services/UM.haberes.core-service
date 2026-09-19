package um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.in;

public interface DeleteLetraByUniqueKeyUseCase {

    void deleteLetraByUniqueKey(Long legajoId, Integer anho, Integer mes);
}
