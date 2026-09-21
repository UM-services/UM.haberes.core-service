package um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out;

public interface ContactoRepository {

    void upsertMailInstitucional(Long legajoId, String mailInstitucional);
}
