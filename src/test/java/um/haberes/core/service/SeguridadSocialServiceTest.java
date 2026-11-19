package um.haberes.core.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import um.haberes.core.model.SeguridadSocialEntity;
import um.haberes.core.repository.JpaSeguridadSocialRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SeguridadSocialServiceTest {

    @Mock
    private JpaSeguridadSocialRepository repository;

    @InjectMocks
    private SeguridadSocialService service;

    @Test
    void findByUniqueOpcional_returnsSeguridadSocialWhenPeriodoExists() {
        SeguridadSocialEntity seguridadSocial = new SeguridadSocialEntity();
        seguridadSocial.setSeguridadSocialId(4L);
        seguridadSocial.setAnho(2024);
        seguridadSocial.setMes(6);
        when(repository.findByAnhoAndMes(2024, 6)).thenReturn(Optional.of(seguridadSocial));

        assertThat(service.findByUniqueOpcional(2024, 6)).contains(seguridadSocial);
    }

    @Test
    void findByUniqueOpcional_returnsEmptyWhenPeriodoIsNew() {
        when(repository.findByAnhoAndMes(2024, 1)).thenReturn(Optional.empty());

        assertThat(service.findByUniqueOpcional(2024, 1)).isEmpty();
    }
}
