package um.haberes.core.hexagonal.seguridad.infrastructure.security;

import org.junit.jupiter.api.Test;
import um.haberes.core.hexagonal.seguridad.infrastructure.config.SeguridadProperties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TokenServiceTest {

    private SeguridadProperties properties() {
        SeguridadProperties properties = new SeguridadProperties();
        properties.setTokenSecret("0123456789abcdef0123456789abcdef");
        properties.setTokenTtlHours(8);
        return properties;
    }

    @Test
    void emiteYValidaElLegajo() {
        TokenService service = new TokenService(properties());

        TokenService.Token token = service.emitir(725L);

        assertThat(token.token()).hasSizeGreaterThan(40);
        Identidad identidad = service.validar(token.token());
        assertThat(identidad.legajoId()).isEqualTo(725L);
        assertThat(identidad.esServicio()).isFalse();
    }

    @Test
    void rechazaUnaFirmaAlterada() {
        TokenService service = new TokenService(properties());
        String token = service.emitir(725L).token();
        String alterado = token.substring(0, token.length() - 2) + "xx";

        assertThatThrownBy(() -> service.validar(alterado))
                .isInstanceOf(TokenService.TokenInvalidoException.class);
    }

    @Test
    void rechazaUnTokenVencido() {
        SeguridadProperties properties = properties();
        properties.setTokenTtlHours(-1);
        TokenService service = new TokenService(properties);

        String token = service.emitir(725L).token();

        assertThatThrownBy(() -> service.validar(token))
                .isInstanceOf(TokenService.TokenInvalidoException.class)
                .hasMessageContaining("vencido");
    }

    @Test
    void rechazaUnTokenMalFormado() {
        TokenService service = new TokenService(properties());

        assertThatThrownBy(() -> service.validar("no-es-un-jwt"))
                .isInstanceOf(TokenService.TokenInvalidoException.class);
    }

    @Test
    void exigeSecretoCuandoElTokenEsObligatorio() {
        SeguridadProperties properties = properties();
        properties.setTokenRequired(true);
        properties.setTokenSecret("");

        TokenService service = new TokenService(properties);

        assertThatThrownBy(service::validarConfiguracion)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("HABERES_AUTH_SECRET");
    }
}
