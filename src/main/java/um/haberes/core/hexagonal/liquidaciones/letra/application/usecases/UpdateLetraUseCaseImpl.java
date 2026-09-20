package um.haberes.core.hexagonal.liquidaciones.letra.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.model.Letra;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.in.UpdateLetraUseCase;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.out.LetraRepository;

@Component
@RequiredArgsConstructor
public class UpdateLetraUseCaseImpl implements UpdateLetraUseCase {

    private final LetraRepository letraRepository;

    @Transactional
    @Override
    public Optional<Letra> updateLetra(Long letraId, Letra letra) {
        return letraRepository.findByLetraId(letraId).map(existing -> {
            Letra.LetraBuilder builder = Letra.builder()
                    .letraId(letraId)
                    .legajoId(existing.getLegajoId())
                    .anho(existing.getAnho())
                    .mes(existing.getMes());
            if (letra.getNeto() != null) {
                builder.neto(letra.getNeto());
            } else {
                builder.neto(existing.getNeto());
            }
            if (letra.getCadena() != null) {
                builder.cadena(letra.getCadena());
            } else {
                builder.cadena(existing.getCadena());
            }
            return letraRepository.save(builder.build());
        });
    }
}
