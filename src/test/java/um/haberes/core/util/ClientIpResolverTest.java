package um.haberes.core.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientIpResolverTest {

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ClientIpResolver clientIpResolver;

    @Test
    void resuelvePrimerValorDeXForwardedFor() {
        when(request.getHeader("X-Forwarded-For")).thenReturn(" 10.0.0.5 , 192.168.1.1");

        assertThat(clientIpResolver.resolve(request)).isEqualTo("10.0.0.5");
    }

    @Test
    void usaXRealIpSiNoHayXForwardedFor() {
        when(request.getHeader("X-Forwarded-For")).thenReturn(null);
        when(request.getHeader("X-Real-IP")).thenReturn("10.0.0.6");

        assertThat(clientIpResolver.resolve(request)).isEqualTo("10.0.0.6");
    }

    @Test
    void usaRemoteAddrComoUltimoRecurso() {
        when(request.getHeader("X-Forwarded-For")).thenReturn("   ");
        when(request.getHeader("X-Real-IP")).thenReturn(null);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        assertThat(clientIpResolver.resolve(request)).isEqualTo("127.0.0.1");
    }

    @Test
    void devuelveVacioSinRequest() {
        assertThat(clientIpResolver.resolve(null)).isEmpty();
    }
}
