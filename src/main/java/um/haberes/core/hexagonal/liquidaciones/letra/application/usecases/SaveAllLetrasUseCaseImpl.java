package um.haberes.core.hexagonal.liquidaciones.letra.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.model.Letra;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.in.SaveAllLetrasUseCase;
import um.haberes.core.hexagonal.liquidaciones.letra.domain.ports.out.LetraRepository;

@Component
@RequiredArgsConstructor
public class SaveAllLetrasUseCaseImpl implements SaveAllLetrasUseCase {

    private final LetraRepository letraRepository;

    @Transactional
    @Override
    public List<Letra> saveAllLetras(List<Letra> letras) {
        return letraRepository.saveAll(letras);
    }
}
